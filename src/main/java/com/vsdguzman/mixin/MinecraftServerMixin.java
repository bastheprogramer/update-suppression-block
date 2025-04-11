package com.vsdguzman.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.vsdguzman.UpdateSuppressionError;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.vsdguzman.UpdateSuppression;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@WrapOperation(
			method = "tickWorlds",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V"
			)
	)
	private void onTick(ServerWorld serverWorld,
						BooleanSupplier shouldKeepTicking,
						Operation<Void> original) {
		try {
			original.call(serverWorld, shouldKeepTicking);
		} catch (Throwable t) {
			if (isSuppressionError(t)) {
				// DEBUG so it doesn't spam WARN:
				UpdateSuppression.LOGGER.debug(
						"Suppressed a world‑tick update in {}: {}",
						serverWorld.getRegistryKey().getValue(),
						t.getMessage()
				);
				return;
			}
			// Not our suppression signal: rethrow
			throw t;
		}
	}

	private boolean isSuppressionError(Throwable t) {
		while (t != null) {
			if (t instanceof UpdateSuppressionError) {
				return true;
			}
			t = t.getCause();
		}
		return false;
	}
}
