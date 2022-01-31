package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import baguchan.hunterillager.HunterIllager;
import baguchan.hunterillager.client.render.layer.CrossArmHeldItemLayer;
import baguchan.hunterillager.entity.HunterIllagerEntity;
import baguchan.hunterillager.init.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.HunterIllagerBipedModel;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;

public class HunterIllagerBipedRenderer
        extends MobRenderer<HunterIllagerEntity, HunterIllagerBipedModel<HunterIllagerEntity>> {
    private static final ResourceLocation ILLAGER = new ResourceLocation(HunterIllager.MODID,
            "textures/entity/hunter_illager/hunter_illager.png");

    public HunterIllagerBipedRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new HunterIllagerBipedModel<>(renderManagerIn.bakeLayer(ModModelLayers.HUNTERILLAGER)),
                0.5F);
        this.addLayer(new CustomHeadLayer<>(this, renderManagerIn.getModelSet()));
        this.addLayer(new ElytraLayer<>(this, renderManagerIn.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new IllagerArmorModel<>(renderManagerIn.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_INNER_LAYER)),
                new IllagerArmorModel<>(renderManagerIn.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_OUTER_LAYER))));
        this.addLayer(new CrossArmHeldItemLayer<>(this));
        this.addLayer(new ItemInHandLayer<>(this) {
            @Override
            public void render(PoseStack p_114989_, MultiBufferSource p_114990_, int p_114991_,
                    HunterIllagerEntity p_114992_, float p_114993_, float p_114994_, float p_114995_, float p_114996_,
                    float p_114997_, float p_114998_) {
                if (p_114992_.isAggressive())
                    super.render(p_114989_, p_114990_, p_114991_, p_114992_, p_114993_, p_114994_, p_114995_, p_114996_,
                            p_114997_, p_114998_);
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(HunterIllagerEntity p_110775_1_) {
        return ILLAGER;
    }
}