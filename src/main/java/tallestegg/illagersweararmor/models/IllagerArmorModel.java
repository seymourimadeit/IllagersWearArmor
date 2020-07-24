package tallestegg.illagersweararmor.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class IllagerArmorModel extends BipedModel<LivingEntity>
{    
	public IllagerArmorModel(float p_i1148_1_) {
		super(p_i1148_1_);
	    this.bipedHead = new ModelRenderer(this, 0, 0);
	    this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i1148_1_);
	    this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
}
