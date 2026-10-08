package net.neal.exomod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Blocks;
import net.neal.exomod.block.entity.EnergyStorageBlockEntity;

public class EnergyStorageRenderer implements BlockEntityRenderer<EnergyStorageBlockEntity> {

    public EnergyStorageRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(EnergyStorageBlockEntity be, float partialTick, PoseStack pose,
                       MultiBufferSource buffer, int light, int overlay) {
        float fill = be.getFillPercent();
        if (fill <= 0) return;   // empty = no core

        float time = be.getLevel().getGameTime() + partialTick;

        pose.pushPose();
        pose.translate(0.5, 0.5, 0.5);                      // move to block centre
        pose.mulPose(Axis.YP.rotationDegrees(time * 2f));   // spin
        pose.mulPose(Axis.XP.rotationDegrees(time * 1.3f)); // tumble
        float size = 0.15f + 0.35f * fill;                  // grows as it charges
        pose.scale(size, size, size);
        pose.translate(-0.5, -0.5, -0.5);

        // Placeholder core: a redstone block, drawn full-bright so it "glows"
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                Blocks.REDSTONE_BLOCK.defaultBlockState(), pose, buffer,
                LightTexture.FULL_BRIGHT, overlay);

        pose.popPose();
    }
}