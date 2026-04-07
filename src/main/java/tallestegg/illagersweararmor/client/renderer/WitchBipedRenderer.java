package tallestegg.illagersweararmor.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.WitchBipedModel;
import tallestegg.illagersweararmor.client.model.render_states.WitchBipedRenderState;
import tallestegg.illagersweararmor.client.renderer.layers.WitchBipedItemLayer;

public class WitchBipedRenderer<T extends Witch> extends MobRenderer<T, WitchBipedRenderState, WitchBipedModel<WitchBipedRenderState>> {
    private static final Identifier WITCH_LOCATION = Identifier.withDefaultNamespace("textures/entity/witch/witch.png");

    public WitchBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new WitchBipedModel(builder.bakeLayer(IWAClientEvents.WITCH)), 0.5F);
        this.addLayer(new WitchBipedItemLayer<>(this));
        this.addLayer(new CustomHeadLayer<>(this, builder.getModelSet(), builder.getPlayerSkinRenderCache()));
        ArmorModelSet<HumanoidModel<WitchBipedRenderState>> armorModels =
                ArmorModelSet.bake(IWAClientEvents.ILLAGER_ARMOR, builder.getModelSet(), IllagerArmorModel::new);
        this.addLayer(new HumanoidArmorLayer(this, armorModels, builder.getEquipmentRenderer()));
    }

    @Override
    public WitchBipedRenderState createRenderState() {
        return new WitchBipedRenderState();
    }

    @Override
    public void extractRenderState(T entity, WitchBipedRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        HumanoidMobRenderer.extractHumanoidRenderState(entity, state, partialTicks, this.itemModelResolver);
        WitchBipedRenderState.extractHoldingEntityRenderState(entity, state, this.itemModelResolver);
        state.isWearingChestplateOrLegging = entity.hasItemInSlot(EquipmentSlot.CHEST) || entity.hasItemInSlot(EquipmentSlot.LEGS);
        state.entityId = entity.getId();
        ItemStack mainHandItem = entity.getMainHandItem();
        state.isHoldingItem = !mainHandItem.isEmpty();
        state.isHoldingPotion = mainHandItem.is(Items.POTION);
    }


    @Override
    protected void scale(WitchBipedRenderState state, PoseStack poseStack) {
        super.scale(state, poseStack);
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public Identifier getTextureLocation(WitchBipedRenderState state) {
        return WITCH_LOCATION;
    }
}