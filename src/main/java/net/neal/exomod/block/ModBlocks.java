package net.neal.exomod.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neal.exomod.ExoMod;
import net.neal.exomod.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ExoMod.MOD_ID);

    public static final DeferredBlock<Block> PINK_DIAMOND_ORE = registerBlocks("pink_diamond_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).sound(SoundType.STONE)));

    public static final DeferredBlock<Block> PINK_DIAMOND_BLOCK = registerBlocks("pink_diamond_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST)));

    public static final  DeferredBlock<Block> SOUND_BLOCK = registerBlocks("sound_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.ROOTS)));


    public static final DeferredBlock<Block> PINK_DIAMOND_STAIRS = registerBlocks("pink_diamond_stairs",
            () -> new StairBlock(ModBlocks.PINK_DIAMOND_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PINK_DIAMOND_SLAB = registerBlocks("pink_diamond_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PINK_DIAMOND_BUTTON = registerBlocks("pink_diamond_button",
            () -> new ButtonBlock(BlockSetType.IRON, 10,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON).sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PINK_DIAMOND_PRESSURE_PLATE = registerBlocks("pink_diamond_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PINK_DIAMOND_FENCE = registerBlocks("pink_diamond_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PINK_DIAMOND_FENCE_GATE = registerBlocks("pink_diamond_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST),
                    SoundEvents.CHAIN_PLACE, SoundEvents.ANVIL_BREAK));

    public static final DeferredBlock<Block> PINK_DIAMOND_WALL = registerBlocks("pink_diamond_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PINK_DIAMOND_DOOR = registerBlocks("pink_diamond_door",
            () -> new DoorBlock(BlockSetType.IRON,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST).noOcclusion()));

    public static final DeferredBlock<Block> PINK_DIAMOND_TRAP_DOOR = registerBlocks("pink_diamond_trap_door",
            () -> new TrapDoorBlock(BlockSetType.IRON,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).sound(SoundType.AMETHYST).noOcclusion()));



    private static <T extends Block> DeferredBlock<T> registerBlocks(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
