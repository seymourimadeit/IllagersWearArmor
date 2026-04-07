package tallestegg.illagersweararmor.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.Pillager;
import tallestegg.illagersweararmor.client.model.render_states.IllagerBipedRenderState;

public class PillagerBipedRenderer extends IllagerBipedRenderer<Pillager> {
    private static final Identifier PILLAGER = Identifier.withDefaultNamespace("textures/entity/illager/pillager.png");

    public PillagerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(IllagerBipedRenderState state) {
        return PILLAGER;
    }
}
