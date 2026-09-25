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

    public static final DeferredItem<Item> PINK_DIAMOND_SWORD = ITEMS.register("pink_diamond_sword",
            () -> new SwordItem(ModToolTiers.PINK_DIAMOND, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PINK_DIAMOND, 4, 2.8F))));

    public static final DeferredItem<Item> PINK_DIAMOND_PICKAXE = ITEMS.register("pink_diamond_pickaxe",
            () -> new PickaxeItem(ModToolTiers.PINK_DIAMOND, new Item.Properties()));


    public static final DeferredItem<Item> PINK_DIAMOND_HELMET = ITEMS.register("pink_diamond_helmet",
            () -> new ArmorItem(ModArmorMaterials.PINK_DIAMOND, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredItem<Item> PINK_DIAMOND_CHESTPLATE = ITEMS.register("pink_diamond_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PINK_DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredItem<Item> PINK_DIAMOND_LEGGINGS = ITEMS.register("pink_diamond_leggings",
            () -> new ArmorItem(ModArmorMaterials.PINK_DIAMOND, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredItem<Item> PINK_DIAMOND_BOOTS = ITEMS.register("pink_diamond_boots",
            () -> new ArmorItem(ModArmorMaterials.PINK_DIAMOND, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(37))));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
