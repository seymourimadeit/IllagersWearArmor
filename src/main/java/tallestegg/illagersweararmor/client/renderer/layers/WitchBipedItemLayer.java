package tallestegg.illagersweararmor.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import tallestegg.illagersweararmor.client.model.WitchBipedModel;
import tallestegg.illagersweararmor.client.model.render_states.WitchBipedRenderState;

public class WitchBipedItemLayer<S extends WitchBipedRenderState, M extends WitchBipedModel<S>> extends RenderLayer<S, M> {
    public WitchBipedItemLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
        ItemStackRenderState item = state.heldItem;
        if (!item.isEmpty()) {
            poseStack.pushPose();
            this.applyTranslation(state, poseStack);
            item.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();
        }
    }

    protected void applyTranslation(S state, PoseStack poseStack) {
        if (state.isHoldingPotion) {
            this.getParentModel().root().translateAndRotate(poseStack);
            this.getParentModel().translateToHead(poseStack);
            this.getParentModel().getNose().translateAndRotate(poseStack);
            poseStack.translate(0.0625F, 0.25F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(140.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(10.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        } else {
            this.getParentModel().translateToArms(state, poseStack);
            poseStack.mulPose(Axis.XP.rotation(0.75F));
            poseStack.scale(1.07F, 1.07F, 1.07F);
            poseStack.translate(0.0F, 0.13F, -0.34F);
            poseStack.mulPose(Axis.XP.rotation((float) Math.PI));
        }
    }
}