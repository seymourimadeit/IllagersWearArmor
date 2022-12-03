package tallestegg.illagersweararmor.client.model.mod_compat;

import baguchan.hunterillager.entity.Hunter;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public class HunterIllagerBipedModel<T extends Hunter> extends IllagerBipedModel<T> {
    private final ModelPart cape;
    private final ModelPart cube_r1;
    private ModelPart shoulderplate_r1;
    private ModelPart shoulderplate_r2;
    private ModelPart nose;
    private ModelPart head;

    public HunterIllagerBipedModel(ModelPart part) {
        super(part);
        this.cape = this.body.getChild("cape");
        this.cube_r1 = this.cape.getChild("cube_r1");
        this.shoulderplate_r1 = this.rightArm.getChild("shoulderplate_r1");
        this.shoulderplate_r2 = this.leftArm.getChild("shoulderplate_r2");
        this.getHead().visible = true;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        Body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(28, 13).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 14.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cape = Body.addOrReplaceChild("cape", CubeListBuilder.create(), PartPose.offset(0.0F,0.0F, 3.4F));

        PartDefinition cube_r1 = cape.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 32).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F,0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(49, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(44, 44).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition shoulderplate_r1 = right_arm.addOrReplaceChild("shoulderplate_r1", CubeListBuilder.create().texOffs(48, 32).addBox(-4.0F, -2.75F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(-6.0F, 0.25F, -2.5F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 36).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition shoulderplate_r2 = left_arm.addOrReplaceChild("shoulderplate_r2", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -2.75F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(17, 18).addBox(4.0F, 0.25F, -2.5F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -34.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 40).addBox(-5.0F, -30.0F, -4.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("arms",
                CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38)
                        .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.cape.xRot = 0.17453294F * limbSwingAmount * 1.75F;
        this.arms.visible = false;
        this.leftArm.visible = true;
        this.rightArm.visible = true;
        this.shoulderplate_r2.visible = entityIn.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
        this.shoulderplate_r1.visible = entityIn.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
    }

}
