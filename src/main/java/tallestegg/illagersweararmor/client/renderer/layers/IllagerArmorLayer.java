package tallestegg.illagersweararmor.client.renderer.layers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;

public class IllagerArmorLayer <T extends AbstractIllager, A extends IllagerModel<T>> extends NonHumanoidArmorLayer<T, A> {
    public IllagerArmorLayer(RenderLayerParent<T, A> pRenderer, EntityModelSet modelSets, ModelManager manager) {
        super(pRenderer, modelSets, manager);
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

    protected void setPartVisibility(T illager, HumanoidModel pModel, EquipmentSlot pSlot) {
        illagerArmorRendering(pModel, pSlot);
    }
}
