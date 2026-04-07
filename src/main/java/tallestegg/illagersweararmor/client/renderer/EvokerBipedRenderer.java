package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.Evoker;
import tallestegg.illagersweararmor.client.model.render_states.IllagerBipedRenderState;

import java.util.Objects;

public class EvokerBipedRenderer extends IllagerBipedRenderer<Evoker> {
    private static final Identifier PILLAGER = Identifier.withDefaultNamespace("textures/entity/illager/evoker.png");

    public EvokerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this) {
            {
                Objects.requireNonNull(EvokerBipedRenderer.this);
            }

            public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, IllagerBipedRenderState state, float yRot, float xRot) {
                if (state.isCastingSpell) {
                    super.submit(poseStack, submitNodeCollector, lightCoords, state, yRot, xRot);
                }
            }
        });
    }

    @Override
    public Identifier getTextureLocation(IllagerBipedRenderState state) {
        return PILLAGER;
    }
}