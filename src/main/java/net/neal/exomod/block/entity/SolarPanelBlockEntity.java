package net.neal.exomod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class SolarPanelBlockEntity extends BlockEntity {
    public static final int CAPACITY = 10_000;
    public static final int GENERATION = 40;     // RF per tick in full sun
    public static final int MAX_OUTPUT = 200;

    // maxReceive = 0, so other mods can only pull energy out, never push it in
    private final GeneratorStorage energy = new GeneratorStorage(CAPACITY, MAX_OUTPUT);

    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOLAR_PANEL_BE.get(), pos, state);
    }

    public IEnergyStorage getEnergyStorage(Direction side) {
        return energy;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SolarPanelBlockEntity be) {
        if (level.isClientSide) return;

        // 1. Generate
        if (level.isDay() && level.canSeeSky(pos.above())) {
            int amount = level.isRaining() ? GENERATION / 2 : GENERATION;
            be.energy.generate(amount);
            be.setChanged();
        }

        // 2. Push to neighbours
        for (Direction dir : Direction.values()) {
            if (be.energy.getEnergyStored() <= 0) return;
            IEnergyStorage target = level.getCapability(
                    Capabilities.EnergyStorage.BLOCK, pos.relative(dir), dir.getOpposite());
            if (target != null && target.canReceive()) {
                int canSend = be.energy.extractEnergy(MAX_OUTPUT, true);
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

    // Small helper: lets the panel add energy to itself even though maxReceive is 0
    private static class GeneratorStorage extends EnergyStorage {
        public GeneratorStorage(int capacity, int maxExtract) {
            super(capacity, 0, maxExtract);
        }

        public void generate(int amount) {
            this.energy = Math.min(this.capacity, this.energy + amount);
        }
    }
}