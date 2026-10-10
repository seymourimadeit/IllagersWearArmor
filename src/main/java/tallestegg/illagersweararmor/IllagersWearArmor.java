package tallestegg.illagersweararmor;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;
import tallestegg.illagersweararmor.loot_tables.datagen.IllagerLootTableSubProvider;
import tallestegg.illagersweararmor.loot_tables.datagen.NaturalLootTableSubProvider;
import tallestegg.illagersweararmor.loot_tables.datagen.RaidLootTableSubProvider;

@Mod(IllagersWearArmor.MODID)
public class IllagersWearArmor {
    public static final String MODID = "illagersweararmor";

    public IllagersWearArmor(IEventBus modEventBus, Dist dist, ModContainer container) {
        container.registerConfig(ModConfig.Type.LOCAL, IWAConfig.COMMON_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, IWAConfig.CLIENT_SPEC);
        IWALootTables.LOOT_ITEM_CONDITION_TYPES.register(modEventBus);
        IWALootTables.LOOT_ITEM_FUNCTION_TYPES.register(modEventBus);
        IWALootTables.CONTEXT_KEY_SET.register(modEventBus);
        NeoForge.EVENT_BUS.register(IWASpawnEvents.class);
        modEventBus.addListener(IllagersWearArmor::onGatherRegistries);
    }


    private static void onGatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.add(Registries.LOOT_TABLE, bootstrapContext -> {
                })
                .lootTable((new LootTableProvider.SubProviderEntry(RaidLootTableSubProvider::new, IWALootTables.SLOT.get())))
                .lootTable(new LootTableProvider.SubProviderEntry(NaturalLootTableSubProvider::new, IWALootTables.SLOT.get()));
    }


    @Mod(value = IllagersWearArmor.MODID, dist = Dist.CLIENT)
    public static class IWAClient {
        public IWAClient(IEventBus modEventBus, Dist dist, ModContainer container) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}
