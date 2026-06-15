package tallestegg.illagersweararmor.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;

public class VexArmorLayer extends NonHumanoidArmorLayer<Vex, VexModel> {
    public VexArmorLayer(RenderLayerParent<Vex, VexModel> pRenderer, EntityModelSet modelSets, ModelManager manager) {
        super(pRenderer, manager, new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR)), new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR)));
    }

    @Override
    public void copyPropertiesTo(HumanoidModel pModel, Vex vex) {
        pModel.head.copyFrom(this.getParentModel().root().getChild("head"));
        pModel.body.copyFrom(this.getParentModel().root().getChild("body"));
        pModel.rightArm.copyFrom(this.getParentModel().root().getChild("body").getChild("right_arm"));
        pModel.leftArm.copyFrom(this.getParentModel().root().getChild("body").getChild("left_arm"));
        pModel.rightLeg.copyFrom(this.getParentModel().root().getChild("body"));
        pModel.leftLeg.copyFrom(this.getParentModel().root().getChild("body"));
    }

    @Override
    protected void renderArmorPiece(PoseStack pPoseStack, MultiBufferSource pBuffer, Vex pLivingEntity, EquipmentSlot pSlot, int pPackedLight, HumanoidModel pModel) {
        ItemStack itemstack = pLivingEntity.getItemBySlot(pSlot);
        Item item = itemstack.getItem();
        if (item instanceof ArmorItem armoritem) {
            if (armoritem.getEquipmentSlot() == pSlot) {
                this.copyPropertiesTo(pModel, pLivingEntity);
                this.setPartVisibility(pLivingEntity, pModel, pSlot);
                net.minecraft.client.model.Model model = getArmorModelHook(pLivingEntity, itemstack, pSlot, pModel);
                boolean flag = this.usesInnerModel(pSlot);
                if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) {
                    int i = ((net.minecraft.world.item.DyeableLeatherItem)armoritem).getColor(itemstack);
                    float f = (float)(i >> 16 & 255) / 255.0F;
                    float f1 = (float)(i >> 8 & 255) / 255.0F;
                    float f2 = (float)(i & 255) / 255.0F;
                    this.renderModel(pPoseStack, pBuffer, pPackedLight, armoritem, model, flag, f, f1, f2, this.getArmorResource(pLivingEntity, itemstack, pSlot, null));
                    this.renderModel(pPoseStack, pBuffer, pPackedLight, armoritem, model, flag, 1.0F, 1.0F, 1.0F, this.getArmorResource(pLivingEntity, itemstack, pSlot, "overlay"));
                } else {
                    this.renderModel(pPoseStack, pBuffer, pPackedLight, armoritem, model, flag, 1.0F, 1.0F, 1.0F, this.getArmorResource(pLivingEntity, itemstack, pSlot, null));
                }
                ArmorTrim.getTrim(pLivingEntity.level().registryAccess(), itemstack).ifPresent((p_289638_) -> {
                    this.renderTrim(armoritem.getMaterial(), pPoseStack, pBuffer, pPackedLight, p_289638_, model, flag);
                });
                if (itemstack.hasFoil()) {
                    this.renderGlint(pPoseStack, pBuffer, pPackedLight, model);
                }

            }
        }
    }


    @Override
    protected void setPartVisibility(Vex vex, HumanoidModel pModel, EquipmentSlot pSlot) {
        pModel.setAllVisible(false);
        switch (pSlot) {
            case HEAD:
                pModel.head.visible = true;
                pModel.hat.visible = true;
                pModel.head.xScale = 0.8F;
                pModel.head.yScale = 0.8F;
                pModel.head.zScale = 0.8F;
                pModel.head.y -= 13.0F;
                break;
            case CHEST:
                pModel.body.visible = true;
                pModel.rightArm.visible = true;
                pModel.leftArm.visible = true;
                pModel.rightArm.y -= -11.5F;
                pModel.rightArm.z += 0.25F;
                pModel.rightArm.x -= 3.0F;
                pModel.rightArm.xScale = 0.8F;
                pModel.rightArm.yScale = 0.8F;
                pModel.rightArm.zScale = 0.8F;
                pModel.leftArm.xScale = 0.8F;
                pModel.leftArm.yScale = 0.8F;
                pModel.leftArm.zScale = 0.8F;
                pModel.leftArm.y -= -11.5F;
                pModel.leftArm.z += 0.25F;
                pModel.leftArm.x -= -3.0F;
                pModel.body.xScale = 0.8F;
                pModel.body.yScale = 0.8F;
                pModel.body.zScale = 0.8F;
                pModel.body.y -= 9.0F;
                break;
            case LEGS:
                pModel.body.visible = true;
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
                pModel.body.y -= 6.0F;
                pModel.body.z -= -0.6F;
                pModel.body.xScale = 0.3F;
                pModel.body.yScale = 0.3F;
                pModel.body.zScale = 0.45F;
                pModel.rightLeg.xScale = 0.6F;
                pModel.rightLeg.yScale = 0.6F;
                pModel.rightLeg.zScale = 0.7F;
                pModel.rightLeg.y = 20.0F;
                pModel.rightLeg.x = -1.5F;
                pModel.rightLeg.z = 1.5F;
                pModel.leftLeg.xScale = 0.6F;
                pModel.leftLeg.yScale = 0.6F;
                pModel.leftLeg.zScale = 0.7F;
                pModel.leftLeg.y = 20.0F;
                pModel.leftLeg.x = 1.5F;
                pModel.leftLeg.z = 1.5F;
                break;
            case FEET:
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
                pModel.rightLeg.y = 20.0F;
                pModel.rightLeg.xScale = 0.6F;
                pModel.rightLeg.yScale = 0.6F;
                pModel.rightLeg.zScale = 0.7F;
                pModel.rightLeg.x = -1.5F;
                pModel.rightLeg.z = 1.5F;
                pModel.leftLeg.xScale = 0.6F;
                pModel.leftLeg.yScale = 0.6F;
                pModel.leftLeg.zScale = 0.7F;
                pModel.leftLeg.y = 20.0F;
                pModel.leftLeg.x = 1.5F;
                pModel.leftLeg.z = 1.5F;
        }
    }
}
