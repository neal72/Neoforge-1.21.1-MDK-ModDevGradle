package net.neal.exomod.event;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.neal.exomod.ExoMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber(modid = ExoMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
        }

        if (event.getType() == VillagerProfession.LIBRARIAN) {
        }
    }
}