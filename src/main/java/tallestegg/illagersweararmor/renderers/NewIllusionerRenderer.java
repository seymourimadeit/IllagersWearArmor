package tallestegg.illagersweararmor.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import tallestegg.illagersweararmor.models.IllagerArmorModel;
import tallestegg.illagersweararmor.models.IllagerBipedModel;

public class NewIllusionerRenderer extends MobRenderer<IllusionerEntity, IllagerBipedModel<IllusionerEntity>> {
    private static final ResourceLocation ILLUSIONIST = new ResourceLocation("textures/entity/illager/illusioner.png");

    public NewIllusionerRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new IllagerBipedModel<>(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new BipedArmorLayer<>(this, new IllagerArmorModel<>(0.5F), new IllagerArmorModel<>(1.0F)));
        this.addLayer(new HeldItemLayer<IllusionerEntity, IllagerBipedModel<IllusionerEntity>>(this) {
            @Override
            public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, IllusionerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                if (entitylivingbaseIn.isSpellcasting() || entitylivingbaseIn.isAggressive()) {
                    super.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
                }
            }
        });
        this.entityModel.bipedHeadwear.showModel = true; // this one line has cost me hours of my life
    }

    @Override
    public ResourceLocation getEntityTexture(IllusionerEntity entity) {
        return ILLUSIONIST;
    }

    @Override
    public void render(IllusionerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityIn.isInvisible() && entityIn.getRenderLocations(partialTicks) != null) {
            Vector3d[] avector3d = entityIn.getRenderLocations(partialTicks);
            float f = this.handleRotationFloat(entityIn, partialTicks);

            for (int i = 0; i < avector3d.length; ++i) {
                matrixStackIn.push();
                matrixStackIn.translate(avector3d[i].x + (double) MathHelper.cos((float) i + f * 0.5F) * 0.025D, avector3d[i].y + (double) MathHelper.cos((float) i + f * 0.75F) * 0.0125D, avector3d[i].z + (double) MathHelper.cos((float) i + f * 0.7F) * 0.025D);
                super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.pop();
            }
        } else {
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
    
    @Override
    protected void preRenderCallback(IllusionerEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected boolean isVisible(IllusionerEntity livingEntityIn) {
        return true;
    }
}