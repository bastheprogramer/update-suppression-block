package com.vsdguzman.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.vsdguzman.UpdateSuppression;
import com.vsdguzman.UpdateSuppressionError;
import com.vsdguzman.UpdateSuppressionGamerule;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

	/**
	 * Wraps the original `tickWorlds` method to catch and handle `UpdateSuppressionError`.
	 * This allows the mod to suppress world tick updates when the custom error is thrown,
	 * preventing crashes and logging the suppression.
	 * code by TISUnion(the @WrapOperation)
	 * https://github.com/TISUnion
	 */
	@WrapOperation(
			method = "tick", // NOT "tickWorlds"
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/server/MinecraftServer;tickWorlds(Ljava/util/function/BooleanSupplier;)V"
			)
	)
	private void onTick(MinecraftServer minecraftServer,
						BooleanSupplier shouldKeepTicking,
						Operation<Void> original) {
		// No need to get server here unless it's used more extensively.
		// It can be directly accessed via serverWorld.getServer() if needed.

		// Get the gamerule value. This line implicitly converts to String then back to boolean,
		// which might be redundant if the gamerule already stores boolean.
		// The debug log should be inside a check for debug level, to avoid performance overhead in production.
		// boolean gameruleValue = serverWorld.getServer().getGameRules().getBoolean(UpdateSuppressionGamerule.UpdateSuppressionCrashFix);
		// UpdateSuppression.LOGGER.debug("UpdateSuppressionCrashFix gamerule value: {}", gameruleValue); // More descriptive log message

		try {
			original.call(minecraftServer,shouldKeepTicking);
		} catch (Throwable t) {
			// Check if the gamerule is enabled before attempting to suppress.
			// This ensures suppression only occurs when intended.
			boolean crashFixGameruleEnabled = minecraftServer.getOverworld().getGameRules().getValue(UpdateSuppressionGamerule.UpdateSuppressionCrashFix);
			boolean isOurSuppressionError = isSuppressionError(t);
			UpdateSuppression.LOGGER.info(String.valueOf(crashFixGameruleEnabled), isOurSuppressionError);
			if (crashFixGameruleEnabled && isOurSuppressionError) {
				return; // Suppress the error and continue ticking
			}
			// If it's not our suppression signal OR the gamerule is off, rethrow the exception.
			throw t;
		}
	}

	/**
	 * Checks if the given Throwable or any of its causes is an instance of `UpdateSuppressionError`.
	 * @param t The Throwable to check.
	 * @return true if an `UpdateSuppressionError` is found in the cause chain, false otherwise.
	 */
	private boolean isSuppressionError(Throwable t) {
		// Iterate through the cause chain to find our custom error
		Throwable current = t;
		while (current != null) {
			if (current instanceof UpdateSuppressionError) {
				return true;
			}
			current = current.getCause();
		}
		return false;
	}
}