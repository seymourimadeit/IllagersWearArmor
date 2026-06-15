package tallestegg.illagersweararmor.client.renderer.layers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Witch;

import static tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer.armorCrossArms;
import static tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer.illagerArmorRendering;

public class WitchArmorLayer<T extends Witch> extends NonHumanoidArmorLayer<T, WitchModel<T>> {
    public WitchArmorLayer(RenderLayerParent<T, WitchModel<T>> pRenderer, EntityModelSet modelSets, ModelManager manager) {
        super(pRenderer, modelSets, manager);
    }

    @Override
    protected void copyPropertiesTo(HumanoidModel model, T livingEntity) {
        model.head.copyFrom(this.getParentModel().getHead());
        model.hat.copyFrom(this.getParentModel().hat);
        model.body.copyFrom(this.getParentModel().root());
        model.rightLeg.copyFrom(this.getParentModel().rightLeg);
        model.leftLeg.copyFrom(this.getParentModel().leftLeg);
        model.leftArm.copyFrom(this.getParentModel().root());
        model.rightArm.copyFrom(this.getParentModel().root());
        armorCrossArms(model);
    }

    @Override
    protected void setPartVisibility(T witch, HumanoidModel model, EquipmentSlot slot) {
        switch(slot) {
            case CHEST:
                model.rightArm.x -= 5;
                model.leftArm.x += 5;
                break;
        }
        illagerArmorRendering(model, slot);
    }
}
