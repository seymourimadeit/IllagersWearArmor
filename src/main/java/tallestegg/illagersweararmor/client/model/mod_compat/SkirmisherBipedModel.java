package tallestegg.illagersweararmor.client.model.mod_compat;

import com.izofar.takesapillage.entity.Skirmisher;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public class SkirmisherBipedModel<T extends Skirmisher> extends IllagerBipedModel<T> {
    public SkirmisherBipedModel(ModelPart pRoot) {
        super(pRoot);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = IllagerBipedModel.createMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-0.5F, -15.0F, -5.0F, 1.0F, 6.0F, 10.0F, new CubeDeformation(-0.5F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.arms.visible = false;
        this.leftArm.visible = true;
        this.rightArm.visible = true;
    }
a
    @Override
    public void doArmPoses(AbstractIllager.IllagerArmPose armpose, T entity, float ageInTicks) {
        if (armpose != null) {
            switch (armpose) {
                case ATTACKING:
                    if (entity.getMainHandItem().isEmpty()) {
                        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
                    } else {
                        this.holdWeaponHigh(entity);
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
    }
}
