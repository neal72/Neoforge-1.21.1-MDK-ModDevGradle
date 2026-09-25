package net.neal.exomod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neal.exomod.ExoMod;
import net.neal.exomod.block.ModBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExoMod.MOD_ID);

    public static final Supplier<CreativeModeTab> EXO_TAB = CREATIVE_MODE_TAB.register("exo_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PINK_DIAMOND.get()))
                    .title(Component.translatable("creativetab.exo_mod.exo_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PINK_DIAMOND);
                        output.accept(ModItems.APPLE_JUICE);
                        output.accept(ModItems.TUNGSBAT);
                        output.accept(ModItems.TRALA_SPAWN_EGG);
                        output.accept(ModItems.TUNG_SPAWN_EGG);

                        output.accept(ModBlocks.SOUND_BLOCK);

                    })



                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
