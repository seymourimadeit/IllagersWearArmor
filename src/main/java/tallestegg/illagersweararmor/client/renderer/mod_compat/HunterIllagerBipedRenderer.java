package tallestegg.illagersweararmor.client.renderer.mod_compat;

import baguchan.hunterillager.HunterIllager;
import baguchan.hunterillager.entity.Hunter;
import baguchan.hunterillager.init.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.mod_compat.HunterIllagerBipedModel;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;

public class HunterIllagerBipedRenderer
        extends MobRenderer<Hunter, HunterIllagerBipedModel<Hunter>> {
    private static final ResourceLocation ILLAGER = new ResourceLocation(HunterIllager.MODID,
            "textures/entity/hunter.png");

    public HunterIllagerBipedRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new HunterIllagerBipedModel<>(renderManagerIn.bakeLayer(ModModelLayers.HUNTERILLAGER)),
                0.5F);
        this.addLayer(new CustomHeadLayer<>(this, renderManagerIn.getModelSet(), renderManagerIn.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, renderManagerIn.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new IllagerArmorModel<>(renderManagerIn.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_INNER_LAYER)),
                new IllagerArmorModel<>(renderManagerIn.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_OUTER_LAYER)), renderManagerIn.getModelManager()));
        //  this.addLayer(new CrossArmHeldItemLayer<>(this));
        this.addLayer(new ItemInHandLayer<>(this, renderManagerIn.getItemInHandRenderer()));
    }

    @Override
    public void render(Hunter entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
                       MultiBufferSource bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private void setModelVisibilities(Hunter entityIn) {
        IllagerBipedModel<Hunter> illagerModel = this.getModel();
        ItemStack itemstack = entityIn.getMainHandItem();
        ItemStack itemstack1 = entityIn.getOffhandItem();
        HumanoidModel.ArmPose bipedmodel$armpose = this.getArmPose(entityIn, itemstack, itemstack1,
                InteractionHand.MAIN_HAND);
        HumanoidModel.ArmPose bipedmodel$armpose1 = this.getArmPose(entityIn, itemstack, itemstack1,
                InteractionHand.OFF_HAND);
        if (entityIn.getMainArm() == HumanoidArm.RIGHT) {
            illagerModel.rightArmPose = bipedmodel$armpose;
            illagerModel.leftArmPose = bipedmodel$armpose1;
        } else {
            illagerModel.rightArmPose = bipedmodel$armpose1;
            illagerModel.leftArmPose = bipedmodel$armpose;
        }
    }

    private HumanoidModel.ArmPose getArmPose(Hunter entityIn, ItemStack itemStackMain, ItemStack itemStackOff,
                                             InteractionHand handIn) {
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
                    if (entityIn.isAggressive())
                        bipedmodel$armpose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                    break;
                case SPEAR:
                    bipedmodel$armpose = HumanoidModel.ArmPose.THROW_SPEAR;
                    break;
                case CROSSBOW:
                    if (handIn == entityIn.getUsedItemHand()) {
                        bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                    }
                    break;
                default:
                    bipedmodel$armpose = HumanoidModel.ArmPose.EMPTY;
                    break;
            }
        } else {
            boolean flag1 = itemStackMain.getItem() instanceof CrossbowItem;
            boolean flag2 = itemStackOff.getItem() instanceof CrossbowItem;
            if (flag1) {
                bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            if (flag2 && itemStackMain.getItem().getUseAnimation(itemStackMain) == UseAnim.NONE) {
                bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        }
        return bipedmodel$armpose;
    }

    @Override
    protected void scale(Hunter p_114919_, PoseStack p_114920_, float p_114921_) {
        p_114920_.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public ResourceLocation getTextureLocation(Hunter p_110775_1_) {
        return ILLAGER;
    }
}