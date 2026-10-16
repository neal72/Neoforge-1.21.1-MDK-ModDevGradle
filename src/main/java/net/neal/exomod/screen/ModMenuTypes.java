package net.neal.exomod.screen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neal.exomod.ExoMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ExoMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<EnergyStorageMenu>> ENERGY_STORAGE_MENU =
            MENUS.register("energy_storage_menu", () -> IMenuTypeExtension.create(EnergyStorageMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}