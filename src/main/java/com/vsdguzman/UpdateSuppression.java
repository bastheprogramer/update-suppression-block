package com.vsdguzman;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTypes;
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

public class UpdateSuppression implements ModInitializer {
    // It is common practice to use lowercase for mod IDs.
    public static final String MOD_ID = "update-suppression-block";
    public static final Logger LOGGER = LogManager.getLogger("UpdateSuppressionBlock");

    // Register the block using a helper method that sets the registry key in its settings.
    public static final Block UpdateSuppressionBlock = registerBlock("update-suppression-block");

    /**
     * Registers the block and assigns the registry key to its settings.
     *
     * @param name the registry name of the block
     * @return the registered block
     */
    private static Block registerBlock(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        // Create the block with its registry key set in the settings.
        Block block = new UpdateSuppressorBlock(
                AbstractBlock.Settings.create().strength(-1.0f).registryKey(key)
        );
        return Registry.register(Registries.BLOCK, key, block);
    }

    /**
     * Registers the BlockItem associated with a block so it can appear in the inventory.
     *
     * @param name  the registry name of the item
     * @param block the block to create an item for
     * @return the registered item
     */
    private static Item registerBlockItem(String name, Block block) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item item = new BlockItem(block, new Item.Settings().registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    @Override
    public void onInitialize() {
        // Register the BlockItem so that the block can be held in an inventory.
        registerBlockItem("update-suppression-block", UpdateSuppressionBlock);

        // Add the block to the REDSTONE item group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(UpdateSuppressionBlock);
        });

        LOGGER.info("Update Suppression mod has been initialized.");
    }
}
