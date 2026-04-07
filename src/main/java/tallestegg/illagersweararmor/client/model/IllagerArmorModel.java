package tallestegg.illagersweararmor.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.client.model.render_states.IllagerBipedRenderState;
import tallestegg.illagersweararmor.client.model.render_states.WitchBipedRenderState;

public class IllagerArmorModel<T extends IllagerBipedRenderState> extends HumanoidModel<T> {
    public IllagerArmorModel(ModelPart part) {
        super(part);
    }

    public static ArmorModelSet<MeshDefinition> createArmorMeshSet(CubeDeformation innerCubeDeformation, CubeDeformation outerCubeDeformation) {
        return createArmorMeshSet(IllagerArmorModel::createBaseMesh, ADULT_ARMOR_PARTS_PER_SLOT, innerCubeDeformation, outerCubeDeformation);
    }

    private static MeshDefinition createBaseMesh(CubeDeformation cubeDeformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(cubeDeformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild(
                "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation), PartPose.ZERO
        );
        partdefinition.addOrReplaceChild(
                "hat",
                CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation.extend(0.5F)),
                PartPose.offset(0.0F, 1.0F, 0.0F)
        );
        return meshdefinition;
    }

    @Override
    public void setupAnim(T state) {
        super.setupAnim(state);
        AbstractIllager.IllagerArmPose armpose = state.armPose;
        boolean flag = armpose == AbstractIllager.IllagerArmPose.CROSSED && IWAConfig.crossArms || state instanceof WitchBipedRenderState;
        if (flag) {
            this.leftArm.y = 3.0F;
            this.leftArm.z = -1.0F;
            this.leftArm.xRot = -0.75F;
            this.rightArm.y = 3.0F;
            this.rightArm.z = -1.0F;
            this.rightArm.xRot = -0.75F;
        }
    }
}
