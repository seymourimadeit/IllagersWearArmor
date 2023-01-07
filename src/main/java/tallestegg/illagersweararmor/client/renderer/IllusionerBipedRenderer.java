package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.phys.Vec3;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public class IllusionerBipedRenderer extends IllagerBipedRenderer<Illusioner> {
    private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/illusioner.png");

    public IllusionerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()) {
            @Override
            public void render(PoseStack p_116352_, MultiBufferSource p_116353_, int p_116354_, Illusioner p_116355_,
                               float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_,
                               float p_116361_) {
                if (p_116355_.isCastingSpell() || p_116355_.isAggressive()) {
                    super.render(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_,
                            p_116360_, p_116361_);
                }
            }
        });
    }

    @Override
    public void render(Illusioner p_114952_, float p_114953_, float p_114954_, PoseStack p_114955_,
            MultiBufferSource p_114956_, int p_114957_) {
        if (p_114952_.isInvisible()) {
            Vec3[] avec3 = p_114952_.getIllusionOffsets(p_114954_);
            float f = this.getBob(p_114952_, p_114954_);

            for (int i = 0; i < avec3.length; ++i) {
                p_114955_.pushPose();
                p_114955_.translate(avec3[i].x + (double) Mth.cos((float) i + f * 0.5F) * 0.025D,
                        avec3[i].y + (double) Mth.cos((float) i + f * 0.75F) * 0.0125D,
                        avec3[i].z + (double) Mth.cos((float) i + f * 0.7F) * 0.025D);
                super.render(p_114952_, p_114953_, p_114954_, p_114955_, p_114956_, p_114957_);
                p_114955_.popPose();
            }
        } else {
            super.render(p_114952_, p_114953_, p_114954_, p_114955_, p_114956_, p_114957_);
        }

    }

    @Override
    public ResourceLocation getTextureLocation(Illusioner p_115720_) {
        return PILLAGER;
    }

    @Override
    protected boolean isBodyVisible(Illusioner p_114959_) {
        return true;
    }
}
