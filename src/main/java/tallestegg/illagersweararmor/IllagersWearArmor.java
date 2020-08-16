package tallestegg.illagersweararmor;


import net.minecraft.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tallestegg.illagersweararmor.renderers.NewEvokerRenderer;
import tallestegg.illagersweararmor.renderers.NewIllusionerRenderer;
import tallestegg.illagersweararmor.renderers.NewPillagerRenderer;
import tallestegg.illagersweararmor.renderers.NewVindicatorRenderer;

@Mod(IllagersWearArmor.MODID)
public class IllagersWearArmor
{	
	public static final String MODID = "illagersweararmor";
	
    public IllagersWearArmor() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IWAConfig.COMMON_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(IWAEvents.class);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
    	RenderingRegistry.registerEntityRenderingHandler(EntityType.PILLAGER, NewPillagerRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityType.VINDICATOR, NewVindicatorRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityType.ILLUSIONER, NewIllusionerRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityType.EVOKER, NewEvokerRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) 
    {
    	
    }
}
