package tallestegg.illagersweararmor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;
import tallestegg.illagersweararmor.client.model.WitchBipedModel;
import tallestegg.illagersweararmor.client.renderer.*;
import tallestegg.illagersweararmor.client.renderer.layers.VexArmorLayer;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IWAClientEvents {
    public static ModelLayerLocation BIPEDILLAGER = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "illagerbiped"), "illagerbiped");
    public static ModelLayerLocation BIPEDILLAGER_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "illagerbiped_outerarmor"), "illagerbiped_outerarmor");
    public static ModelLayerLocation ENCHANTER_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "enchanterbiped_outerarmor"), "enchanterbiped_outerarmor");
    public static ModelLayerLocation BIPEDILLAGER_ARMOR_INNER_LAYER = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "illagerbiped_innerarmor"), "illagerbiped_innerarmor");
    public static ModelLayerLocation SKRIMISHER = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "skrimisherbiped"), "skrimisherbiped");
    public static ModelLayerLocation WITCH = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "witch_biped"), "witch_biped");
    public static ModelLayerLocation VEX_BIPED = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "vex_biped"), "vex_biped");
    public static ModelLayerLocation VEX_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "vex_outerarmor"), "vex_outerarmor");
    public static ModelLayerLocation VEX_ARMOR_INNER_LAYER = new ModelLayerLocation(
            ResourceLocation.parse(IllagersWearArmor.MODID + "vex_innerarmor"), "vex_innerarmor");

    @SubscribeEvent
    public static void render(EntityRenderersEvent.RegisterRenderers event) {
        if (!ModList.get().isLoaded("vanilla_animations")) {
            event.registerEntityRenderer(EntityType.PILLAGER, PillagerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.EVOKER, EvokerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
            event.registerEntityRenderer(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.WITCH, WitchBipedRenderer::new);
        }
   /*     if (ModList.get().isLoaded("takesapillage")) {
            event.registerEntityRenderer(ModEntityTypes.ARCHER.get(), ArcherBipedRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.LEGIONER.get(), LegionerBipedRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKIRMISHER.get(), SkirmisherBipedRenderer::new);
        }*/
    }

    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        VexRenderer renderer = event.getRenderer(EntityType.VEX);
        renderer.addLayer(new VexArmorLayer(renderer, event.getEntityModels(), event.getContext().getModelManager()));
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WITCH, WitchBipedModel::createBodyModel);
        event.registerLayerDefinition(BIPEDILLAGER, IllagerBipedModel::createBodyLayer);
        event.registerLayerDefinition(BIPEDILLAGER_ARMOR_OUTER_LAYER, IllagerArmorModel::createOuterArmorLayer);
        event.registerLayerDefinition(BIPEDILLAGER_ARMOR_INNER_LAYER, IllagerArmorModel::createInnerArmorLayer);
    }
}
