package net.neal.exomod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class EnergyStorageBlockEntity extends BlockEntity {
    public static final int CAPACITY = 1_000_000;
    public static final int MAX_TRANSFER = 10_000;

    private final EnergyStorage energy = new EnergyStorage(CAPACITY, MAX_TRANSFER, MAX_TRANSFER) {
        @Override
        public int receiveEnergy(int amount, boolean simulate) {
            int received = super.receiveEnergy(amount, simulate);
            if (received > 0 && !simulate) setChanged();
            return received;
        }

        @Override
        public int extractEnergy(int amount, boolean simulate) {
            int extracted = super.extractEnergy(amount, simulate);
            if (extracted > 0 && !simulate) setChanged();
            return extracted;
        }
    };

    public EnergyStorageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENERGY_STORAGE_BE.get(), pos, state);
    }

    public IEnergyStorage getEnergyStorage(Direction side) {
        return energy;
    }


    // Optional: push power into neighbouring machines/cables every tick
    public static void tick(Level level, BlockPos pos, BlockState state, EnergyStorageBlockEntity be) {
        if (level.isClientSide) return;
        // Sync to client every half second, but only if the energy changed
        if (level.getGameTime() % 10 == 0 && be.energy.getEnergyStored() != be.lastSyncedEnergy) {
            be.lastSyncedEnergy = be.energy.getEnergyStored();
            level.sendBlockUpdated(pos, state, state, 3);
        }
        for (Direction dir : Direction.values()) {
            if (be.energy.getEnergyStored() <= 0) return;
            IEnergyStorage target = level.getCapability(
                    Capabilities.EnergyStorage.BLOCK, pos.relative(dir), dir.getOpposite());
            if (target != null && target.canReceive()) {
                int canSend = be.energy.extractEnergy(MAX_TRANSFER, true);
                int accepted = target.receiveEnergy(canSend, false);
                be.energy.extractEnergy(accepted, false);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("energy", energy.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("energy")) {
            energy.deserializeNBT(registries, tag.get("energy"));
        }
    }

    private int lastSyncedEnergy = -1;

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public float getFillPercent() {
        return (float) energy.getEnergyStored() / energy.getMaxEnergyStored();
    }
}