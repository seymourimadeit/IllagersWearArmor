package tallestegg.illagersweararmor.client.renderer.mod_compat;

import baguchan.enchantwithmob.EnchantWithMob;
import baguchan.enchantwithmob.client.ModModelLayers;
import baguchan.enchantwithmob.entity.EnchanterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;
import tallestegg.illagersweararmor.client.model.mod_compat.EnchanterBipedModel;

public class EnchanterBipedRenderer extends MobRenderer<EnchanterEntity, EnchanterBipedModel<EnchanterEntity>> {
    private static final ResourceLocation ILLAGER = new ResourceLocation(EnchantWithMob.MODID, "textures/entity/enchanter.png");
    protected final BookModel bookModel;

    public EnchanterBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new EnchanterBipedModel<>(builder.bakeLayer(ModModelLayers.ENCHANTER)), 0.5F);
        bookModel = new BookModel(builder.bakeLayer(ModelLayers.BOOK));
        this.addLayer(new CustomHeadLayer<>(this, builder.getModelSet(), builder.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, builder.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_INNER_LAYER)),
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.ENCHANTER_ARMOR_OUTER_LAYER))));
    }

    @Override
    public void render(EnchanterEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        float bookAnimation = entityIn.getBookAnimationScale(partialTicks);

        float f = Mth.approach(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot);
        float swingProgress = this.getAttackAnim(entityIn, partialTicks);

        if (entityIn.isAlive()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0D, 1.1625D, 0.0F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f + 90F));
            matrixStackIn.translate(-0.575D, 0.0D, 0.0D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(60.0F * bookAnimation));

            //When spell casting, stop animation
            if (swingProgress > 0 && !entityIn.isCastingSpell()) {
                matrixStackIn.translate(-0.05F * (1.0F - swingProgress), -0.1F * (1.0F - swingProgress), 0.0D);
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(45.0F * (1.0F - swingProgress)));
            }

            this.bookModel.setupAnim(0.0F, Mth.clamp(bookAnimation, 0.0F, 0.1F), Mth.clamp(bookAnimation, 0.0F, 0.9F), bookAnimation);
            VertexConsumer ivertexbuilder = EnchantTableRenderer.BOOK_LOCATION.buffer(bufferIn, RenderType::entitySolid);
            this.bookModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }

    @Override
    protected void scale(EnchanterEntity p_114919_, PoseStack p_114920_, float p_114921_) {
        p_114920_.scale(0.9375F, 0.9375F, 0.9375F);
    }

    private void setModelVisibilities(EnchanterEntity entityIn) {
        IllagerBipedModel<EnchanterEntity> illagerModel = this.getModel();
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

    private HumanoidModel.ArmPose getArmPose(EnchanterEntity entityIn, ItemStack itemStackMain, ItemStack itemStackOff,
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
    public ResourceLocation getTextureLocation(EnchanterEntity p_110775_1_) {
        return ILLAGER;
    }
}
