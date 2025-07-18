package com.vsdguzman.datagen;

import com.vsdguzman.UpdateSuppression;
import com.vsdguzman.UpdateSuppressionGamerule;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEnglishLangProvider extends FabricLanguageProvider {
    public ModEnglishLangProvider(FabricDataOutput dataGenerator, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataGenerator, "en_us", registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(UpdateSuppression.UpdateSuppressionBlock, "Update Suppression Block");
        translationBuilder.add(UpdateSuppression.UpdateSuppressionBlock.asItem(), "Update Suppression Block");
        translationBuilder.add(String.valueOf(UpdateSuppressionGamerule.UpdateSuppressionCrashFix), "Update Suppression Crash Fix");
    }
}
