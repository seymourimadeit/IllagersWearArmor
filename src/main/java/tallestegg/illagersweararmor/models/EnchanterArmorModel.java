package tallestegg.illagersweararmor.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;

public class EnchanterArmorModel<T extends AbstractIllagerEntity> extends BipedModel<T> {
    public EnchanterArmorModel(float modelSizeIn) {
        super(modelSizeIn);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -15.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSizeIn);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
    }
}
