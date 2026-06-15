package tallestegg.illagersweararmor.client.renderer.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.joml.Vector3f;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.renderer.model.VillagerArmorModel;

import javax.annotation.Nullable;
import java.util.Map;

public class NonHumanoidArmorLayer<T extends LivingEntity, A extends EntityModel<T>> extends RenderLayer<T, A> {
    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
    private final HumanoidModel innerModel;
    private final HumanoidModel outerModel;
    private final TextureAtlas armorTrimAtlas;

    public NonHumanoidArmorLayer(RenderLayerParent<T, A> pRenderer, ModelManager manager, HumanoidModel innerModel, HumanoidModel outerModel) {
        super(pRenderer);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.armorTrimAtlas = manager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    public NonHumanoidArmorLayer(RenderLayerParent<T, A> pRenderer, EntityModelSet modelSets, ModelManager manager) {
        this(pRenderer, manager, new VillagerArmorModel(modelSets.bakeLayer(IWAClientEvents.ILLAGER_ARMOR_INNER_LAYER)), new VillagerArmorModel(modelSets.bakeLayer(IWAClientEvents.ILLAGER_ARMOR_OUTER_LAYER)));
    }

    public static void armorCrossArms(HumanoidModel model) {
        model.leftArm.y = 3.0F;
        model.leftArm.z = -1.0F;
        model.leftArm.xRot = -0.75F;
        model.rightArm.y = 3.0F;
        model.rightArm.z = -1.0F;
        model.rightArm.xRot = -0.75F;
    }

    public static void illagerArmorRendering(HumanoidModel pModel, EquipmentSlot pSlot) {
        pModel.setAllVisible(false);
        switch (pSlot) {
            case HEAD:
                pModel.head.visible = true;
                pModel.hat.visible = true;
                break;
            case CHEST:
                pModel.body.visible = true;
                pModel.rightArm.visible = true;
                pModel.leftArm.visible = true;
                pModel.body.offsetScale(new Vector3f(-0.100F, 0.0F, 0.2F));
                pModel.body.y += 0.5F;
                pModel.rightArm.x -= 1;
                pModel.leftArm.x += 1;
                break;
            case LEGS:
                pModel.body.visible = true;
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
                pModel.body.offsetScale(new Vector3f(-0.100F, 0.0F, 0.2F));
                break;
            case FEET:
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
        }
    }

    protected void copyPropertiesTo(HumanoidModel model, T livingEntity) {
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.CHEST, pPackedLight, this.getArmorModel(EquipmentSlot.CHEST));
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.LEGS, pPackedLight, this.getArmorModel(EquipmentSlot.LEGS));
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.FEET, pPackedLight, this.getArmorModel(EquipmentSlot.FEET));
        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.HEAD, pPackedLight, this.getArmorModel(EquipmentSlot.HEAD));
    }

    protected void renderArmorPiece(PoseStack pPoseStack, MultiBufferSource pBuffer, T pLivingEntity, EquipmentSlot pSlot, int pPackedLight, HumanoidModel pModel) {
        ItemStack itemstack = pLivingEntity.getItemBySlot(pSlot);
        Item item = itemstack.getItem();
        if (item instanceof ArmorItem armoritem) {
            if (armoritem.getEquipmentSlot() == pSlot) {
                this.getParentModel().copyPropertiesTo(pModel);
                this.copyPropertiesTo(pModel, pLivingEntity);
                this.setPartVisibility(pLivingEntity, pModel, pSlot);
                net.minecraft.client.model.Model model = getArmorModelHook(pLivingEntity, itemstack, pSlot, pModel);
                boolean flag = this.usesInnerModel(pSlot);
                if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) {
                    int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(itemstack);
                    float f = (float) (i >> 16 & 255) / 255.0F;
                    float f1 = (float) (i >> 8 & 255) / 255.0F;
                    float f2 = (float) (i & 255) / 255.0F;
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

    protected void setPartVisibility(T illager, HumanoidModel pModel, EquipmentSlot pSlot) {
    }

    protected void renderModel(PoseStack p_289664_, MultiBufferSource p_289689_, int p_289681_, ArmorItem p_289650_, net.minecraft.client.model.Model p_289658_, boolean p_289668_, float p_289678_, float p_289674_, float p_289693_, ResourceLocation armorResource) {
        VertexConsumer vertexconsumer = p_289689_.getBuffer(RenderType.armorCutoutNoCull(armorResource));
        p_289658_.renderToBuffer(p_289664_, vertexconsumer, p_289681_, OverlayTexture.NO_OVERLAY, p_289678_, p_289674_, p_289693_, 1.0F);
    }

    protected void renderTrim(ArmorMaterial p_289690_, PoseStack p_289687_, MultiBufferSource p_289643_, int p_289683_, ArmorTrim p_289692_, net.minecraft.client.model.Model p_289663_, boolean p_289651_) {
        TextureAtlasSprite textureatlassprite = this.armorTrimAtlas.getSprite(p_289651_ ? p_289692_.innerTexture(p_289690_) : p_289692_.outerTexture(p_289690_));
        VertexConsumer vertexconsumer = textureatlassprite.wrap(p_289643_.getBuffer(Sheets.armorTrimsSheet()));
        p_289663_.renderToBuffer(p_289687_, vertexconsumer, p_289683_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    protected void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, net.minecraft.client.model.Model p_289659_) {
        p_289659_.renderToBuffer(p_289673_, p_289654_.getBuffer(RenderType.armorEntityGlint()), p_289649_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }


    protected HumanoidModel getArmorModel(EquipmentSlot pSlot) {
        return this.usesInnerModel(pSlot) ? this.innerModel : this.outerModel;
    }

    protected boolean usesInnerModel(EquipmentSlot pSlot) {
        return pSlot == EquipmentSlot.LEGS;
    }

    /*=================================== FORGE START =========================================*/

    /**
     * Hook to allow item-sensitive armor model. for HumanoidArmorLayer.
     */
    protected net.minecraft.client.model.Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
        return net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
    }

    /**
     * More generic ForgeHook version of the above function, it allows for Items to have more control over what texture they provide.
     *
     * @param entity Entity wearing the armor
     * @param stack  ItemStack for the armor
     * @param slot   Slot ID that the item is in
     * @param type   Subtype, can be null or "overlay"
     * @return ResourceLocation pointing at the armor's texture
     */
    public ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
        }

        return resourcelocation;
    }
    /*=================================== FORGE END ===========================================*/
}

