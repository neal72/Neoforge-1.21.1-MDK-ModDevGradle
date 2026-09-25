package net.neal.exomod.item;

import net.minecraft.world.item.*;
import net.neal.exomod.ExoMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neal.exomod.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExoMod.MOD_ID);

    public static final DeferredItem<Item> APPLE_JUICE = ITEMS.register("apple_juice",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PINK_DIAMOND = ITEMS.register("pink_diamond",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TUNGSBAT = ITEMS.register("tungsbat",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> TRALA_SPAWN_EGG = ITEMS.register("trala_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.TRALA, 0x7e9680, 0xc5d1c5,
                    new Item.Properties()));

    public static final DeferredItem<Item> TUNG_SPAWN_EGG = ITEMS.register("tung_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.TUNG, 0x7e2688, 0xc5a1c5,
                    new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
