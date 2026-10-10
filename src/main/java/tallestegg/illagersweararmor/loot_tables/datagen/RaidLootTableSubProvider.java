package tallestegg.illagersweararmor.loot_tables.datagen;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class RaidLootTableSubProvider extends IllagerLootTableSubProvider {

    public RaidLootTableSubProvider(LootTableSubProvider.Context output) {
        super(output);
    }

    @Override
    public void run() {
        acceptRaidLootTable("chainmail", false);
        acceptRaidLootTable("copper", true);
        acceptRaidLootTable("diamond", true);
        acceptRaidLootTable("golden", true);
        acceptRaidLootTable("iron", true);
        acceptRaidLootTable("leather", false);
    }

    public void acceptRaidLootTable(String armorSet, boolean addTrim) {
        acceptLootTable(armorSet, addTrim, "_raid", "raid_armor_sets/", 0.5F);
    }
}
