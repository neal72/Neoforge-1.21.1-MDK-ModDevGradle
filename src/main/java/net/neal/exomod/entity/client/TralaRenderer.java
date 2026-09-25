package net.neal.exomod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neal.exomod.ExoMod;
import net.neal.exomod.entity.custom.TralaEntity;

public class TralaRenderer extends MobRenderer<TralaEntity, TralaModel<TralaEntity>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ExoMod.MOD_ID, "textures/entity/trala.png");

    public TralaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TralaModel<>(pContext.bakeLayer(ModModelLayers.TRALA_LAYER)), 0.1f); // shadow size
    }

    @Override
    public ResourceLocation getTextureLocation(TralaEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(TralaEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.scale(2.0f, 2.0f, 2.0f);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }
}