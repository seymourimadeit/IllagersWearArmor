package tallestegg.illagersweararmor;

import com.baguchan.enchantwithmob.registry.ModEntities;

import baguchan.hunterillager.init.HunterEntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tallestegg.illagersweararmor.renderers.NewEnchanterRenderer;
import tallestegg.illagersweararmor.renderers.NewEvokerRenderer;
import tallestegg.illagersweararmor.renderers.NewHunterRenderer;
import tallestegg.illagersweararmor.renderers.NewIllusionerRenderer;
import tallestegg.illagersweararmor.renderers.NewPillagerRenderer;
import tallestegg.illagersweararmor.renderers.NewVindicatorRenderer;

@Mod(IllagersWearArmor.MODID)
public class IllagersWearArmor {
    public static final String MODID = "illagersweararmor";

    @SuppressWarnings("deprecation")
    public IllagersWearArmor() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(IWAEvents.class);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IWAConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, IWAConfig.CLIENT_SPEC);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        if (IWAConfig.PillagerRenderArmor)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.PILLAGER, NewPillagerRenderer::new);
        if (IWAConfig.VindicatorRenderArmor)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.VINDICATOR, NewVindicatorRenderer::new);
        if (IWAConfig.IllusionerRenderArmor)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.ILLUSIONER, NewIllusionerRenderer::new);
        if (IWAConfig.EvokerRenderArmor)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.EVOKER, NewEvokerRenderer::new);
        if (ModList.get().isLoaded("enchantwithmob") && IWAConfig.EnchanterRenderArmor)
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENCHANTER, NewEnchanterRenderer::new);
        if (ModList.get().isLoaded("hunterillager"))
            RenderingRegistry.registerEntityRenderingHandler(HunterEntityRegistry.HUNTERILLAGER, NewHunterRenderer::new);
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }
}
