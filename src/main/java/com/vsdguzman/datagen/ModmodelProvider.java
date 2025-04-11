package com.vsdguzman.datagen;

import com.vsdguzman.UpdateSuppression;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;


public class ModmodelProvider extends FabricModelProvider {

    public ModmodelProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(UpdateSuppression.UpdateSuppressionBlock);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }

}

