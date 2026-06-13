package tallestegg.illagersweararmor;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer;
import tallestegg.illagersweararmor.client.renderer.layers.VexArmorLayer;
import tallestegg.illagersweararmor.client.renderer.layers.WitchArmorLayer;

import static net.minecraft.client.model.geom.LayerDefinitions.INNER_ARMOR_DEFORMATION;
import static net.minecraft.client.model.geom.LayerDefinitions.OUTER_ARMOR_DEFORMATION;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IWAClientEvents {
    public static ModelLayerLocation ILLAGER_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID, "illager_outerarmor"), "illager_outerarmor");
    public static ModelLayerLocation ILLAGER_ARMOR_INNER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID, "illager_innerarmor"), "illager_innerarmor");
    public static ModelLayerLocation WITCH_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID, "witch_outerarmor"), "witch_outerarmor");
    public static ModelLayerLocation WITCH_ARMOR_INNER_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID, "witch_innerarmor"), "witch_innerarmor");


    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        EntityRendererProvider.Context context = event.getContext();
        ForgeRegistries.ENTITY_TYPES.forEach(entityType -> {
            if (!IWAConfig.CLIENT.renderBlackList.get().contains(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString())) {
                EntityRenderer entityRenderer = event.getEntityRenderer(entityType);
                if (entityRenderer != null && entityRenderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer) {
                    if (entityType == EntityType.VEX) {
                        VexRenderer vexRenderer = (VexRenderer) livingEntityRenderer;
                        vexRenderer.addLayer(new VexArmorLayer(vexRenderer, event.getEntityModels(), context.getModelManager()));
                    }
                    if (entityType == EntityType.WITCH) {
                        WitchRenderer witchRenderer = (WitchRenderer) livingEntityRenderer;
                        witchRenderer.addLayer(new WitchArmorLayer<>(witchRenderer, event.getEntityModels(), context.getModelManager()));
                    }
                    if (livingEntityRenderer.getModel() instanceof IllagerModel) {
                        livingEntityRenderer.addLayer(new IllagerArmorLayer(livingEntityRenderer, event.getEntityModels(), context.getModelManager()));
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ILLAGER_ARMOR_OUTER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(ILLAGER_ARMOR_INNER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(WITCH_ARMOR_OUTER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(WITCH_ARMOR_INNER_LAYER, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
    }
}
