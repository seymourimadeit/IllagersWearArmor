package tallestegg.illagersweararmor;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;
import tallestegg.illagersweararmor.loot_tables.RaidWaveCondition;

@Mod(IllagersWearArmor.MODID)
public class IllagersWearArmor {
    public static final String MODID = "zillagersweararmor";

    public IllagersWearArmor() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        IWALootTables.LOOT_ITEM_CONDITION_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        IWALootTables.LOOT_ITEM_FUNCTION_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IWAConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, IWAConfig.CLIENT_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }
}
