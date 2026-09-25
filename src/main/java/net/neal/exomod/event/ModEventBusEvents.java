package net.neal.exomod.event;

import net.neal.exomod.ExoMod;
import net.neal.exomod.entity.ModEntities;
import net.neal.exomod.entity.custom.TralaEntity;
import net.neal.exomod.entity.custom.TungEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ExoMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TUNG.get(), TungEntity.createAttributes().build());
        event.put(ModEntities.TRALA.get(), TralaEntity.createAttributes().build());
    }
}