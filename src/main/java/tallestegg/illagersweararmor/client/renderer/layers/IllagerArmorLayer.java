package tallestegg.illagersweararmor.client.renderer.layers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;
import org.joml.Vector3f;

public class IllagerArmorLayer<T extends AbstractIllager, M extends IllagerModel<T>, A extends HumanoidModel<T>> extends NonHumanoidArmorLayer<T, M, A> {
    public IllagerArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
        super(renderer,  innerModel, outerModel, modelManager);
    }

    @Override
    protected void setPartVisibility(A model, EquipmentSlot slot) {
        illagerArmorRendering(model, slot);
    }

    public static void illagerArmorRendering(HumanoidModel pModel, EquipmentSlot pSlot) {
        pModel.setAllVisible(false);
        switch (pSlot) {
            case HEAD:
                pModel.head.visible = true;
                pModel.hat.visible = true;
                pModel.head.y = -2;
                break;
            case CHEST:
                pModel.body.visible = true;
                pModel.rightArm.visible = true;
                pModel.leftArm.visible = true;
                pModel.body.offsetScale(new Vector3f(0.0F, 0.0F, 0.2F));
                pModel.rightArm.x -= 1;
                pModel.leftArm.x += 1;
                pModel.rightArm.offsetScale(new Vector3f(0.0F, 0.0F, 0.2F));
                pModel.leftArm.offsetScale(new Vector3f(0.0F, 0.0F, 0.2F));
                break;
            case LEGS:
                pModel.body.visible = true;
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
                pModel.body.offsetScale(new Vector3f(0.0F, 0.0F, 0.3F));
                pModel.rightLeg.offsetScale(new Vector3f(0.1F, 0.0F, 0.5F));
                pModel.leftLeg.offsetScale(new Vector3f(0.1F, 0.0F, 0.5F));
                break;
            case FEET:
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
                pModel.rightLeg.offsetScale(new Vector3f(0.1F, 0.0F, 0.4F));
                pModel.leftLeg.offsetScale(new Vector3f(0.1F, 0.0F, 0.4F));
        }
    }

    @Override
    protected void copyPropertiesTo(HumanoidModel model, T livingEntity) {
        model.head.copyFrom(this.getParentModel().head);
        model.hat.copyFrom(this.getParentModel().hat);
        model.rightArm.copyFrom(this.getParentModel().rightArm);
        model.leftArm.copyFrom(this.getParentModel().leftArm);
        model.body.copyFrom(this.getParentModel().root);
        model.rightLeg.copyFrom(this.getParentModel().rightLeg);
        model.leftLeg.copyFrom(this.getParentModel().leftLeg);
        boolean flag = livingEntity.getArmPose() == AbstractIllager.IllagerArmPose.CROSSED;
        if (flag) {
            armorCrossArms(model);
        }
    }

    public static void armorCrossArms(HumanoidModel model) {
        model.leftArm.y = 3.0F;
        model.leftArm.z = -1.0F;
        model.leftArm.xRot = -0.75F;
        model.rightArm.y = 3.0F;
        model.rightArm.z = -1.0F;
        model.rightArm.xRot = -0.75F;
    }
}

