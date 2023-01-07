package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public abstract class IllagerBipedRenderer<T extends AbstractIllager> extends MobRenderer<T, IllagerBipedModel<T>> {
    public IllagerBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new IllagerBipedModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, builder.getModelSet(), builder.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, builder.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_INNER_LAYER)),
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_OUTER_LAYER))));
    }

    @Override
    protected void scale(T p_114919_, PoseStack p_114920_, float p_114921_) {
        p_114920_.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
                       MultiBufferSource bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void setModelVisibilities(T entityIn) {
        IllagerBipedModel<T> illagerModel = this.getModel();
        ItemStack itemstack = entityIn.getMainHandItem();
        ItemStack itemstack1 = entityIn.getOffhandItem();
        HumanoidModel.ArmPose bipedmodel$armpose = this.getArmPose(entityIn, itemstack, itemstack1,
                InteractionHand.MAIN_HAND);
        HumanoidModel.ArmPose bipedmodel$armpose1 = this.getArmPose(entityIn, itemstack, itemstack1,
                InteractionHand.OFF_HAND);
        if (entityIn.getArmPose() != AbstractIllager.IllagerArmPose.CROSSED) {
            if (entityIn.getMainArm() == HumanoidArm.RIGHT) {
                illagerModel.rightArmPose = bipedmodel$armpose;
                illagerModel.leftArmPose = bipedmodel$armpose1;
            } else {
                illagerModel.rightArmPose = bipedmodel$armpose1;
                illagerModel.leftArmPose = bipedmodel$armpose;
            }
        }
    }

    protected HumanoidModel.ArmPose getArmPose(T entityIn, ItemStack itemStackMain, ItemStack itemStackOff, InteractionHand handIn) {
        HumanoidModel.ArmPose bipedmodel$armpose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemstack = handIn == InteractionHand.MAIN_HAND ? itemStackMain : itemStackOff;
        if (!itemstack.isEmpty()) {
            bipedmodel$armpose = HumanoidModel.ArmPose.ITEM;
            UseAnim useaction = itemstack.getUseAnimation();
            switch (useaction) {
                case BLOCK:
                    bipedmodel$armpose = HumanoidModel.ArmPose.BLOCK;
                    break;
                case BOW:
                    bipedmodel$armpose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                    break;
                case SPEAR:
                    bipedmodel$armpose = HumanoidModel.ArmPose.THROW_SPEAR;
                    break;
                case CROSSBOW:
                    if (handIn == entityIn.getUsedItemHand() && entityIn.getUseItemRemainingTicks() > 0)
                        bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                    break;
                case SPYGLASS:
                    if (entityIn.getUseItemRemainingTicks() > 0)
                        bipedmodel$armpose = HumanoidModel.ArmPose.SPYGLASS;
                    break;
                default:
                    bipedmodel$armpose = HumanoidModel.ArmPose.EMPTY;
                    break;
            }
        }
        if (!entityIn.swinging && itemstack.getItem() instanceof CrossbowItem && !entityIn.isUsingItem()) {
            bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
        return bipedmodel$armpose;
    }
}
