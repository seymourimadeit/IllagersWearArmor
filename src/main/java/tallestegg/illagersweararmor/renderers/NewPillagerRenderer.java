package tallestegg.illagersweararmor.renderers;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.util.ResourceLocation;
import tallestegg.illagersweararmor.models.IllagerBipedModel;

public class NewPillagerRenderer extends NewIllagerRenderer<PillagerEntity> 
{
	private static final ResourceLocation PILLAGER_TEXTURE = new ResourceLocation("textures/entity/illager/pillager.png");

	public NewPillagerRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new IllagerBipedModel<>(0.0F, 0.0F, 64, 64), 0.5f);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
	}
	
	@Override
	public ResourceLocation getEntityTexture(PillagerEntity entity) 
	{
	   return PILLAGER_TEXTURE;
	}
}
