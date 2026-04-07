package tallestegg.illagersweararmor.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.client.model.render_states.IllagerBipedRenderState;

public class IllagerBipedModel<S extends IllagerBipedRenderState> extends HumanoidModel<S> {
    public ModelPart nose = this.head.getChild("nose");
    public ModelPart jacket = this.body.getChild("jacket");
    public ModelPart arms;

    public IllagerBipedModel(ModelPart part) {
        super(part);
        this.arms = part.getChild("arms");
        this.hat.visible = false;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F,
                8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
        partdefinition1.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F,
                6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("arms",
                CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38)
                        .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partdefinition2.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(S state) {
        super.setupAnim(state);
        AbstractIllager.IllagerArmPose armpose = state.armPose;
        this.jacket.visible = !state.isWearingChestplateOrLegging;
        this.arms.y = 3.0F;
        this.arms.z = -1.0F;
        this.arms.xRot = -0.75F;
        boolean flag = armpose == AbstractIllager.IllagerArmPose.CROSSED && IWAConfig.crossArms;
        this.arms.visible = flag;
        if (flag) {
            this.leftArm.y = 3.0F;
            this.leftArm.z = -1.0F;
            this.leftArm.xRot = -0.75F;
            this.rightArm.y = 3.0F;
            this.rightArm.z = -1.0F;
            this.rightArm.xRot = -0.75F;
        }
        this.leftArm.visible = !flag;
        this.rightArm.visible = !flag;
        if (armpose != null) {
            switch (armpose) {
                case ATTACKING:
                    if (state.getMainHandItemState().isEmpty()) {
                        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, state);
                    } else {
                        this.holdWeaponHigh(state);
                    }
                    break;
                case SPELLCASTING:
                    this.rightArm.z = 0.0F;
                    this.rightArm.x = -5.0F;
                    this.leftArm.z = 0.0F;
                    this.leftArm.x = 5.0F;
                    this.rightArm.xRot = Mth.cos(state.ageInTicks * 0.6662F) * 0.25F;
                    this.leftArm.xRot = Mth.cos(state.ageInTicks * 0.6662F) * 0.25F;
                    this.rightArm.zRot = 2.3561945F;
                    this.leftArm.zRot = -2.3561945F;
                    this.rightArm.yRot = 0.0F;
                    this.leftArm.yRot = 0.0F;
                    break;
                case CELEBRATING:
                    this.rightArm.z = 0.0F;
                    this.rightArm.x = -5.0F;
                    this.rightArm.xRot = Mth.cos(state.ageInTicks * 0.6662F) * 0.05F;
                    this.rightArm.zRot = 2.670354F;
                    this.rightArm.yRot = 0.0F;
                    this.leftArm.z = 0.0F;
                    this.leftArm.x = 5.0F;
                    this.leftArm.xRot = Mth.cos(state.ageInTicks * 0.6662F) * 0.05F;
                    this.leftArm.zRot = -2.3561945F;
                    this.leftArm.yRot = 0.0F;
                    break;
                default:
                    break;
            }
            this.setupAttackAnimation(state);
        }
    }

    private void holdWeaponHigh(S pMob) {
        if (pMob.mainArm == HumanoidArm.LEFT) {
            this.leftArm.xRot = -1.8F;
        } else {
            this.rightArm.xRot = -1.8F;
        }
    }

    @Override
    protected void setupAttackAnimation(S pLivingEntity) {
        if (pLivingEntity.attackTime > 0.0F && pLivingEntity.armPose == AbstractIllager.IllagerArmPose.ATTACKING) {
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, pLivingEntity.mainArm, pLivingEntity.attackTime, pLivingEntity.ageInTicks);
        } else {
            super.setupAttackAnimation(pLivingEntity);
        }
    }
}
