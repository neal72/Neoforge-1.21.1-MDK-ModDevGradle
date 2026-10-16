package net.neal.exomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neal.exomod.block.entity.EnergyStorageBlockEntity;
import net.neal.exomod.block.entity.ModBlockEntities;
import net.neal.exomod.block.entity.SideMode;
import org.jetbrains.annotations.Nullable;

public class EnergyStorageBlock extends BaseEntityBlock {
    public static final MapCodec<EnergyStorageBlock> CODEC = simpleCodec(EnergyStorageBlock::new);

    public EnergyStorageBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(SIDE_DOWN, SideMode.INPUT).setValue(SIDE_UP, SideMode.INPUT)
                .setValue(SIDE_NORTH, SideMode.INPUT).setValue(SIDE_SOUTH, SideMode.INPUT)
                .setValue(SIDE_WEST, SideMode.INPUT).setValue(SIDE_EAST, SideMode.INPUT));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    // BaseEntityBlock is invisible by default, so this is required
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyStorageBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null
                : createTickerHelper(type, ModBlockEntities.ENERGY_STORAGE_BE.get(), EnergyStorageBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof EnergyStorageBlockEntity be) {
            player.openMenu(be, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static final EnumProperty<SideMode> SIDE_DOWN  = EnumProperty.create("down",  SideMode.class);
    public static final EnumProperty<SideMode> SIDE_UP    = EnumProperty.create("up",    SideMode.class);
    public static final EnumProperty<SideMode> SIDE_NORTH = EnumProperty.create("north", SideMode.class);
    public static final EnumProperty<SideMode> SIDE_SOUTH = EnumProperty.create("south", SideMode.class);
    public static final EnumProperty<SideMode> SIDE_WEST  = EnumProperty.create("west",  SideMode.class);
    public static final EnumProperty<SideMode> SIDE_EAST  = EnumProperty.create("east",  SideMode.class);

    public static EnumProperty<SideMode> propertyFor(Direction dir) {
        return switch (dir) {
            case DOWN -> SIDE_DOWN;
            case UP -> SIDE_UP;
            case NORTH -> SIDE_NORTH;
            case SOUTH -> SIDE_SOUTH;
            case WEST -> SIDE_WEST;
            case EAST -> SIDE_EAST;
        };
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIDE_DOWN, SIDE_UP, SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST);
    }
}