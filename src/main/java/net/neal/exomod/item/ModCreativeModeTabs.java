package net.neal.exomod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neal.exomod.ExoMod;
import net.neal.exomod.block.ModBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExoMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXO_TAB =
            CREATIVE_MODE_TABS.register("exo_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.exo_mod.exo_tab"))
                    .icon(() -> new ItemStack(ModItems.PINK_DIAMOND.get()))
                    .displayItems((parameters, output) ->
                            ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
