package net.neal.exomod.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neal.exomod.block.ModBlocks;
import net.neal.exomod.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Special drops first
        add(ModBlocks.PINK_DIAMOND_ORE.get(),
                block -> createOreDrop(ModBlocks.PINK_DIAMOND_ORE.get(), ModItems.PINK_DIAMOND.get()));

        add(ModBlocks.PINK_DIAMOND_DOOR.get(),
                block -> createDoorTable(ModBlocks.PINK_DIAMOND_DOOR.get()));

        // Every other block drops itself
        for (Block block : getKnownBlocks()) {
            if (!map.containsKey(block.getLootTable())) {
                dropSelf(block);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}