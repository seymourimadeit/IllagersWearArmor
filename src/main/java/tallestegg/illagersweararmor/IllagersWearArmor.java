package tallestegg.illagersweararmor;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import tallestegg.illagersweararmor.loot_tables.RaidWaveCondition;

@Mod(IllagersWearArmor.MODID)
public class IllagersWearArmor {
    private static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, IllagersWearArmor.MODID);
    public static final String MODID = "illagersweararmor";

    public IllagersWearArmor(IEventBus modEventBus, Dist dist, ModContainer container) {
        modEventBus.addListener(this::registerLootData);
        container.registerConfig(ModConfig.Type.COMMON, IWAConfig.COMMON_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, IWAConfig.CLIENT_SPEC);
        LOOT_CONDITION_TYPES.register(modEventBus);
        NeoForge.EVENT_BUS.register(IWASpawnEvents.class);
    }

    @SubscribeEvent
    private void registerLootData(RegisterEvent event) {
            if (!event.getRegistryKey().equals(Registries.LOOT_CONDITION_TYPE))
                return;
        LOOT_CONDITION_TYPES.register("wave", () -> RaidWaveCondition.TYPE);
    }
}
