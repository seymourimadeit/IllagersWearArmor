package tallestegg.illagersweararmor.client.renderer;

import com.baguchan.enchantwithmob.EnchantWithMob;
import com.baguchan.enchantwithmob.client.ModModelLayers;
import com.baguchan.enchantwithmob.entity.EnchanterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.EnchanterBipedModel;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;

public class EnchanterBipedRenderer extends MobRenderer<EnchanterEntity, EnchanterBipedModel<EnchanterEntity>> {
    private static final ResourceLocation ILLAGER = new ResourceLocation(EnchantWithMob.MODID, "textures/entity/enchanter.png");
    protected final BookModel bookModel;

    public EnchanterBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new EnchanterBipedModel<>(builder.bakeLayer(ModModelLayers.ENCHANTER)), 0.5F);
        bookModel = new BookModel(builder.bakeLayer(ModelLayers.BOOK));
        this.addLayer(new CustomHeadLayer<>(this, builder.getModelSet()));
        this.addLayer(new ElytraLayer<>(this, builder.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_INNER_LAYER)),
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.ENCHANTER_ARMOR_OUTER_LAYER))));
    }

    @Override
    public void render(EnchanterEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        float bookAnimation = entityIn.getBookAnimationScale(partialTicks);

        float f = Mth.approach(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot);
        float swingProgress = this.getAttackAnim(entityIn, partialTicks);

        if (entityIn.isAlive()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0D, 1.1625D, 0.0F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f + 90F));
            matrixStackIn.translate(-0.575D, 0.0D, 0.0D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(60.0F * bookAnimation));

            //When spell casting, stop animation
            if (swingProgress > 0 && !entityIn.isCastingSpell()) {
                matrixStackIn.translate(-0.05F * (1.0F - swingProgress), -0.1F * (1.0F - swingProgress), 0.0D);
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(45.0F * (1.0F - swingProgress)));
            }

            this.bookModel.setupAnim(0.0F, Mth.clamp(bookAnimation, 0.0F, 0.1F), Mth.clamp(bookAnimation, 0.0F, 0.9F), bookAnimation);
            VertexConsumer ivertexbuilder = EnchantTableRenderer.BOOK_LOCATION.buffer(bufferIn, RenderType::entitySolid);
            this.bookModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(EnchanterEntity p_110775_1_) {
        return ILLAGER;
    }
}
