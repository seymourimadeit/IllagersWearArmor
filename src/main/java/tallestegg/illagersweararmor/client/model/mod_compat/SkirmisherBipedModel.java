package tallestegg.illagersweararmor.client.model.mod_compat;

import com.izofar.takesapillage.entity.Skirmisher;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.registries.ForgeRegistries;
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public class SkirmisherBipedModel<T extends Skirmisher> extends HumanoidModel<T> {
    public SkirmisherBipedModel(ModelPart pRoot) {
        super(pRoot);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-0.5F, -15.0F, -5.0F, 1.0F, 6.0F, 10.0F, new CubeDeformation(-0.5F)), PartPose.ZERO);
        partdefinition1.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.leftArm.visible = true;
        this.rightArm.visible = true;
        AbstractIllager.IllagerArmPose armpose = entityIn.getArmPose();
        boolean flag = armpose == AbstractIllager.IllagerArmPose.CROSSED && IWAConfig.crossArms;
        if (armpose != null) {
            switch (armpose) {
                case ATTACKING:
                    if (entityIn.getMainHandItem().isEmpty()) {
                        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
                    } else {
                        this.holdWeaponHigh(entityIn);
                    }
                    break;
                case SPELLCASTING:
                    this.rightArm.z = 0.0F;
                    this.rightArm.x = -5.0F;
                    this.leftArm.z = 0.0F;
                    this.leftArm.x = 5.0F;
                    this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
                    this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
                    this.rightArm.zRot = 2.3561945F;
                    this.leftArm.zRot = -2.3561945F;
                    this.rightArm.yRot = 0.0F;
                    this.leftArm.yRot = 0.0F;
                    break;
                case CELEBRATING:
                    this.rightArm.z = 0.0F;
                    this.rightArm.x = -5.0F;
                    this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
                    this.rightArm.zRot = 2.670354F;
                    this.rightArm.yRot = 0.0F;
                    this.leftArm.z = 0.0F;
                    this.leftArm.x = 5.0F;
                    this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
                    this.leftArm.zRot = -2.3561945F;
                    this.leftArm.yRot = 0.0F;
                    break;
                default:
                    break;
            }
        }
        this.setupAttackAnimation(entityIn, ageInTicks);
    }

    private void holdWeaponHigh(T pMob) {
        if (pMob.isLeftHanded()) {
            this.leftArm.xRot = -1.8F;
        } else {
            this.rightArm.xRot = -1.8F;
        }
    }

    @Override
    protected void setupAttackAnimation(T pLivingEntity, float pAgeInTicks) {
        if (this.attackTime > 0.0F && pLivingEntity.getArmPose() == AbstractIllager.IllagerArmPose.ATTACKING) {
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, pLivingEntity, this.attackTime, pAgeInTicks);
        } else {
            super.setupAttackAnimation(pLivingEntity, pAgeInTicks);
        }
    }
}
