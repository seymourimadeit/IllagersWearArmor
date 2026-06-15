package tallestegg.illagersweararmor.client.renderer.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.DyedItemColor;
import tallestegg.illagersweararmor.IWAConfig;

import java.util.Map;

public class VexArmorLayer extends NonHumanoidArmorLayer<Vex, VexModel, HumanoidModel<Vex>> {
    public VexArmorLayer(RenderLayerParent<Vex, VexModel> pRenderer, EntityModelSet modelSets, ModelManager modelManager) {
        super(pRenderer,  new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR)),  new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR)), modelManager);
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
    protected void renderArmorPiece(PoseStack p_117119_, MultiBufferSource p_117120_, Vex p_117121_, EquipmentSlot p_117122_, int p_117123_, HumanoidModel p_117124_, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = p_117121_.getItemBySlot(p_117122_);
        if (itemstack.getItem() instanceof ArmorItem armoritem) {
            if (armoritem.getEquipmentSlot() == p_117122_) {
                this.copyPropertiesTo(p_117124_, p_117121_);
                this.setPartVisibility(p_117124_, p_117122_);
                net.minecraft.client.model.Model model = getArmorModelHook(p_117121_, itemstack, p_117122_, p_117124_);
                boolean flag = this.usesInnerModel(p_117122_);
                ArmorMaterial armormaterial = armoritem.getMaterial().value();
                int i = itemstack.is(ItemTags.DYEABLE) ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(itemstack, -6265536)) : -1;

                for (ArmorMaterial.Layer armormaterial$layer : armormaterial.layers()) {
                    int j = armormaterial$layer.dyeable() ? i : -1;
                    var texture = net.neoforged.neoforge.client.ClientHooks.getArmorTexture(p_117121_, itemstack, armormaterial$layer, flag, p_117122_);
                    this.renderModel(p_117119_, p_117120_, p_117123_, p_117124_, j, texture);
                }

                ArmorTrim armortrim = itemstack.get(DataComponents.TRIM);
                if (armortrim != null) {
                    this.renderTrim(armoritem.getMaterial(), p_117119_, p_117120_, p_117123_, armortrim, model, flag);
                }

                if (itemstack.hasFoil()) {
                    this.renderGlint(p_117119_, p_117120_, p_117123_, model);
                }
            }
        }
    }

    @Override
    protected void setPartVisibility(HumanoidModel pModel, EquipmentSlot pSlot) {
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
                pModel.body.xScale = 0.6F;
                pModel.body.yScale = 0.6F;
                pModel.body.zScale = 0.75F;
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
