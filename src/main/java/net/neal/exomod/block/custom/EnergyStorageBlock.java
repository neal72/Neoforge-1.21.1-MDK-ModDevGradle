package net.neal.exomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neal.exomod.block.entity.EnergyStorageBlockEntity;
import net.neal.exomod.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class EnergyStorageBlock extends BaseEntityBlock {
    public static final MapCodec<EnergyStorageBlock> CODEC = simpleCodec(EnergyStorageBlock::new);

    public EnergyStorageBlock(Properties properties) {
        super(properties);
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

    // Right-click to see how much is stored
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof EnergyStorageBlockEntity be) {
            var e = be.getEnergyStorage(null);
            player.displayClientMessage(Component.literal(
                    e.getEnergyStored() + " / " + e.getMaxEnergyStored() + " RF"), true);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}