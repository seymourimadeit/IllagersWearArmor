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

import java.util.Map;

public class VexArmorLayer extends RenderLayer<Vex, VexModel> {
    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
    private final HumanoidModel innerModel;
    private final HumanoidModel outerModel;
    private final TextureAtlas armorTrimAtlas;

    public VexArmorLayer(RenderLayerParent<Vex, VexModel> pRenderer, EntityModelSet modelSets, ModelManager modelManager) {
        super(pRenderer);
        this.innerModel = new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR));
        this.outerModel = new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    public void copyPropertiesTo(HumanoidModel pModel) {
        pModel.head.copyFrom(this.getParentModel().root().getChild("head"));
        //  pModel.hat.copyFrom(this.getParentModel().root().getChild("head"));
        pModel.body.copyFrom(this.getParentModel().root().getChild("body"));
        pModel.rightArm.copyFrom(this.getParentModel().root().getChild("body").getChild("right_arm"));
        pModel.leftArm.copyFrom(this.getParentModel().root().getChild("body").getChild("left_arm"));
        pModel.rightLeg.copyFrom(this.getParentModel().root().getChild("body"));
        pModel.leftLeg.copyFrom(this.getParentModel().root().getChild("body"));
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, Vex pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.copyPropertiesTo(innerModel);
        this.copyPropertiesTo(outerModel);
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.CHEST, pPackedLight, this.getArmorModel(EquipmentSlot.CHEST));
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.LEGS, pPackedLight, this.getArmorModel(EquipmentSlot.LEGS));
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.FEET, pPackedLight, this.getArmorModel(EquipmentSlot.FEET));
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.HEAD, pPackedLight, this.getArmorModel(EquipmentSlot.HEAD));
    }

    private void renderArmorPiece(PoseStack p_117119_, MultiBufferSource p_117120_, Vex p_117121_, EquipmentSlot p_117122_, int p_117123_, HumanoidModel p_117124_) {
        ItemStack itemstack = p_117121_.getItemBySlot(p_117122_);
        if (itemstack.getItem() instanceof ArmorItem armoritem) {
            if (armoritem.getEquipmentSlot() == p_117122_) {
                this.copyPropertiesTo(p_117124_);
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

    private void renderModel(PoseStack p_289664_, MultiBufferSource p_289689_, int p_289681_, HumanoidModel p_289658_, int p_350798_, ResourceLocation p_324344_) {
        renderModel(p_289664_, p_289689_, p_289681_, (net.minecraft.client.model.Model) p_289658_, p_350798_, p_324344_);
    }
    private void renderModel(PoseStack p_289664_, MultiBufferSource p_289689_, int p_289681_, net.minecraft.client.model.Model p_289658_, int p_350798_, ResourceLocation p_324344_) {
        VertexConsumer vertexconsumer = p_289689_.getBuffer(RenderType.armorCutoutNoCull(p_324344_));
        p_289658_.renderToBuffer(p_289664_, vertexconsumer, p_289681_, OverlayTexture.NO_OVERLAY, p_350798_);
    }

    private void renderTrim(
            Holder<ArmorMaterial> p_323506_, PoseStack p_289687_, MultiBufferSource p_289643_, int p_289683_, ArmorTrim p_289692_, HumanoidModel p_289663_, boolean p_289651_
    ) {
        renderTrim(p_323506_, p_289687_, p_289643_, p_289683_, p_289692_, (net.minecraft.client.model.Model) p_289663_, p_289651_);
    }
    private void renderTrim(
            Holder<ArmorMaterial> p_323506_, PoseStack p_289687_, MultiBufferSource p_289643_, int p_289683_, ArmorTrim p_289692_, net.minecraft.client.model.Model p_289663_, boolean p_289651_
    ) {
        TextureAtlasSprite textureatlassprite = this.armorTrimAtlas
                .getSprite(p_289651_ ? p_289692_.innerTexture(p_323506_) : p_289692_.outerTexture(p_323506_));
        VertexConsumer vertexconsumer = textureatlassprite.wrap(p_289643_.getBuffer(Sheets.armorTrimsSheet(p_289692_.pattern().value().decal())));
        p_289663_.renderToBuffer(p_289687_, vertexconsumer, p_289683_, OverlayTexture.NO_OVERLAY);
    }

    private void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, VexModel p_289659_) {
        renderGlint(p_289673_, p_289654_, p_289649_, (net.minecraft.client.model.Model) p_289659_);
    }
    private void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, net.minecraft.client.model.Model p_289659_) {
        p_289659_.renderToBuffer(p_289673_, p_289654_.getBuffer(RenderType.armorEntityGlint()), p_289649_, OverlayTexture.NO_OVERLAY);
    }

    private HumanoidModel getArmorModel(EquipmentSlot p_117079_) {
        return this.usesInnerModel(p_117079_) ? this.innerModel : this.outerModel;
    }

    private boolean usesInnerModel(EquipmentSlot p_117129_) {
        return p_117129_ == EquipmentSlot.LEGS;
    }

    /**
     * Hook to allow item-sensitive armor model. for HumanoidArmorLayer.
     */
    protected net.minecraft.client.model.Model getArmorModelHook(Vex entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
        return net.neoforged.neoforge.client.ClientHooks.getArmorModel(entity, itemStack, slot, model);
    }
}
