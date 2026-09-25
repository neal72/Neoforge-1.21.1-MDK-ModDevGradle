package net.neal.exomod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neal.exomod.entity.animations.ModAnimationDefinitions;
import net.neal.exomod.entity.custom.TralaEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;


public class TralaModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart trala;
    private final ModelPart head;

    public TralaModel(ModelPart root) {
        this.trala = root.getChild("trala");
        this.head = trala.getChild("body").getChild("torso").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition trala = partdefinition.addOrReplaceChild("trala", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition body = trala.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(1.5F, 2.5F, -4.0F));

        PartDefinition back_leg = body.addOrReplaceChild("back_leg", CubeListBuilder.create().texOffs(18, 25).addBox(-4.0F, 0.0F, 6.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(21, 22).addBox(-4.0F, -2.0F, 7.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.5F, -2.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.5F, -2.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 11).addBox(-3.0F, -3.0F, 1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition head_fin_r1 = head.addOrReplaceChild("head_fin_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.3F, 6.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -2.0F, 9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-4.0F, -2.0F, 7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -0.5F, 0.0F));

        PartDefinition tail_fin = tail.addOrReplaceChild("tail_fin", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, 10.0F));

        PartDefinition bottom_r1 = tail_fin.addOrReplaceChild("bottom_r1", CubeListBuilder.create().texOffs(11, 11).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition top_r1 = tail_fin.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 1.5F, 1.0F));

        PartDefinition cube_r1 = right_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(7, 26).addBox(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 25).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 1.0F));

        PartDefinition cube_r2 = left_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 25).addBox(-1.0F, 1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 26).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition side_fins = body.addOrReplaceChild("side_fins", CubeListBuilder.create(), PartPose.offset(-5.5F, 0.5F, 4.0F));

        PartDefinition cube_r3 = side_fins.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(18, 17).addBox(0.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r4 = side_fins.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(9, 17).addBox(-1.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

            this.animateWalk(ModAnimationDefinitions.TRALA_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
            if (entity instanceof TralaEntity tralaEntity) {
                this.animate(tralaEntity.idleAnimationState, ModAnimationDefinitions.TRALA_IDLE, ageInTicks, 1f);
            }
        }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -1.0F, 1.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -1.0F, 1.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        trala.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return trala;
    }
}