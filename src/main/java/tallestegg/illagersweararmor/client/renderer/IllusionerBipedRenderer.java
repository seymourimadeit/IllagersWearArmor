package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import tallestegg.illagersweararmor.client.model.render_states.IllagerBipedRenderState;

import java.util.Objects;

public class IllusionerBipedRenderer extends IllagerBipedRenderer<Illusioner> {
    private static final Identifier PILLAGER = Identifier.withDefaultNamespace("textures/entity/illager/illusioner.png");

    public IllusionerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this) {
            {
                Objects.requireNonNull(IllusionerBipedRenderer.this);
            }
            public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, IllagerBipedRenderState state, float yRot, float xRot) {
                if (state.isCastingSpell || state.isAggressive) {
                    super.submit(poseStack, submitNodeCollector, lightCoords, state, yRot, xRot);
                }
            }
        });
    }

    @Override
    public Identifier getTextureLocation(IllagerBipedRenderState state) {
        return PILLAGER;
    }
    @Override
    public void submit(IllagerBipedRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.isInvisible) {
            Vec3[] offsets = state.illusionOffsets;

            for (int i = 0; i < offsets.length; i++) {
                poseStack.pushPose();
                poseStack.translate(
                        offsets[i].x + Mth.cos(i + state.ageInTicks * 0.5F) * 0.025,
                        offsets[i].y + Mth.cos(i + state.ageInTicks * 0.75F) * 0.0125,
                        offsets[i].z + Mth.cos(i + state.ageInTicks * 0.7F) * 0.025
                );
                super.submit(state, poseStack, submitNodeCollector, camera);
                poseStack.popPose();
            }
        } else {
            super.submit(state, poseStack, submitNodeCollector, camera);
        }
    }

    @Override
    protected boolean isBodyVisible(IllagerBipedRenderState state) {
        return true;
    }

    @Override
    protected AABB getBoundingBoxForCulling(Illusioner entity) {
        return super.getBoundingBoxForCulling(entity).inflate(3.0, 0.0, 3.0);
    }
}
