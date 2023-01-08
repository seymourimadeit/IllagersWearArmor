/*package tallestegg.illagersweararmor.client.model.mod_compat;

import com.izofar.takesapillage.entity.Skirmisher;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
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
}*/
