package tallestegg.illagersweararmor.renderers;

import com.baguchan.enchantwithmob.EnchantWithMob;
import com.baguchan.enchantwithmob.entity.EnchanterEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import tallestegg.illagersweararmor.models.EnchanterArmorModel;
import tallestegg.illagersweararmor.models.EnchanterBipedModel;

public class NewEnchanterRenderer<T extends EnchanterEntity> extends MobRenderer<T, EnchanterBipedModel<T>> {
    private static final ResourceLocation ILLAGER = new ResourceLocation(EnchantWithMob.MODID, "textures/entity/enchanter.png");

    protected final BookModel bookModel = new BookModel();

    public NewEnchanterRenderer(EntityRendererManager p_i47477_1_) {
        super(p_i47477_1_, new EnchanterBipedModel<>(), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        // this.addLayer(new CrossArmHeldItemLayer<>(this));
        this.addLayer(new BipedArmorLayer<>(this, new EnchanterArmorModel<>(0.5F), new EnchanterArmorModel<>(1.0F)));
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        float bookAnimation = entityIn.getBookAnimationScale(partialTicks);

        float f = MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset);

        if (entityIn.isAlive()) {
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, 1.1625D, 0.0F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f + 90F));
            matrixStackIn.translate(-0.575D, 0.0D, 0.0D);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(60.0F * bookAnimation));
            this.bookModel.setBookState(0.0F, MathHelper.clamp(bookAnimation, 0.0F, 0.1F), MathHelper.clamp(bookAnimation, 0.0F, 0.9F), bookAnimation);
            IVertexBuilder ivertexbuilder = EnchantmentTableTileEntityRenderer.TEXTURE_BOOK.getBuffer(bufferIn, RenderType::getEntitySolid);
            this.bookModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.pop();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(T entity) {
        return ILLAGER;
    }
}
