package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Evoker;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public class EvokerBipedRenderer extends IllagerBipedRenderer<Evoker> {
    private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/evoker.png");

    public EvokerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<Evoker, IllagerBipedModel<Evoker>>(this, builder.getItemInHandRenderer()) {
            @Override
            public void render(PoseStack p_116352_, MultiBufferSource p_116353_, int p_116354_, Evoker p_116355_,
                               float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_,
                               float p_116361_) {
                if (p_116355_.isAggressive() || p_116355_.isCastingSpell()) {
                    super.render(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_,
                            p_116360_, p_116361_);
                }
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(Evoker p_115720_) {
        return PILLAGER;
    }
}