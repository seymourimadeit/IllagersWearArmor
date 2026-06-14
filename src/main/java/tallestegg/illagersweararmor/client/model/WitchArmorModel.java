package tallestegg.illagersweararmor.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.joml.Vector3f;

public class WitchArmorModel extends VillagerArmorModel<HumanoidRenderState> {
    public WitchArmorModel(ModelPart root) {
        super(root);
        this.body.offsetScale(new Vector3f(-0.1F, 0.0F, 0.1F));
        this.body.y += 2;
    }
    public static ArmorModelSet<MeshDefinition> createArmorMeshSet(CubeDeformation innerCubeDeformation, CubeDeformation outerCubeDeformation) {
        return createArmorMeshSet(WitchArmorModel::createBaseMesh, ADULT_ARMOR_PARTS_PER_SLOT, innerCubeDeformation, outerCubeDeformation);
    }

    private static MeshDefinition createBaseMesh(CubeDeformation cubeDeformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(cubeDeformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild(
                "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation), PartPose.ZERO
        );
        head.addOrReplaceChild(
                "nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F)
        );
        PartDefinition arms = partdefinition.addOrReplaceChild(
                "arms",
                CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
                "hat",
                CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation.extend(0.5F)),
                PartPose.offset(0.0F, 1.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
                "body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.7F,0,0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -1.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.4f)),
                PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F,-0.75F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.4f)),
                PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F,-0.75F, 0.0F, 0.0F));
        return meshdefinition;
    }

    @Override
    public void setupAnim(HumanoidRenderState state) {
    }
}
