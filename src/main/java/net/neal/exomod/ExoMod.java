package net.neal.exomod;

import net.minecraft.world.item.CreativeModeTabs;
import net.neal.exomod.block.ModBlocks;
import net.neal.exomod.item.ModArmorMaterials;
import net.neal.exomod.item.ModCreativeModeTabs;
import net.neal.exomod.item.ModItems;
import net.neal.exomod.sound.ModSounds;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neal.exomod.entity.ModEntities;

@Mod(ExoMod.MOD_ID)
public class ExoMod {
    public static final String MOD_ID = "exo_mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExoMod(IEventBus modEventBus, ModContainer modContainer) {

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModArmorMaterials.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

@SubscribeEvent
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.APPLE_JUICE);
            event.accept(ModItems.PINK_DIAMOND);
            event.accept(ModItems.TUNGSBAT);
            event.accept(ModItems.TRALA_SPAWN_EGG);
            event.accept(ModItems.TUNG_SPAWN_EGG);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.SOUND_BLOCK);

        }
    }
}
