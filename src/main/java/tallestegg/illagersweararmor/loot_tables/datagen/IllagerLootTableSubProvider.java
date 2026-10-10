package tallestegg.illagersweararmor.loot_tables.datagen;

import com.ibm.icu.impl.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import oshi.util.tuples.Triplet;
import tallestegg.illagersweararmor.IllagersWearArmor;
import tallestegg.illagersweararmor.loot_tables.ArmorSlotFunction;
import tallestegg.illagersweararmor.loot_tables.RaidWaveCondition;

import java.util.List;

public class IllagerLootTableSubProvider implements LootTableSubProvider {
    protected final LootTableSubProvider.Context output;

    public IllagerLootTableSubProvider(LootTableSubProvider.Context output) {
        this.output = output;
    }

    List<Triplet<String, Float, Integer>> sets = List.of(
            new Triplet("chainmail", 0.12F, 3),
            new Triplet("copper", 0.32F, 1),
            new Triplet("diamond", 0.04F, 5),
            new Triplet("gold", 0.33F, 2),
            new Triplet("iron", 0.13F, 4),
            new Triplet("leather", 0.36F, 0));

    @Override
    public void run() {
        createIllagerGatheringLootTable();
        createIllagerGatheringLootTableRaid();
    }

    public void createIllagerGatheringLootTable() {
        Identifier lootTablePath = Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/illager");
        ResourceKey<LootTable> table = ResourceKey.create(Registries.LOOT_TABLE, lootTablePath);
        LootPool.Builder lootPoolBuilder = LootPool.lootPool().setRolls(ContextIntProviders.exactly(1));
        for (Triplet<String, Float, Integer> entry : sets) {
            ResourceKey<LootTable> nestedTable = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/armor_set/" + entry.getA()));
            lootPoolBuilder.add(NestedLootTable.lootTableReference(output.lookup(Registries.LOOT_TABLE).getOrThrow(nestedTable)).when(LootItemRandomChanceCondition.randomChance(entry.getB())));
        }
        lootPoolBuilder.build();
        this.output.accept(table, new LootTable.Builder().withPool(lootPoolBuilder));
    }

    public void createIllagerGatheringLootTableRaid() {
        Identifier lootTablePath = Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/illager_raid");
        ResourceKey<LootTable> table = ResourceKey.create(Registries.LOOT_TABLE, lootTablePath);
        LootPool.Builder lootPoolBuilder = LootPool.lootPool().setRolls(ContextIntProviders.exactly(1));
        for (Triplet<String, Float, Integer> entry : sets) {
            ResourceKey<LootTable> nestedTable = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/armor_set/raid_armor_sets/" + entry.getA() + "_raid"));
            lootPoolBuilder.add(NestedLootTable.lootTableReference(output.lookup(Registries.LOOT_TABLE).getOrThrow(nestedTable)).when(RaidWaveCondition.wave(entry.getC())));
        }
        for (int i = 3; i > 0; i--) {
            lootPoolBuilder.add(NestedLootTable.lootTableReference(output.lookup(Registries.LOOT_TABLE).getOrThrow(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/armor_set/raid_armor_sets/diamond_raid")))).when(LootItemRandomChanceCondition.randomChance(5 + i)));
        }
        lootPoolBuilder.build();
        this.output.accept(table, new LootTable.Builder().withPool(lootPoolBuilder));
    }


    public void acceptLootTable(String armorSet, boolean addTrim, String additionalArgs, String armorPath, float chance) {
        this.output.accept(createLootTable(armorSet, additionalArgs, armorPath), buildArmorLootTable(armorSet, addTrim, chance));
    }


    public static ResourceKey<LootTable> createLootTable(String armor, String additionalArgs, String armorPath) {
        Identifier lootTable = Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/armor_sets/" + armorPath + armor + additionalArgs);
        return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
    }

    public LootTable.Builder buildArmorLootTable(String setName, boolean addTrim, float chance) {
        LootTable.Builder builder = LootTable.lootTable();
        LootPool.Builder lootPoolBuilder = LootPool.lootPool().setRolls(ContextIntProviders.exactly(1));
        List<Pair<String, EquipmentSlot>> equipment = List.of(Pair.of("_helmet", EquipmentSlot.HEAD), Pair.of("_chestplate", EquipmentSlot.CHEST), Pair.of("_leggings", EquipmentSlot.LEGS), Pair.of("_boots", EquipmentSlot.FEET));
        HolderGetter<TrimPattern> trimPatterns = this.output.lookup(Registries.TRIM_PATTERN);
        HolderGetter<TrimMaterial> trimMaterial = this.output.lookup(Registries.TRIM_MATERIAL);
        for (Pair<String, EquipmentSlot> equipmentName : equipment) {
            for (Item item : BuiltInRegistries.ITEM) {
                String itemName = BuiltInRegistries.ITEM.getKey(item).getPath();
                if (setName.concat(equipmentName.first).equals(itemName)) {
                    UniformContainerBase.Builder<?> lootItem = lootItem(item).when(LootItemRandomChanceCondition.randomChance(chance)).apply(ArmorSlotFunction.armorSlotFunction(equipmentName.second));
                    if (addTrim)
                        lootItem.apply(SetComponentsFunction.setComponent(DataComponents.TRIM, new ArmorTrim(trimMaterial.getOrThrow(TrimMaterials.EMERALD), trimPatterns.getOrThrow(TrimPatterns.SENTRY))));
                    lootPoolBuilder.add(lootItem);
                }
            }
        }
        lootPoolBuilder.build();
        return builder.withPool(lootPoolBuilder);
    }

    public UniformContainerBase.Builder<?> lootItem(Item item) {
        return LootItem.lootTableItem(item);
    }
}

