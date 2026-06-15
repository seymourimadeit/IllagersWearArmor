package tallestegg.illagersweararmor.client.renderer.layers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Witch;

import static tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer.armorCrossArms;
import static tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer.illagerArmorRendering;

public class WitchArmorLayer<T extends Witch, M extends WitchModel<T>, A extends HumanoidModel<T>> extends NonHumanoidArmorLayer<T, M, A> {
    public WitchArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, modelManager);
    }

    @Override
    protected void setPartVisibility(A model, EquipmentSlot slot) {
        switch(slot) {
            case CHEST:
                model.rightArm.x -= 5;
                model.leftArm.x += 5;
                break;
        }
        illagerArmorRendering(model, slot);
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
}

