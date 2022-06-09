package tallestegg.illagersweararmor.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Pillager;

public class PillagerBipedRenderer extends IllagerBipedRenderer<Pillager> {
    private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/pillager.png");

    public PillagerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Pillager p_115720_) {
        return PILLAGER;
    }
}
