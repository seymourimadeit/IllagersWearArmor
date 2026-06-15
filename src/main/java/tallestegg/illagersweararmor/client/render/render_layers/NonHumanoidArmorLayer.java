package tallestegg.illagersweararmor.client.render.render_layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import tallestegg.illagersweararmor.IWAClientEvents;

public class NonHumanoidArmorLayer<S extends LivingEntityRenderState, M extends EntityModel<S>, A extends EntityModel<S>> extends RenderLayer<S, M> {
    protected final ArmorModelSet<A> modelSet;
    protected final ArmorModelSet<A> babyModelSet;
    protected final EquipmentLayerRenderer equipmentRenderer;

    public NonHumanoidArmorLayer(RenderLayerParent<S, M> renderer, ArmorModelSet<A> modelSet, EquipmentLayerRenderer equipmentRenderer) {
        this(renderer, modelSet, modelSet, equipmentRenderer);
    }

    public NonHumanoidArmorLayer(RenderLayerParent<S, M> renderer, ArmorModelSet<A> modelSet, ArmorModelSet<A> babyModelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.modelSet = modelSet;
        this.babyModelSet = babyModelSet;
        this.equipmentRenderer = equipmentRenderer;
    }

    public static boolean shouldRender(ItemStack itemStack, EquipmentSlot slot) {
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        return equippable != null && shouldRender(equippable, slot);
    }

    public static boolean shouldRender(Equippable equippable, EquipmentSlot slot) {
        return equippable.assetId().isPresent() && equippable.slot() == slot;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
        this.renderArmorPiece(poseStack, submitNodeCollector, state.getRenderData(IWAClientEvents.CHEST_EQUIPMENT_CONTEXT), EquipmentSlot.CHEST, lightCoords, state);
        this.renderArmorPiece(poseStack, submitNodeCollector, state.getRenderData(IWAClientEvents.LEGS_EQUIPMENT_CONTEXT), EquipmentSlot.LEGS, lightCoords, state);
        this.renderArmorPiece(poseStack, submitNodeCollector, state.getRenderData(IWAClientEvents.FEET_EQUIPMENT_CONTEXT), EquipmentSlot.FEET, lightCoords, state);
        this.renderArmorPiece(poseStack, submitNodeCollector, state.getRenderData(IWAClientEvents.HEAD_EQUIPMENT_CONTEXT), EquipmentSlot.HEAD, lightCoords, state);
    }

    protected void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, EquipmentSlot slot, int lightCoords, S state) {
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && shouldRender(equippable, slot)) {
            A model = this.getArmorModel(state, slot);
            poseStack.pushPose();
            EquipmentClientInfo.LayerType layerType = state.isBaby && state.entityType != EntityType.ARMOR_STAND ? EquipmentClientInfo.LayerType.HUMANOID_BABY : (this.usesInnerModel(slot) ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS : EquipmentClientInfo.LayerType.HUMANOID);
            this.equipmentRenderer.renderLayers(layerType, equippable.assetId().orElseThrow(), model, state, itemStack, poseStack, submitNodeCollector, lightCoords, state.outlineColor);
            poseStack.popPose();
        }
    }

    protected A getArmorModel(S state, EquipmentSlot slot) {
        return (state.isBaby ? this.babyModelSet : this.modelSet).get(slot);
    }

    protected boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    public static void modelCopyFrom(ModelPart modelPart1, ModelPart modelPart2) {
        modelPart1.xScale = modelPart2.xScale;
        modelPart1.yScale = modelPart2.yScale;
        modelPart1.zScale = modelPart2.zScale;
        modelPart1.xRot = modelPart2.xRot;
        modelPart1.yRot = modelPart2.yRot;
        modelPart1.zRot = modelPart2.zRot;
        modelPart1.x = modelPart2.x;
        modelPart1.y = modelPart2.y;
        modelPart1.z = modelPart2.z;
    }

    public static void armorCrossArms(HumanoidModel model) {
        model.leftArm.y = 3.0F;
        model.leftArm.z = -1.0F;
        model.leftArm.xRot = -0.75F;
        model.rightArm.y = 3.0F;
        model.rightArm.z = -1.0F;
        model.rightArm.xRot = -0.75F;
    }
}

