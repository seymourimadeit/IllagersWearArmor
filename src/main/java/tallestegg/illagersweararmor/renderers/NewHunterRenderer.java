package tallestegg.illagersweararmor.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;

import baguchan.hunterillager.HunterIllagerCore;
import baguchan.hunterillager.entity.HunterIllagerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import tallestegg.illagersweararmor.models.HunterBipedModel;
import tallestegg.illagersweararmor.models.IllagerArmorModel;

public class NewHunterRenderer extends MobRenderer<HunterIllagerEntity, HunterBipedModel<HunterIllagerEntity>> {
    private static final ResourceLocation ILLAGER = new ResourceLocation(HunterIllagerCore.MODID, "textures/entity/hunter_illager/hunter_illager.png");

    public NewHunterRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HunterBipedModel<>(0F, 0F, 64, 128), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new BipedArmorLayer<>(this, new IllagerArmorModel<>(0.5F), new IllagerArmorModel<>(1.0F)));
        this.addLayer(new OffHandCrossedArmLayer<HunterIllagerEntity, HunterBipedModel<HunterIllagerEntity>>(this));
        this.addLayer(new HeldItemLayer<HunterIllagerEntity, HunterBipedModel<HunterIllagerEntity>>(this) {
            @Override
            public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, HunterIllagerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                if (entitylivingbaseIn.isAggressive()) {
                    super.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
                }
            }
        });
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(HunterIllagerEntity entity) {
        return ILLAGER;
    }
}
