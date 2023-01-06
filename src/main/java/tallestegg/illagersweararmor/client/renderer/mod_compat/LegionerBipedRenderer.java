package tallestegg.illagersweararmor.client.renderer.mod_compat;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Legioner;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import tallestegg.illagersweararmor.client.renderer.IllagerBipedRenderer;

public class LegionerBipedRenderer extends IllagerBipedRenderer<Legioner> {
    private static final ResourceLocation LEGIONER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/legioner.png");

    public LegionerBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Legioner legioner) {
        return LEGIONER;
    }

    @Override
    protected HumanoidModel.ArmPose getArmPose(Legioner entityIn, ItemStack itemStackMain, ItemStack itemStackOff, InteractionHand handIn) {
        HumanoidModel.ArmPose bipedmodel$armpose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemstack = handIn == InteractionHand.MAIN_HAND ? itemStackMain : itemStackOff;
        if (!itemstack.isEmpty()) {
            bipedmodel$armpose = HumanoidModel.ArmPose.ITEM;
            if (entityIn.isAlive() && (entityIn.isUsingShield() || entityIn.getShieldHand() == handIn)) {
                bipedmodel$armpose = HumanoidModel.ArmPose.BLOCK;
            }
        }
        return bipedmodel$armpose;
    }
}
