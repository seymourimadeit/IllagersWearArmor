package tallestegg.illagersweararmor.loot_tables;

import java.util.function.Consumer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.fml.common.Mod;
import tallestegg.illagersweararmor.IllagersWearArmor;


public class IWALootTables {
    public static final BiMap<ResourceLocation, LootContextParamSet> REGISTRY = HashBiMap.create();
    public static final LootContextParamSet SLOT = register("slot", (table) -> {
        table.required(LootContextParams.THIS_ENTITY);
    });

    public static final ResourceKey<LootTable> ILLAGER_HELMET = registerLootTable(
            "entities/illager_helmet");
    public static final ResourceKey<LootTable> ILLAGER_CHEST = registerLootTable(
            "entities/illager_chestplate");
    public static final ResourceKey<LootTable> ILLAGER_LEGGINGS = registerLootTable(
            "entities/illager_legs");
    public static final ResourceKey<LootTable> ILLAGER_FEET = registerLootTable(
            "entities/illager_feet");
    public static final ResourceKey<LootTable> NATURAL_SPAWN_ILLAGER_HELMET = registerLootTable(
            "entities/natural_spawn/illager_helmet");
    public static final ResourceKey<LootTable> NATURAL_SPAWN_ILLAGER_CHEST = registerLootTable(
            "entities/natural_spawn/illager_chestplate");
    public static final ResourceKey<LootTable> NATURAL_SPAWN_ILLAGER_LEGGINGS = registerLootTable(
            "entities/natural_spawn/illager_legs");
    public static final ResourceKey<LootTable> NATURAL_SPAWN_ILLAGER_FEET = registerLootTable(
            "entities/natural_spawn/illager_feet");
    public static final ResourceKey<LootTable> VEX_HELMET = registerLootTable(
            "entities/natural_spawn/vex_helmet");
    public static final ResourceKey<LootTable> VEX_CHEST = registerLootTable(
            "entities/natural_spawn/vex_chestplate");
    public static final ResourceKey<LootTable> VEX_LEGGINGS = registerLootTable(
            "entities/natural_spawn/vex_legs");
    public static final ResourceKey<LootTable> VEX_FEET = registerLootTable(
            "entities/natural_spawn/vex_feet");

    public static ResourceKey<LootTable> registerLootTable(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(IllagersWearArmor.MODID, id));
    }
    
    public static LootContextParamSet register(String p_81429_, Consumer<LootContextParamSet.Builder> p_81430_) {
        LootContextParamSet.Builder lootcontextparamset$builder = new LootContextParamSet.Builder();
        p_81430_.accept(lootcontextparamset$builder);
        LootContextParamSet lootcontextparamset = lootcontextparamset$builder.build();
        ResourceLocation resourcelocation = ResourceLocation.parse(IllagersWearArmor.MODID + p_81429_);
        LootContextParamSet lootcontextparamset1 = REGISTRY.put(resourcelocation, lootcontextparamset);
        if (lootcontextparamset1 != null) {
            throw new IllegalStateException("Loot table parameter set " + resourcelocation + " is already registered");
        } else {
            return lootcontextparamset;
        }
    }
}
