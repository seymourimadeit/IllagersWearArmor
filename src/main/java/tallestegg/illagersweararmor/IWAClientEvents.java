package tallestegg.illagersweararmor;

import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EvokerRenderer;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;
import tallestegg.illagersweararmor.client.model.WitchBipedModel;
import tallestegg.illagersweararmor.client.model.mod_compat.SkirmisherBipedModel;
import tallestegg.illagersweararmor.client.renderer.*;
import tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer;
import tallestegg.illagersweararmor.client.renderer.layers.VexArmorLayer;
import tallestegg.illagersweararmor.client.renderer.mod_compat.ArcherBipedRenderer;
import tallestegg.illagersweararmor.client.renderer.mod_compat.LegionerBipedRenderer;
import tallestegg.illagersweararmor.client.renderer.mod_compat.SkirmisherBipedRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IWAClientEvents {
    public static ModelLayerLocation BIPEDILLAGER = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "illagerbiped"), "illagerbiped");
    public static ModelLayerLocation BIPEDILLAGER_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "illagerbiped_outerarmor"), "illagerbiped_outerarmor");
    public static ModelLayerLocation ENCHANTER_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "enchanterbiped_outerarmor"), "enchanterbiped_outerarmor");
    public static ModelLayerLocation BIPEDILLAGER_ARMOR_INNER_LAYER = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "illagerbiped_innerarmor"), "illagerbiped_innerarmor");
    public static ModelLayerLocation SKRIMISHER = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "skrimisherbiped"), "skrimisherbiped");
    public static ModelLayerLocation WITCH = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "witch_biped"), "witch_biped");
    public static ModelLayerLocation VEX_BIPED = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "vex_biped"), "vex_biped");
    public static ModelLayerLocation VEX_ARMOR_OUTER_LAYER = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "vex_outerarmor"), "vex_outerarmor");
    public static ModelLayerLocation VEX_ARMOR_INNER_LAYER = new ModelLayerLocation(
            new ResourceLocation(IllagersWearArmor.MODID + "vex_innerarmor"), "vex_innerarmor");

    @SubscribeEvent
    public static void render(EntityRenderersEvent.RegisterRenderers event) {
        if (!ModList.get().isLoaded("vanilla_animations")) {
            event.registerEntityRenderer(EntityType.PILLAGER, PillagerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.EVOKER, EvokerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
            event.registerEntityRenderer(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.WITCH, WitchBipedRenderer::new);
        }
        if (ModList.get().isLoaded("takesapillage")) {
            event.registerEntityRenderer(ModEntityTypes.ARCHER.get(), ArcherBipedRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.LEGIONER.get(), LegionerBipedRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKIRMISHER.get(), SkirmisherBipedRenderer::new);
        }
    }

    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        VexRenderer renderer = event.getRenderer(EntityType.VEX);
        renderer.addLayer(new VexArmorLayer(renderer, event.getEntityModels(), event.getContext().getModelManager()));
       // LivingEntityRenderer illagerRenderer = event.getRenderer(EntityType.EVOKER);
        //illagerRenderer.addLayer(new IllagerArmorLayer<Evoker>(illagerRenderer, event.getEntityModels(), event.getContext().getModelManager()));
        // New system for illager armor rendering that doesn't override the models
        // Still very WIP ! Will not implement until 100% happy with it
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WITCH, WitchBipedModel::createBodyModel);
        event.registerLayerDefinition(BIPEDILLAGER, IllagerBipedModel::createBodyLayer);
        event.registerLayerDefinition(BIPEDILLAGER_ARMOR_OUTER_LAYER, IllagerArmorModel::createOuterArmorLayer);
        event.registerLayerDefinition(ENCHANTER_ARMOR_OUTER_LAYER, IllagerArmorModel::createOuterEnchanterArmorLayer);
        event.registerLayerDefinition(BIPEDILLAGER_ARMOR_INNER_LAYER, IllagerArmorModel::createInnerArmorLayer);
        if (ModList.get().isLoaded("takesapillage"))
            event.registerLayerDefinition(SKRIMISHER, SkirmisherBipedModel::createBodyLayer);
    }
}
