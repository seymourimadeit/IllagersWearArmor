package tallestegg.illagersweararmor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tallestegg.illagersweararmor.client.renderer.layers.IllagerArmorLayer;
import tallestegg.illagersweararmor.client.renderer.layers.VexArmorLayer;
import tallestegg.illagersweararmor.client.renderer.layers.WitchArmorLayer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IWAClientEvents {
    @SubscribeEvent
    public static void addLayer(EntityRenderersEvent.AddLayers event) {
        VexRenderer vexRenderer = event.getRenderer(EntityType.VEX);
        EntityRendererProvider.Context context = event.getContext();
        vexRenderer.addLayer(new VexArmorLayer(vexRenderer, event.getEntityModels(),context.getModelManager()));
        BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
            EntityRenderer entityRenderer = event.getRenderer(entityType);
            if (entityRenderer != null && entityRenderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer && livingEntityRenderer.getModel() instanceof IllagerModel) {
                livingEntityRenderer.addLayer(new IllagerArmorLayer(livingEntityRenderer, new HumanoidModel(context.bakeLayer(ModelLayers.GIANT_INNER_ARMOR)),
                        new HumanoidModel(context.bakeLayer(ModelLayers.GIANT_OUTER_ARMOR)), context.getModelManager()));
            }
        });
        WitchRenderer witchRenderer = event.getRenderer(EntityType.WITCH);
        witchRenderer.addLayer(new WitchArmorLayer<>(witchRenderer, new HumanoidModel(context.bakeLayer(ModelLayers.GIANT_INNER_ARMOR)),
                new HumanoidModel(context.bakeLayer(ModelLayers.GIANT_OUTER_ARMOR)), context.getModelManager()));
    }
}
