package tallestegg.illagersweararmor;

import com.google.common.reflect.TypeToken;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.model.monster.witch.WitchModel;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.WitchArmorModel;
import tallestegg.illagersweararmor.client.render.render_layers.NonHumanoidArmorLayer;

import java.util.function.Function;

@EventBusSubscriber(value = Dist.CLIENT)
public class IWAClientEvents {
    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, path);
    }
    
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
    public static final ModelLayerLocation ILLAGER_ARMOR_HEAD = new ModelLayerLocation(id("illager_armor/head"), "main");
    public static final ModelLayerLocation ILLAGER_ARMOR_CHEST = new ModelLayerLocation(id("illager_armor/chest"), "main");
    public static final ModelLayerLocation ILLAGER_ARMOR_LEGS = new ModelLayerLocation(id("illager_armor/legs"), "main");
    public static final ModelLayerLocation ILLAGER_ARMOR_FEET = new ModelLayerLocation(id("illager_armor/feet"), "main");
    public static final ArmorModelSet<ModelLayerLocation> ILLAGER_ARMOR = new ArmorModelSet<>(ILLAGER_ARMOR_HEAD, ILLAGER_ARMOR_CHEST, ILLAGER_ARMOR_LEGS, ILLAGER_ARMOR_FEET);
    public static final ModelLayerLocation WITCH_ARMOR_HEAD = new ModelLayerLocation(id("witch_armor/head"), "main");
    public static final ModelLayerLocation WITCH_ARMOR_CHEST = new ModelLayerLocation(id("witch_armor/chest"), "main");
    public static final ModelLayerLocation WITCH_ARMOR_LEGS = new ModelLayerLocation(id("witch_armor/legs"), "main");
    public static final ModelLayerLocation WITCH_ARMOR_FEET = new ModelLayerLocation(id("witch_armor/feet"), "main");
    public static final ArmorModelSet<ModelLayerLocation> WITCH_ARMOR = new ArmorModelSet<>(WITCH_ARMOR_HEAD, WITCH_ARMOR_CHEST, WITCH_ARMOR_LEGS, WITCH_ARMOR_FEET);

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
        event.registerEntityModifier(new TypeToken<WitchRenderer>() {
        }, (entity, state) -> {
            state.setRenderData(HEAD_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.HEAD));
            state.setRenderData(CHEST_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.CHEST));
            state.setRenderData(LEGS_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.LEGS));
            state.setRenderData(FEET_EQUIPMENT_CONTEXT, entity.getItemBySlot(EquipmentSlot.FEET));
        });
    }

    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        EntityRendererProvider.Context context = event.getContext();
        BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
            EntityRenderer entityRenderer = event.getRenderer(entityType);
            if (entityRenderer != null && entityRenderer instanceof LivingEntityRenderer<?, ?, ?> livingEntityRenderer) {
                if (!IWAConfig.CLIENT.renderBlackList.get().contains(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString())) {
                    if (livingEntityRenderer.getModel() instanceof IllagerModel) {
                        livingEntityRenderer.addLayer(new NonHumanoidArmorLayer(livingEntityRenderer, nonHumanoidBake(ILLAGER_ARMOR, context.getModelSet(), modelPart -> new IllagerArmorModel(modelPart)), context.getEquipmentRenderer()));
                    }
                    if (entityType == EntityType.WITCH) {
                        livingEntityRenderer.addLayer(new NonHumanoidArmorLayer(livingEntityRenderer, nonHumanoidBake(WITCH_ARMOR, context.getModelSet(), modelPart -> new WitchModel(modelPart)), context.getEquipmentRenderer()));
                    }
                }
            }
        });
    }

    public static <M extends EntityModel<?>> ArmorModelSet<M> nonHumanoidBake(ArmorModelSet<ModelLayerLocation> locations, EntityModelSet modelSet, Function<ModelPart, M> factory) {
        return locations.map(id -> factory.apply(modelSet.bakeLayer(id)));
    }


    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        registerArmorLayerDefs(event, ILLAGER_ARMOR, IllagerArmorModel.createArmorMeshSet(new CubeDeformation(0.5F), new CubeDeformation(1.0F)));
        registerArmorLayerDefs(event, WITCH_ARMOR, WitchArmorModel.createArmorMeshSet(new CubeDeformation(0.5F), new CubeDeformation(1.0F)));
    }

    private static void registerArmorLayerDefs(EntityRenderersEvent.RegisterLayerDefinitions event, ArmorModelSet<ModelLayerLocation> targets, ArmorModelSet<MeshDefinition> meshes) {
        event.registerLayerDefinition(targets.head(), () -> LayerDefinition.create(meshes.head(), 64, 32));
        event.registerLayerDefinition(targets.chest(), () -> LayerDefinition.create(meshes.chest(), 64, 32));
        event.registerLayerDefinition(targets.legs(), () -> LayerDefinition.create(meshes.legs(), 64, 32));
        event.registerLayerDefinition(targets.feet(), () -> LayerDefinition.create(meshes.feet(), 64, 32));
    }
}
