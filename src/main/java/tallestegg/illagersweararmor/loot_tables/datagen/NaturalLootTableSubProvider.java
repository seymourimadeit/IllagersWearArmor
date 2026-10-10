package tallestegg.illagersweararmor.loot_tables.datagen;

import net.minecraft.data.loot.LootTableSubProvider;

public class NaturalLootTableSubProvider extends IllagerLootTableSubProvider {

    public NaturalLootTableSubProvider(LootTableSubProvider.Context output) {
        super(output);
    }

    @Override
    public void run() {
        acceptRaidLootTable("chainmail", false, 0.12F);
        acceptRaidLootTable("copper", true, 0.32F);
        acceptRaidLootTable("diamond", true, 0.04F);
        acceptRaidLootTable("golden", true, 0.33F);
        acceptRaidLootTable("iron", true, 0.13F);
        acceptRaidLootTable("leather", false, 0.36F);
    }

    public void acceptRaidLootTable(String armorSet, boolean addTrim, float chance) {
        acceptLootTable(armorSet, addTrim, "", "", chance);
    }
}
