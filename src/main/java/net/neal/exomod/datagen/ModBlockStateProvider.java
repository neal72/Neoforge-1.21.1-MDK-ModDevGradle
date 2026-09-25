package net.neal.exomod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.neal.exomod.ExoMod;
import net.neal.exomod.block.ModBlocks;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ExoMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.PINK_DIAMOND_BLOCK);
        blockWithItem(ModBlocks.PINK_DIAMOND_ORE);
        blockWithItem(ModBlocks.SOUND_BLOCK);

        var tex = blockTexture(ModBlocks.PINK_DIAMOND_BLOCK.get());

        stairsBlock((StairBlock) ModBlocks.PINK_DIAMOND_STAIRS.get(), tex);
        slabBlock((SlabBlock) ModBlocks.PINK_DIAMOND_SLAB.get(), tex, tex);
        buttonBlock((ButtonBlock) ModBlocks.PINK_DIAMOND_BUTTON.get(), tex);
        pressurePlateBlock((PressurePlateBlock) ModBlocks.PINK_DIAMOND_PRESSURE_PLATE.get(), tex);
        fenceBlock((FenceBlock) ModBlocks.PINK_DIAMOND_FENCE.get(), tex);
        fenceGateBlock((FenceGateBlock) ModBlocks.PINK_DIAMOND_FENCE_GATE.get(), tex);
        wallBlock((WallBlock) ModBlocks.PINK_DIAMOND_WALL.get(), tex);

        doorBlockWithRenderType((DoorBlock) ModBlocks.PINK_DIAMOND_DOOR.get(),
                modLoc("block/pink_diamond_door_bottom"), modLoc("block/pink_diamond_door_top"), "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.PINK_DIAMOND_TRAP_DOOR.get(),
                modLoc("block/pink_diamond_trap_door"), true, "cutout");

        // item models (what you see in your inventory)
        blockItem(ModBlocks.PINK_DIAMOND_STAIRS);
        blockItem(ModBlocks.PINK_DIAMOND_SLAB);
        blockItem(ModBlocks.PINK_DIAMOND_PRESSURE_PLATE);
        blockItem(ModBlocks.PINK_DIAMOND_FENCE_GATE);
        blockItem(ModBlocks.PINK_DIAMOND_TRAP_DOOR, "_bottom");

        simpleBlockItem(ModBlocks.PINK_DIAMOND_FENCE.get(), models().fenceInventory("pink_diamond_fence_inventory", tex));
        simpleBlockItem(ModBlocks.PINK_DIAMOND_WALL.get(), models().wallInventory("pink_diamond_wall_inventory", tex));
        simpleBlockItem(ModBlocks.PINK_DIAMOND_BUTTON.get(), models().buttonInventory("pink_diamond_button_inventory", tex));
        itemModels().basicItem(ModBlocks.PINK_DIAMOND_DOOR.asItem()); // flat door icon
    }

    private void blockWithItem(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void blockItem(DeferredBlock<?> block) {
        blockItem(block, "");
    }

    private void blockItem(DeferredBlock<?> block, String suffix) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(
                "exo_mod:block/" + block.getId().getPath() + suffix));
    }
}