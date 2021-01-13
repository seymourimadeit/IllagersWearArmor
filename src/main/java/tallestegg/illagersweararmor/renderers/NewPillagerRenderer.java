package tallestegg.illagersweararmor.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.util.ResourceLocation;
import tallestegg.illagersweararmor.models.IllagerArmorModel;
import tallestegg.illagersweararmor.models.IllagerBipedModel;

public class NewPillagerRenderer extends MobRenderer<PillagerEntity, IllagerBipedModel<PillagerEntity>> {
    private static final ResourceLocation PILLAGER_TEXTURE = new ResourceLocation("textures/entity/illager/pillager.png");

    public NewPillagerRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new IllagerBipedModel<>(0.0F, 0.0F, 64, 64), 0.5f);
        this.addLayer(new BipedArmorLayer<>(this, new IllagerArmorModel<>(0.5F), new IllagerArmorModel<>(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new HeadLayer<PillagerEntity, IllagerBipedModel<PillagerEntity>>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(PillagerEntity entity) {
        return PILLAGER_TEXTURE;
    }
    
    @Override
    protected void preRenderCallback(PillagerEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
