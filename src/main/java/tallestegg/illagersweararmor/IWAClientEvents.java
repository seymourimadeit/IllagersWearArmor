package tallestegg.illagersweararmor;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer;
import tallestegg.illagersweararmor.client.renderer.layers.VexArmorLayer;
import tallestegg.illagersweararmor.client.renderer.layers.WitchArmorLayer;

import static net.minecraft.client.model.geom.LayerDefinitions.INNER_ARMOR_DEFORMATION;
import static net.minecraft.client.model.geom.LayerDefinitions.OUTER_ARMOR_DEFORMATION;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IWAClientEvents {
    public static ModelLayerLocation ILLAGER_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID,  "illager_outerarmor"), "illager_outerarmor");
    public static ModelLayerLocation ILLAGER_ARMOR_INNER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID,  "illager_innerarmor"), "illager_innerarmor");
    public static ModelLayerLocation WITCH_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID,  "witch_outerarmor"), "witch_outerarmor");
    public static ModelLayerLocation WITCH_ARMOR_INNER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID,  "witch_innerarmor"), "witch_innerarmor");

    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        VexRenderer vexRenderer = event.getRenderer(EntityType.VEX);
        EntityRendererProvider.Context context = event.getContext();
        vexRenderer.addLayer(new VexArmorLayer(vexRenderer, event.getEntityModels(),context.getModelManager()));
        BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
            if (IWAConfig.CLIENT.renderBlackList.get().contains(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString()))
                return;
            EntityRenderer entityRenderer = event.getRenderer(entityType);
            if (entityRenderer != null && entityRenderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer && livingEntityRenderer.getModel() instanceof IllagerModel) {
                livingEntityRenderer.addLayer(new IllagerArmorLayer(livingEntityRenderer, new HumanoidModel(context.bakeLayer(ILLAGER_ARMOR_INNER_LAYER)),
                        new HumanoidModel(context.bakeLayer(ILLAGER_ARMOR_OUTER_LAYER)), context.getModelManager()));
            }
        });
        WitchRenderer witchRenderer = event.getRenderer(EntityType.WITCH);
        witchRenderer.addLayer(new WitchArmorLayer<>(witchRenderer, new HumanoidModel(context.bakeLayer(WITCH_ARMOR_INNER_LAYER)),
                new HumanoidModel(context.bakeLayer(WITCH_ARMOR_OUTER_LAYER)), context.getModelManager()));
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ILLAGER_ARMOR_OUTER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(ILLAGER_ARMOR_INNER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(WITCH_ARMOR_OUTER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(WITCH_ARMOR_INNER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
    }
}
