    package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Vindicator;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public class VindicatorBipedRenderer extends IllagerBipedRenderer<Vindicator> {
    private static final ResourceLocation PILLAGER = ResourceLocation.withDefaultNamespace("textures/entity/illager/vindicator.png");

    public VindicatorBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<Vindicator, IllagerBipedModel<Vindicator>>(this, builder.getItemInHandRenderer()) {
            @Override
            public void render(PoseStack p_116352_, MultiBufferSource p_116353_, int p_116354_, Vindicator p_116355_,
                    float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_,
                    float p_116361_) {
                if (p_116355_.isAggressive()) {
                    super.render(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_,
                            p_116360_, p_116361_);
                }

            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(Vindicator p_115720_) {
        return PILLAGER;
    }
}
