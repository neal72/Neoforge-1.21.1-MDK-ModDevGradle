package net.neal.exomod.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neal.exomod.util.ModTags;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier PINK_DIAMOND = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_PINK_DIAMOND_TOOL, // blocks this tier CAN'T mine
            1500,  // durability
            5f,    // mining speed
            4f,    // attack damage bonus
            25,    // enchantability
            () -> Ingredient.of(ModItems.PINK_DIAMOND.get()));
}