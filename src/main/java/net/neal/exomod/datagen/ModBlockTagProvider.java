package net.neal.exomod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neal.exomod.ExoMod;
import net.neal.exomod.block.ModBlocks;
import net.neal.exomod.util.ModTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ExoMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.PINK_DIAMOND_ORE.get())
                .add(ModBlocks.PINK_DIAMOND_BLOCK.get());

        // Only diamond-level pickaxes or better can get drops
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.PINK_DIAMOND_ORE.get())
                .add(ModBlocks.PINK_DIAMOND_BLOCK.get());

        tag(ModTags.Blocks.NEEDS_PINK_DIAMOND_TOOL)
                .addTags(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_PINK_DIAMOND_TOOL)
                .addTags(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_PINK_DIAMOND_TOOL);
    }
}