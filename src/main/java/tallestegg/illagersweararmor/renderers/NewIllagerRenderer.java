package tallestegg.illagersweararmor.renderers;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import tallestegg.illagersweararmor.models.IllagerBipedModel;

public abstract class NewIllagerRenderer<T extends AbstractIllagerEntity> extends BipedRenderer<T, IllagerBipedModel<T>> {
    protected NewIllagerRenderer(EntityRendererManager p_i50966_1_, IllagerBipedModel<T> p_i50966_2_, float p_i50966_3_) {
        super(p_i50966_1_, p_i50966_2_, p_i50966_3_);
    }
}
