package tallestegg.illagersweararmor.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class IllagerBipedModel<T extends AbstractIllagerEntity> extends BipedModel<T>
{
	private ModelRenderer arms;
	
	public IllagerBipedModel(float modelSize, float p_i47227_2_, int textureWidthIn, int textureHeightIn) 
	{
		super(modelSize);
	    this.bipedHead = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
	    this.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, modelSize);
	    this.bipedHeadwear = (new ModelRenderer(this, 32, 0)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.bipedHeadwear.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, modelSize + 0.45F);
	    this.bipedHead.addChild(this.bipedHeadwear);
	    this.bipedHeadwear.showModel = false;
	    ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
	    modelrenderer.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
	    modelrenderer.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, modelSize);
	    this.bipedHead.addChild(modelrenderer);
	    this.bipedBody = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
	    this.bipedBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, modelSize);
	    this.bipedBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, modelSize + 0.5F);
	    this.arms = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.arms.setRotationPoint(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
	    this.arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSize);
	    ModelRenderer modelrenderer1 = (new ModelRenderer(this, 44, 22)).setTextureSize(textureWidthIn, textureHeightIn);
	    modelrenderer1.mirror = true;
	    modelrenderer1.addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, modelSize);
	    this.arms.addChild(modelrenderer1);
	    this.arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, modelSize);
	    this.bipedLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.bipedLeftLeg.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
	    this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
	    this.bipedRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.bipedRightLeg.mirror = true;
	    this.bipedRightLeg.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
	    this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
	    this.bipedRightArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
	    this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i47227_2_, 0.0F);
	    this.bipedLeftArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
	    this.bipedLeftArm.mirror = true;
	    this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
	    this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i47227_2_, 0.0F);
	}
	
    protected Iterable<ModelRenderer> getBodyParts() 
    {
	    return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.arms));
	}
    
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) 
    {
    	ItemStack itemstack = entityIn.getHeldItemMainhand();
    	UseAction useaction = itemstack.getUseAction();
        this.rightArmPose = BipedModel.ArmPose.EMPTY;
        this.leftArmPose = BipedModel.ArmPose.EMPTY;
    	switch (useaction) 
    	{
    	  default:
    	  this.rightArmPose = BipedModel.ArmPose.EMPTY;
    	  if (!itemstack.isEmpty()) this.rightArmPose = BipedModel.ArmPose.ITEM;
    	  break;
    	  case BLOCK:
    	  this.rightArmPose = BipedModel.ArmPose.BLOCK;
    	  break;
    	  case BOW:
    	  this.rightArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
    	  break;
    	  case CROSSBOW: 
    	  this.rightArmPose = BipedModel.ArmPose.CROSSBOW_HOLD;
    	  if (entityIn.isHandActive()) this.rightArmPose = BipedModel.ArmPose.CROSSBOW_CHARGE;
    	  break;
    	  case SPEAR:
    	  this.rightArmPose = BipedModel.ArmPose.THROW_SPEAR;
    	  break;
    	}
    }
    
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
    {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        AbstractIllagerEntity.ArmPose armpose = entityIn.getArmPose();
        boolean armsCrossed = armpose == AbstractIllagerEntity.ArmPose.CROSSED;
        this.arms.showModel = armsCrossed;
        this.bipedLeftArm.showModel = !armsCrossed;
        this.bipedRightArm.showModel = !armsCrossed;
    }

}
