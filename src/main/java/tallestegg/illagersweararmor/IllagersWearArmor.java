package tallestegg.illagersweararmor;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;

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
