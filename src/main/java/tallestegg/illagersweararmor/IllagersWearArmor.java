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
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;
import tallestegg.illagersweararmor.loot_tables.RaidWaveCondition;

@Mod(IllagersWearArmor.MODID)
public class IllagersWearArmor {
    public static final String MODID = "illagersweararmor";

    public IllagersWearArmor(IEventBus modEventBus, Dist dist, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, IWAConfig.COMMON_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, IWAConfig.CLIENT_SPEC);
        IWALootTables.LOOT_ITEM_CONDITION_TYPES.register(modEventBus);
        IWALootTables.LOOT_ITEM_FUNCTION_TYPES.register(modEventBus);
        NeoForge.EVENT_BUS.register(IWASpawnEvents.class);
    }

    @Mod(value = IllagersWearArmor.MODID, dist = Dist.CLIENT)
    public static class IWAClient {
        public IWAClient(IEventBus modEventBus, Dist dist, ModContainer container) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}
