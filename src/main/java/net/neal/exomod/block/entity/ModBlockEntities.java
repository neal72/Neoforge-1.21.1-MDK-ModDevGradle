package net.neal.exomod.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neal.exomod.ExoMod;
import net.neal.exomod.block.ModBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ExoMod.MOD_ID);

    public static final Supplier<BlockEntityType<EnergyStorageBlockEntity>> ENERGY_STORAGE_BE =
            BLOCK_ENTITIES.register("energy_storage", () -> BlockEntityType.Builder.of(
                    EnergyStorageBlockEntity::new, ModBlocks.ENERGY_STORAGE.get()).build(null));

    public static final Supplier<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL_BE =
            BLOCK_ENTITIES.register("solar_panel", () -> BlockEntityType.Builder.of(
                    SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}