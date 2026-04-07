package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;
import tallestegg.illagersweararmor.client.model.render_states.IllagerBipedRenderState;

public abstract class IllagerBipedRenderer<T extends AbstractIllager> extends MobRenderer<T, IllagerBipedRenderState, IllagerBipedModel<IllagerBipedRenderState>> {
    public IllagerBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new IllagerBipedModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, builder.getModelSet(), builder.getPlayerSkinRenderCache()));
        ArmorModelSet<HumanoidModel<IllagerBipedRenderState>> armorModels =
                ArmorModelSet.bake(IWAClientEvents.ILLAGER_ARMOR, builder.getModelSet(), IllagerArmorModel::new);
        this.addLayer(new HumanoidArmorLayer<>(this, armorModels, builder.getEquipmentRenderer()));
    }

    @Override
    public IllagerBipedRenderState createRenderState() {
        return new IllagerBipedRenderState();
    }

    @Override
    protected void scale(IllagerBipedRenderState state, PoseStack poseStack) {
        super.scale(state, poseStack);
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public void extractRenderState(T entity, IllagerBipedRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        HumanoidMobRenderer.extractHumanoidRenderState(entity, state, partialTick, this.itemModelResolver);
        state.isUsingItem = entity.isUsingItem();
        state.isAggressive = entity.isAggressive();
        state.entityId = entity.getId();
        state.armPose = entity.getArmPose();
        if (entity instanceof SpellcasterIllager spellcasterIllager)
            state.isCastingSpell = spellcasterIllager.isCastingSpell();
        if (entity instanceof Illusioner iLlusioner)
            state.illusionOffsets = iLlusioner.getIllusionOffsets(partialTick);
        state.isWearingChestplateOrLegging = entity.hasItemInSlot(EquipmentSlot.CHEST) || entity.hasItemInSlot(EquipmentSlot.LEGS);
        if (state.isUsingItem) {
            state.useItemHand = entity.getUsedItemHand();
            state.ticksUsingItem = entity.getTicksUsingItem();
            ItemStack use = entity.getUseItem();
            if (use.getItem() instanceof CrossbowItem) {
                state.maxCrossbowChargeDuration = CrossbowItem.getChargeDuration(use, entity);
            } else {
                state.maxCrossbowChargeDuration = 0.0F;
            }
        } else {
            state.ticksUsingItem = 0;
            state.maxCrossbowChargeDuration = 0.0F;
        }
        state.rightArmPose = renderArmPose(entity, HumanoidArm.RIGHT);
        state.leftArmPose = renderArmPose(entity, HumanoidArm.LEFT);
        state.mainArm = entity.getMainArm();
        state.isCrouching = entity.getPose() == net.minecraft.world.entity.Pose.CROUCHING;
    }

    private static HumanoidModel.ArmPose renderArmPose(AbstractIllager guard, HumanoidArm arm) {
        ItemStack itemstack = guard.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack itemstack1 = guard.getItemInHand(InteractionHand.OFF_HAND);
        HumanoidModel.ArmPose humanoidmodel$armpose = identifyArmPoses(guard, itemstack, InteractionHand.MAIN_HAND);
        HumanoidModel.ArmPose humanoidmodel$armpose1 = identifyArmPoses(guard, itemstack1, InteractionHand.OFF_HAND);
        if (humanoidmodel$armpose.isTwoHanded()) {
            humanoidmodel$armpose1 = itemstack1.isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
        }

        return guard.getMainArm() == arm ? humanoidmodel$armpose : humanoidmodel$armpose1;
    }

    private static HumanoidModel.ArmPose identifyArmPoses(AbstractIllager guard, ItemStack handItem, InteractionHand hand) {
        var extensions = net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(handItem);
        var armPose = extensions.getArmPose(guard, hand, handItem);
        if (armPose != null) {
            return armPose;
        }
        if (handItem.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        } else {
            if (guard.getUsedItemHand() == hand && guard.getUseItemRemainingTicks() > 0) {
                ItemUseAnimation itemuseanimation = handItem.getUseAnimation();
                switch (itemuseanimation) {
                    case BLOCK -> {
                        return HumanoidModel.ArmPose.BLOCK;
                    }
                    case BOW -> {
                        return HumanoidModel.ArmPose.BOW_AND_ARROW;
                    }
                    case TRIDENT -> {
                        return HumanoidModel.ArmPose.THROW_TRIDENT;
                    }
                    case CROSSBOW -> {
                        return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                    }
                    case SPYGLASS -> {
                        return HumanoidModel.ArmPose.SPYGLASS;
                    }
                    case TOOT_HORN -> {
                        return HumanoidModel.ArmPose.TOOT_HORN;
                    }
                    case BRUSH -> {
                        return HumanoidModel.ArmPose.BRUSH;
                    }
                    case SPEAR -> {
                        return HumanoidModel.ArmPose.SPEAR;
                    }
                }
            }
            if (!guard.swinging && (handItem.getItem() instanceof CrossbowItem)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
            SwingAnimation swinganimation = handItem.get(DataComponents.SWING_ANIMATION);
            if (swinganimation != null && swinganimation.type() == SwingAnimationType.STAB && guard.swinging) {
                return HumanoidModel.ArmPose.SPEAR;
            } else {
                return handItem.is(ItemTags.SPEARS) ? HumanoidModel.ArmPose.SPEAR : HumanoidModel.ArmPose.ITEM;
            }
        }
    }
}
