package tallestegg.illagersweararmor;

import baguchan.enchantwithmob.client.ModModelLayers;
import baguchan.enchantwithmob.registry.ModEntities;
import baguchan.hunterillager.init.HunterEntityRegistry;
import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;
import tallestegg.illagersweararmor.client.model.mod_compat.EnchanterBipedModel;
import tallestegg.illagersweararmor.client.model.mod_compat.HunterIllagerBipedModel;
import tallestegg.illagersweararmor.client.model.mod_compat.SkirmisherBipedModel;
import tallestegg.illagersweararmor.client.renderer.EvokerBipedRenderer;
import tallestegg.illagersweararmor.client.renderer.IllusionerBipedRenderer;
import tallestegg.illagersweararmor.client.renderer.PillagerBipedRenderer;
import tallestegg.illagersweararmor.client.renderer.VindicatorBipedRenderer;
import tallestegg.illagersweararmor.client.renderer.mod_compat.*;

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

    @SubscribeEvent
    public static void render(EntityRenderersEvent.RegisterRenderers event) {
        if (!ModList.get().isLoaded("vanilla_animations")) {
            event.registerEntityRenderer(EntityType.PILLAGER, PillagerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.EVOKER, EvokerBipedRenderer::new);
            event.registerEntityRenderer(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
            event.registerEntityRenderer(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
        }
        if (ModList.get().isLoaded("enchantwithmob"))
            event.registerEntityRenderer(ModEntities.ENCHANTER.get(), EnchanterBipedRenderer::new);
        if (ModList.get().isLoaded("hunterillager"))
            event.registerEntityRenderer(HunterEntityRegistry.HUNTERILLAGER.get(), HunterIllagerBipedRenderer::new);
        if (ModList.get().isLoaded("takesapillage")) {
            event.registerEntityRenderer(ModEntityTypes.ARCHER.get(), ArcherBipedRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.LEGIONER.get(), LegionerBipedRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKIRMISHER.get(), SkirmisherBipedRenderer::new);
        }
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        if (ModList.get().isLoaded("enchantwithmob"))
            event.registerLayerDefinition(ModModelLayers.ENCHANTER, EnchanterBipedModel::createBodyLayer);
        if (ModList.get().isLoaded("hunterillager"))
            event.registerLayerDefinition(baguchan.hunterillager.init.ModModelLayers.HUNTERILLAGER,
                    HunterIllagerBipedModel::createBodyLayer);
        if (ModList.get().isLoaded("takesapillage"))
            event.registerLayerDefinition(SKRIMISHER, SkirmisherBipedModel::createBodyLayer);
        event.registerLayerDefinition(BIPEDILLAGER, IllagerBipedModel::createBodyLayer);
        event.registerLayerDefinition(BIPEDILLAGER_ARMOR_OUTER_LAYER, IllagerArmorModel::createOuterArmorLayer);
        event.registerLayerDefinition(ENCHANTER_ARMOR_OUTER_LAYER, IllagerArmorModel::createOuterEnchanterArmorLayer);
        event.registerLayerDefinition(BIPEDILLAGER_ARMOR_INNER_LAYER, IllagerArmorModel::createInnerArmorLayer);
    }
}
