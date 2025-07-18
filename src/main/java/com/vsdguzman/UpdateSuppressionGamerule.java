package com.vsdguzman;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class UpdateSuppressionGamerule {
    public static final GameRules.Key<GameRules.BooleanRule> UpdateSuppressionCrashFix =
            GameRuleRegistry.register("UpdateSuppressionCrashFix", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

    public static void init(){
        return;
    }
}
