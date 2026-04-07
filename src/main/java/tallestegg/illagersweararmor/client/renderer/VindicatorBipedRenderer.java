    package tallestegg.illagersweararmor.client.renderer;

    import com.mojang.blaze3d.vertex.PoseStack;
    import net.minecraft.client.renderer.SubmitNodeCollector;
    import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
    import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
    import net.minecraft.resources.Identifier;
    import net.minecraft.world.entity.monster.illager.Vindicator;
    import tallestegg.illagersweararmor.client.model.render_states.IllagerBipedRenderState;

    import java.util.Objects;

    public class VindicatorBipedRenderer extends IllagerBipedRenderer<Vindicator> {

    private static final Identifier PILLAGER = Identifier.withDefaultNamespace("textures/entity/illager/vindicator.png");

    public VindicatorBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this) {
            {
                Objects.requireNonNull(VindicatorBipedRenderer.this);
            }

            public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, IllagerBipedRenderState state, float yRot, float xRot) {
                if (state.isAggressive) {
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
