package tallestegg.illagersweararmor;

import com.google.common.reflect.TypeToken;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import tallestegg.illagersweararmor.client.render.render_layers.IllagerArmorLayer;

@EventBusSubscriber(value = Dist.CLIENT)
public class IWAClientEvents {
    public static final ContextKey<ItemStack> HEAD_EQUIPMENT_CONTEXT = new ContextKey<>(
            // The id of your context key. Used for distinguishing between keys internally.
            Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "head_equipment_context"));
    public static final ContextKey<ItemStack> CHEST_EQUIPMENT_CONTEXT = new ContextKey<>(
            // The id of your context key. Used for distinguishing between keys internally.
            Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "chest_equipment_context"));
    public static final ContextKey<ItemStack> LEGS_EQUIPMENT_CONTEXT = new ContextKey<>(
            // The id of your context key. Used for distinguishing between keys internally.
            Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "legs_equipment_context"));
    public static final ContextKey<ItemStack> FEET_EQUIPMENT_CONTEXT = new ContextKey<>(
            // The id of your context key. Used for distinguishing between keys internally.
            Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "feet_equipment_context"));

    @SubscribeEvent
    public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(new TypeToken<IllagerRenderer<AbstractIllager, IllagerRenderState>>() {
                                     },
                (entity, state) -> {
                    state.setRenderData(HEAD_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.HEAD));
                    state.setRenderData(CHEST_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.CHEST));
                    state.setRenderData(LEGS_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.LEGS));
                    state.setRenderData(FEET_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.FEET));
                });
    }

    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        VindicatorRenderer renderer = event.getRenderer(EntityType.VINDICATOR);
        renderer.addLayer(new IllagerArmorLayer<>(renderer, ArmorModelSet.bake(ModelLayers.ARMOR_STAND_ARMOR, event.getContext().getModelSet(), HumanoidModel::new), event.getContext().getEquipmentRenderer()));
    }
}
