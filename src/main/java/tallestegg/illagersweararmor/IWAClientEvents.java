package tallestegg.illagersweararmor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.IllagerBipedModel;
import tallestegg.illagersweararmor.client.model.WitchBipedModel;
import tallestegg.illagersweararmor.client.renderer.*;

@EventBusSubscriber(value = Dist.CLIENT)
public class IWAClientEvents {
    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, path);
    }
    public static ModelLayerLocation BIPEDILLAGER = new ModelLayerLocation(
            id("illagerbiped"), "illagerbiped");
    public static ModelLayerLocation WITCH = new ModelLayerLocation(
            id("witch_biped"), "witch_biped");
    public static final ModelLayerLocation ILLAGER_ARMOR_HEAD = new ModelLayerLocation(id("illager_armor/head"), "main");
    public static final ModelLayerLocation ILLAGER_ARMOR_CHEST = new ModelLayerLocation(id("illager_armor/chest"), "main");
    public static final ModelLayerLocation ILLAGER_ARMOR_LEGS = new ModelLayerLocation(id("illager_armor/legs"), "main");
    public static final ModelLayerLocation ILLAGER_ARMOR_FEET = new ModelLayerLocation(id("illager_armor/feet"), "main");
    public static final ArmorModelSet<ModelLayerLocation> ILLAGER_ARMOR = new ArmorModelSet<>(ILLAGER_ARMOR_HEAD, ILLAGER_ARMOR_CHEST, ILLAGER_ARMOR_LEGS, ILLAGER_ARMOR_FEET);

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
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BIPEDILLAGER, IllagerBipedModel::createBodyLayer);
        event.registerLayerDefinition(WITCH, WitchBipedModel::createBodyModel);
        registerArmorLayerDefs(event, ILLAGER_ARMOR, IllagerArmorModel.createArmorMeshSet(new CubeDeformation(0.5F), new CubeDeformation(1.0F)));
    }

    private static void registerArmorLayerDefs(EntityRenderersEvent.RegisterLayerDefinitions event, ArmorModelSet<ModelLayerLocation> targets, ArmorModelSet<MeshDefinition> meshes) {
        event.registerLayerDefinition(targets.head(), () -> LayerDefinition.create(meshes.head(), 64, 32));
        event.registerLayerDefinition(targets.chest(), () -> LayerDefinition.create(meshes.chest(), 64, 32));
        event.registerLayerDefinition(targets.legs(), () -> LayerDefinition.create(meshes.legs(), 64, 32));
        event.registerLayerDefinition(targets.feet(), () -> LayerDefinition.create(meshes.feet(), 64, 32));
    }

}
