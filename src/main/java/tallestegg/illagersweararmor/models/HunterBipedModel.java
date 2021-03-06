package tallestegg.illagersweararmor.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import baguchan.hunterillager.entity.HunterIllagerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import tallestegg.illagersweararmor.IWAConfig;

public class HunterBipedModel<T extends HunterIllagerEntity> extends BipedModel<T> {
    private final ModelRenderer quiver;
    private final ModelRenderer cape;
    private final ModelRenderer capeLower;
    private final ModelRenderer jacket;
    private final ModelRenderer arms;

    public HunterBipedModel(float scaleFactor, float p_i47227_2_, int textureWidthIn, int textureHeightIn) {
        super(scaleFactor, p_i47227_2_, textureWidthIn, textureHeightIn);
        this.bipedHead = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, scaleFactor);
        this.bipedHeadwear = (new ModelRenderer(this, 32, 0)).setTextureSize(textureWidthIn, textureHeightIn);
        this.bipedHeadwear.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, scaleFactor + 0.45F);
        this.bipedHeadwear.showModel = false;
        ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        modelrenderer.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
        modelrenderer.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, scaleFactor);
        this.bipedHead.addChild(modelrenderer);
        this.bipedBody = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.bipedBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, scaleFactor);
        this.jacket = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.jacket.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.jacket.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, scaleFactor + 0.5F);
        this.arms = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.arms.setRotationPoint(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
        this.arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, scaleFactor);
        ModelRenderer modelrenderer1 = (new ModelRenderer(this, 44, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        modelrenderer1.mirror = true;
        modelrenderer1.addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, scaleFactor);
        this.arms.addChild(modelrenderer1);
        this.arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, scaleFactor);
        this.bipedRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
        this.bipedLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
        this.bipedRightArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i47227_2_, 0.0F);
        this.bipedLeftArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scaleFactor);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i47227_2_, 0.0F);

        // Mojang made the original hood 12 pixels for some reason, so this had to be
        // done
        this.getModelHead().addBox("hood_fixed", -4F, -10F, -4F, 8, 10, 8, scaleFactor + 0.45F, 32, 0);

        this.cape = new ModelRenderer(this, 0, 0);
        this.cape.setTextureSize(textureWidthIn, textureHeightIn);
        this.cape.setRotationPoint(0F, 0.5F, 3F);
        this.cape.setTextureOffset(0, 64).addBox(-4.5F, 0F, -0.5F, 9, 11, 1, 0.1F + scaleFactor);
        this.bipedBody.addChild(this.cape);

        this.quiver = new ModelRenderer(this);
        this.quiver.setTextureSize(textureWidthIn, textureHeightIn);
        this.quiver.setRotationPoint(3F, 0F, 2.5F);
        this.quiver.setTextureOffset(20, 64).addBox(-2.5F, 0F, -2.5F, 5, 13, 3, -0.5F + scaleFactor);
        this.quiver.setTextureOffset(36, 64).addBox(-2.5F, 0F, -2.5F, 5, 13, 3, scaleFactor);
        this.cape.addChild(this.quiver);

        this.capeLower = new ModelRenderer(this, 0, 0);
        this.capeLower.setTextureSize(textureWidthIn, textureHeightIn);
        this.capeLower.setRotationPoint(0F, 11F, 0F);
        this.capeLower.setTextureOffset(0, 76).addBox(-4.5F, 0F, -0.5F, 9, 8, 1, 0.1F + scaleFactor);
        this.cape.addChild(this.capeLower);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.arms, this.jacket, this.cape, this.capeLower));
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.rightArmPose = BipedModel.ArmPose.EMPTY;
        this.leftArmPose = BipedModel.ArmPose.EMPTY;
        if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
            this.giveModelRightArmPoses(Hand.MAIN_HAND, entityIn);
            this.giveModelLeftArmPoses(Hand.OFF_HAND, entityIn);
        } else {
            this.giveModelRightArmPoses(Hand.OFF_HAND, entityIn);
            this.giveModelLeftArmPoses(Hand.MAIN_HAND, entityIn);
        }
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    private void giveModelRightArmPoses(Hand hand, T entityIn) {
        ItemStack itemstack = entityIn.getHeldItem(hand);
        UseAction useaction = itemstack.getUseAction();
        if (entityIn.getArmPose() != AbstractIllagerEntity.ArmPose.CROSSED) {
            switch (useaction) {
            case BLOCK:
                this.rightArmPose = BipedModel.ArmPose.BLOCK;
                break;
            case CROSSBOW:
                this.rightArmPose = BipedModel.ArmPose.CROSSBOW_HOLD;
                if (entityIn.isHandActive()) {
                    this.rightArmPose = BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
                break;
            case BOW:
                this.rightArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
                break;
            default:
                this.rightArmPose = BipedModel.ArmPose.EMPTY;
                if (!itemstack.isEmpty()) {
                    this.rightArmPose = BipedModel.ArmPose.ITEM;
                }
                break;
            }
        }
    }

    private void giveModelLeftArmPoses(Hand hand, T entityIn) {
        ItemStack itemstack = entityIn.getHeldItem(hand);
        UseAction useaction = itemstack.getUseAction();
        if (entityIn.getArmPose() != AbstractIllagerEntity.ArmPose.CROSSED) {
            switch (useaction) {
            case BLOCK:
                this.leftArmPose = BipedModel.ArmPose.BLOCK;
                break;
            case CROSSBOW:
                this.leftArmPose = BipedModel.ArmPose.CROSSBOW_HOLD;
                if (entityIn.isHandActive()) {
                    this.leftArmPose = BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
                break;
            case BOW:
                this.leftArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
                break;
            default:
                this.leftArmPose = BipedModel.ArmPose.EMPTY;
                if (!itemstack.isEmpty()) {
                    this.leftArmPose = BipedModel.ArmPose.ITEM;
                }
                break;
            }
        }
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.quiver.rotateAngleZ = 0.5235987755982988F;
        this.cape.rotateAngleX = ((float) (Math.PI) / 18) * (limbSwingAmount * 1.75F);
        this.capeLower.rotateAngleX = ((float) (Math.PI) / 32) * (limbSwingAmount * 1.75F);
        AbstractIllagerEntity.ArmPose armpose = entityIn.getArmPose();
        this.arms.rotationPointY = 3.0F;
        this.arms.rotationPointZ = -1.0F;
        this.arms.rotateAngleX = -0.75F;
        this.jacket.copyModelAngles(bipedBody);
        boolean isWearingChestplateOrLeggings = entityIn.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() instanceof ArmorItem || entityIn.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() instanceof ArmorItem;
        this.jacket.showModel = !isWearingChestplateOrLeggings;
        boolean flag = armpose == AbstractIllagerEntity.ArmPose.CROSSED && IWAConfig.IllagerCrossArms;
        this.arms.showModel = flag;
        this.bipedLeftArm.showModel = !flag;
        this.bipedRightArm.showModel = !flag;
        if (flag) {
            this.bipedLeftArm.rotationPointY = 3.0F;
            this.bipedLeftArm.rotationPointZ = -1.0F;
            this.bipedLeftArm.rotateAngleX = -0.75F;
            this.bipedRightArm.rotationPointY = 3.0F;
            this.bipedRightArm.rotationPointZ = -1.0F;
            this.bipedRightArm.rotateAngleX = -0.75F;
        }
        switch (armpose) {
        case ATTACKING:
            if (!entityIn.getHeldItemMainhand().isEmpty() && !(entityIn.getHeldItemMainhand().getItem() instanceof ShootableItem))
                ModelHelper.func_239103_a_(this.bipedRightArm, this.bipedLeftArm, entityIn, this.swingProgress, ageInTicks);
            break;
        case CELEBRATING:
            this.bipedRightArm.rotationPointZ = 0.0F;
            this.bipedRightArm.rotationPointX = -5.0F;
            this.bipedRightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.bipedRightArm.rotateAngleZ = 2.670354F;
            this.bipedRightArm.rotateAngleY = 0.0F;
            this.bipedLeftArm.rotationPointZ = 0.0F;
            this.bipedLeftArm.rotationPointX = 5.0F;
            this.bipedLeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.bipedLeftArm.rotateAngleZ = -2.3561945F;
            this.bipedLeftArm.rotateAngleY = 0.0F;
            break;
        case SPELLCASTING:
            this.bipedRightArm.rotationPointZ = 0.0F;
            this.bipedRightArm.rotationPointX = -5.0F;
            this.bipedLeftArm.rotationPointZ = 0.0F;
            this.bipedLeftArm.rotationPointX = 5.0F;
            this.bipedRightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.bipedLeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.bipedRightArm.rotateAngleZ = 2.3561945F;
            this.bipedLeftArm.rotateAngleZ = -2.3561945F;
            this.bipedRightArm.rotateAngleY = 0.0F;
            this.bipedLeftArm.rotateAngleY = 0.0F;
            break;
        default:
            break;
        }
    }
}