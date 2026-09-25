package net.neal.exomod.event;

import net.neal.exomod.ExoMod;
import net.neal.exomod.entity.ModEntities;
import net.neal.exomod.entity.client.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ExoMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.TRALA_LAYER, TralaModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TUNG_LAYER, TungModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TRALA.get(), TralaRenderer::new);
        event.registerEntityRenderer(ModEntities.TUNG.get(), TungRenderer::new);
    }
}