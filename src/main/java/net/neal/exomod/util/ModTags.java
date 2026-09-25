package net.neal.exomod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neal.exomod.ExoMod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_PINK_DIAMOND_TOOL = createTag("needs_pink_diamond_tool");
        public static final TagKey<Block> INCORRECT_FOR_PINK_DIAMOND_TOOL = createTag("incorrect_for_pink_diamond_tool");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ExoMod.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ExoMod.MOD_ID, name));
        }
    }
}