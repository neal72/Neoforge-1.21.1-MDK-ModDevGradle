package net.neal.exomod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.neal.exomod.ExoMod;
import net.neal.exomod.entity.custom.TungEntity;

public class TungRenderer extends MobRenderer<TungEntity, TungModel<TungEntity>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ExoMod.MOD_ID, "textures/entity/tung.png");

    public TungRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TungModel<>(pContext.bakeLayer(ModModelLayers.TUNG_LAYER)), 0.3f);
        this.addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer())); // draws the bat
    }

    @Override
    public ResourceLocation getTextureLocation(TungEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(TungEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.scale(2.0f, 2.0f, 2.0f);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }
}