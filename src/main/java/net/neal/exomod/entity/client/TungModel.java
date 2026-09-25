package net.neal.exomod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.neal.exomod.entity.animations.ModAnimationDefinitions;
import net.neal.exomod.entity.custom.TungEntity;

public class TungModel<T extends Entity> extends HierarchicalModel<T> implements ArmedModel {
	private final ModelPart tung;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart rightArm;
	private final ModelPart rightHand;

	public TungModel(ModelPart root) {
		this.tung = root.getChild("tung");
		this.body = tung.getChild("body");
		this.head = body.getChild("torso").getChild("head");
		this.rightArm = body.getChild("right arm");
		this.rightHand = rightArm.getChild("right_hand");
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
		this.tung.translateAndRotate(poseStack);
		this.body.translateAndRotate(poseStack);
		this.rightArm.translateAndRotate(poseStack);
		this.rightHand.translateAndRotate(poseStack);


		poseStack.scale(0.5f, 0.5f, 0.5f);
		poseStack.translate(0.200f, -0.45f, -0.5f); // cancels vanilla's player-arm offset


		// fine-tuning: adjust these numbers until the handle sits in his hand
		poseStack.translate(0.0f, 0.25f, 1.0f);

		poseStack.mulPose(Axis.XP.rotationDegrees(-45f));  // tilt forward/back
		poseStack.mulPose(Axis.YP.rotationDegrees(0f));  // turn around its length
		poseStack.mulPose(Axis.ZP.rotationDegrees(0f));  // tilt left/right
	}


	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tung = partdefinition.addOrReplaceChild("tung", CubeListBuilder.create(), PartPose.offset(2.0F, 20.0F, 0.0F));

		PartDefinition body = tung.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, -7.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -14.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(20, 14).addBox(-1.8F, -12.5F, -2.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.3F, -8.2F, -2.1F, 0.3F, 0.3F, 0.1F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.1F, -8.6F, -2.1F, 0.3F, 0.5F, 0.1F, new CubeDeformation(0.0F))
				.texOffs(12, 22).addBox(-0.4F, -10.3F, -2.3F, 1.0F, 1.4F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 12).addBox(-4.6F, -10.3F, -2.3F, 1.0F, 1.4F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 10).addBox(-0.6F, -9.9F, -2.1F, 1.0F, 1.4F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(20, 17).addBox(-4.2F, -12.5F, -2.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 24).addBox(-2.4F, -10.2F, -2.3F, 0.8F, 1.6F, 0.3F, new CubeDeformation(0.0F))
				.texOffs(10, 25).addBox(-2.4F, -9.4F, -2.9F, 0.8F, 0.6F, 0.3F, new CubeDeformation(0.0F))
				.texOffs(16, 24).addBox(-2.9F, -9.4F, -2.3F, 1.8F, 0.6F, 0.3F, new CubeDeformation(0.0F))
				.texOffs(8, 25).addBox(-2.4F, -9.9F, -2.6F, 0.8F, 1.2F, 0.3F, new CubeDeformation(0.0F))
				.texOffs(12, 24).addBox(-4.4F, -9.9F, -2.1F, 1.0F, 1.4F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.0F, -8.2F, -2.1F, 0.3F, 0.3F, 0.1F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.2F, -8.6F, -2.1F, 0.3F, 0.5F, 0.1F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.7F, -8.0F, -2.1F, 1.4F, 0.2F, 0.1F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right arm", CubeListBuilder.create(), PartPose.offset(-4.25F, -3.1443F, 0.2091F));

		PartDefinition tum_r1 = right_arm.addOrReplaceChild("tum_r1", CubeListBuilder.create().texOffs(4, 22).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition tum_r2 = right_arm.addOrReplaceChild("tum_r2", CubeListBuilder.create().texOffs(20, 20).addBox(-3.0F, -10.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 5.6443F, 2.7909F, 0.3927F, 0.0F, 0.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition tum_r3 = right_hand.addOrReplaceChild("tum_r3", CubeListBuilder.create().texOffs(22, 24).addBox(0.3F, 0.5F, -0.5F, 0.2F, 1.1F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 25).addBox(0.3F, 1.6F, -0.4F, 0.2F, 0.9F, 0.9F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.3F, 0.5F, -0.9F, 0.2F, 2.0F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(20, 24).addBox(0.5F, 0.5F, -0.5F, 0.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(0.25F, -3.1443F, 0.2091F));

		PartDefinition tum_r4 = left_arm.addOrReplaceChild("tum_r4", CubeListBuilder.create().texOffs(8, 22).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition tum_r5 = left_arm.addOrReplaceChild("tum_r5", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.4F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tum_r6 = left_hand.addOrReplaceChild("tum_r6", CubeListBuilder.create().texOffs(24, 20).addBox(0.1F, 0.5F, -0.5F, 0.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 22).addBox(0.5F, 0.5F, -0.5F, 0.2F, 1.1F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(6, 25).addBox(0.5F, 1.6F, -0.4F, 0.2F, 0.9F, 0.9F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.5F, 0.5F, -0.9F, 0.2F, 2.0F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_leg = tung.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 14).addBox(-3.5F, -4.0F, -4.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, 4.0F, 4.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(16, 0).addBox(-4.0F, 0.0F, -9.0F, 2.0F, 0.5F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.4F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.0F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.5F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.0F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(16, 10).addBox(-3.8F, -0.3F, -7.0F, 1.6F, 0.3F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 2.0F));

		PartDefinition left_leg = tung.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 19).addBox(2.5F, -4.0F, -4.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, 4.0F, 4.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(0, 0).addBox(2.0F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.6F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(2.6F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F))
				.texOffs(16, 12).addBox(2.2F, -0.3F, -7.0F, 1.6F, 0.3F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 5).addBox(2.0F, 0.0F, -9.0F, 2.0F, 0.5F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.1F, 0.2F, -9.4F, 0.4F, 0.3F, 0.4F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 2.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.TUNG_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		if (entity instanceof TungEntity tungEntity) {
			this.animate(tungEntity.idleAnimationState, ModAnimationDefinitions.TUNG_IDLE, ageInTicks, 1f);
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
		tung.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return tung;
	}
}