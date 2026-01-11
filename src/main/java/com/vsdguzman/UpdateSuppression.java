package com.vsdguzman;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class UpdateSuppression implements ModInitializer {
    public static final String MOD_ID = "update-suppression-block";
    public static final Logger LOGGER = LogManager.getLogger("UpdateSuppressionBlock");

    // FIXED: Use method references (::new) so the block isn't created until the Settings have the ID.
    public static final Block UpdateSuppressionBlock = registerBlock("update-suppression-block", UpdateSuppressorBlock::new, AbstractBlock.Settings.create().strength(-1.0f));
    /**
     * Registers the block.
     * FIXED: Now accepts a Factory (Function) instead of a pre-made Block.
     * This allows us to inject the RegistryKey into the settings BEFORE the block is created.
     *
     * @param name          the registry name of the block
     * @param blockFactory  the constructor of the block (e.g., Block::new)
     * @param blockSettings the settings for the block
     * @return the registered block
     */
    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings) {
        // 1. Create ID and Key
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);

        // 2. Add Key to Settings
        AbstractBlock.Settings settings = blockSettings.registryKey(key);

        // 3. Create Block using the Factory
        Block block = blockFactory.apply(settings);

        // 4. Register
        return Registry.register(Registries.BLOCK, key, block);
    }

    /**
     * Registers the BlockItem.
     */
    private static Item registerBlockItem(String name, Block block) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item item = new BlockItem(block, new Item.Settings().registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    @Override
    public void onInitialize() {

        // Register the BlockItems
        registerBlockItem("update-suppression-block", UpdateSuppressionBlock);

        // Add the block to the REDSTONE item group
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(UpdateSuppressionBlock);
        });

        UpdateSuppressionGamerule.init();

        LOGGER.info("Update Suppression mod has been initialized.");
    }
}