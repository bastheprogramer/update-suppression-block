package com.vsdguzman;


import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.GameRule;
import static com.vsdguzman.UpdateSuppression.MOD_ID;

public class UpdateSuppressionGamerule {
    public static final GameRule<Boolean> UpdateSuppressionCrashFix = GameRuleBuilder.forBoolean(true)
            .buildAndRegister(Identifier.of(MOD_ID, "update_suppression_crash_fix"));
                    //("UpdateSuppressionCrashFix", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

    public static void init(){
    }
}
