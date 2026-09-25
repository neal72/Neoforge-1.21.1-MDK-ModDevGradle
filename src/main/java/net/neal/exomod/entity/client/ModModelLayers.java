package net.neal.exomod.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neal.exomod.ExoMod;

public class ModModelLayers {
    public static final ModelLayerLocation TRALA_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(ExoMod.MOD_ID, "trala_layer"), "main");

    public static final ModelLayerLocation TUNG_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(ExoMod.MOD_ID, "tung_layer"), "main");
}