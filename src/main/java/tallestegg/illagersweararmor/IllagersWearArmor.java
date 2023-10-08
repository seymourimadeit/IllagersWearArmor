package tallestegg.illagersweararmor;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegisterEvent;
import tallestegg.illagersweararmor.loot_tables.RaidWaveCondition;

@Mod(IllagersWearArmor.MODID)
public class IllagersWearArmor {
    private static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, IllagersWearArmor.MODID);
    public static final String MODID = "zillagersweararmor";

    public IllagersWearArmor() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLootData);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IWAConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, IWAConfig.CLIENT_SPEC);
        LOOT_CONDITION_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void registerLootData(RegisterEvent event) {
            if (!event.getRegistryKey().equals(Registries.LOOT_CONDITION_TYPE))
                return;
        LOOT_CONDITION_TYPES.register("wave", () -> RaidWaveCondition.TYPE);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }
}
