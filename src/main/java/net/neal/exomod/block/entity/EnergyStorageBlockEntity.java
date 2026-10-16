package net.neal.exomod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neal.exomod.block.custom.EnergyStorageBlock;
import net.neal.exomod.screen.EnergyStorageMenu;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EnergyStorageBlockEntity extends BlockEntity implements MenuProvider {
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

    private final SideMode[] sideModes = new SideMode[6];
    private final IEnergyStorage inputView = new SidedView(true);
    private final IEnergyStorage outputView = new SidedView(false);
    private int lastSyncedEnergy = -1;

    // Data sent to the GUI: 0-5 = side modes, 6 + 7 = energy (split in two, max 65535 per slot)
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            if (index < 6) return sideModes[index].ordinal();
            if (index == 6) return energy.getEnergyStored() & 0xFFFF;
            return (energy.getEnergyStored() >> 16) & 0xFFFF;
        }

        @Override
        public void set(int index, int value) {}

        @Override
        public int getCount() {
            return 8;
        }
    };

    public EnergyStorageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENERGY_STORAGE_BE.get(), pos, state);
        Arrays.fill(sideModes, SideMode.INPUT);
    }

    // Called by cables/other mods. null = no connection on that side
    @Nullable
    public IEnergyStorage getEnergyStorage(@Nullable Direction side) {
        if (side == null) return energy;
        return switch (sideModes[side.get3DDataValue()]) {
            case INPUT -> inputView;
            case OUTPUT -> outputView;
            case NONE -> null;
        };
    }

    public void cycleSide(Direction dir) {
        int i = dir.get3DDataValue();
        sideModes[i] = sideModes[i].next();
        setChanged();
        if (level != null) {
            BlockState state = getBlockState();
            if (state.hasProperty(EnergyStorageBlock.propertyFor(dir))) {
                level.setBlock(worldPosition, state.setValue(EnergyStorageBlock.propertyFor(dir), sideModes[i]), 3);
            }
            level.invalidateCapabilities(worldPosition);
        }
    }

    public float getFillPercent() {
        return (float) energy.getEnergyStored() / energy.getMaxEnergyStored();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnergyStorageBlockEntity be) {
        if (level.isClientSide) return;

        if (level.getGameTime() % 10 == 0 && be.energy.getEnergyStored() != be.lastSyncedEnergy) {
            be.lastSyncedEnergy = be.energy.getEnergyStored();
            level.sendBlockUpdated(pos, state, state, 3);
        }

        // Only push out of OUTPUT sides
        for (Direction dir : Direction.values()) {
            if (be.sideModes[dir.get3DDataValue()] != SideMode.OUTPUT) continue;
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

    // ---------- GUI ----------
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.exo_mod.energy_storage");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new EnergyStorageMenu(id, inv, this, data);
    }

    // ---------- Saving ----------
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("energy", energy.serializeNBT(registries));
        byte[] modes = new byte[6];
        for (int i = 0; i < 6; i++) modes[i] = (byte) sideModes[i].ordinal();
        tag.putByteArray("sides", modes);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("energy")) energy.deserializeNBT(registries, tag.get("energy"));
        if (tag.contains("sides")) {
            byte[] modes = tag.getByteArray("sides");
            for (int i = 0; i < Math.min(6, modes.length); i++) sideModes[i] = SideMode.values()[modes[i]];
        }
    }

    // ---------- Client sync (for the renderer) ----------
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Input-only or output-only view of the same energy
    private class SidedView implements IEnergyStorage {
        private final boolean input;
        SidedView(boolean input) { this.input = input; }

        @Override public int receiveEnergy(int amount, boolean simulate) { return input ? energy.receiveEnergy(amount, simulate) : 0; }
        @Override public int extractEnergy(int amount, boolean simulate) { return input ? 0 : energy.extractEnergy(amount, simulate); }
        @Override public int getEnergyStored() { return energy.getEnergyStored(); }
        @Override public int getMaxEnergyStored() { return energy.getMaxEnergyStored(); }
        @Override public boolean canExtract() { return !input; }
        @Override public boolean canReceive() { return input; }
    }
}