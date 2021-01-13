package tallestegg.illagersweararmor.models;

import com.baguchan.enchantwithmob.entity.EnchanterEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
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

public class EnchanterBipedModel<T extends EnchanterEntity> extends BipedModel<T> implements IHasArm, IHasHead {
    public ModelRenderer nose;
    public ModelRenderer cape;
    public ModelRenderer jacket;
    private final ModelRenderer arms;
    private final ModelRenderer crossArmR;
    private final ModelRenderer crossArmL;

    public EnchanterBipedModel() {
        super(0.0F);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addBox(-4.0F, -14.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.45F, 0.45F, 0.45F);
        this.bipedBody = (new ModelRenderer(this)).setTextureSize(128, 64);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0.0F);
        this.jacket = (new ModelRenderer(this)).setTextureSize(128, 64);
        this.jacket.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jacket.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 22);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 46);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.arms = new ModelRenderer(this, 44, 22);
        this.arms.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.arms.addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F);
        ModelRenderer modelrenderer1 = (new ModelRenderer(this, 44, 22));
        modelrenderer1.mirror = true;
        modelrenderer1.addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F);
        this.arms.addChild(modelrenderer1);
        this.arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F);
        this.crossArmR = (new ModelRenderer(this, 0, 22));
        this.crossArmR.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.crossArmR.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);
        this.crossArmL = (new ModelRenderer(this, 0, 22));
        this.crossArmL.mirror = true;
        this.crossArmL.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.crossArmL.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 22);
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 46);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.cape = new ModelRenderer(this, 64, 0);
        this.cape.setRotationPoint(0.0F, 1.0F, 3.4F);
        this.cape.addBox(-4.5F, 0.0F, 0.0F, 9.0F, 15.0F, 1.0F, 0.6F, 0.7F, 0.0F);
        this.setRotateAngle(cape, 0.1563815016444822F, 0.0F, 0.0F);
        this.bipedHead.addChild(this.nose);
        this.bipedBody.addChild(this.cape);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.arms, this.cape, this.jacket));
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
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float bipedHeadPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, bipedHeadPitch);
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

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {

    }

    @Override
    public ModelRenderer getModelHead() {
        return this.bipedHead;
    }
}