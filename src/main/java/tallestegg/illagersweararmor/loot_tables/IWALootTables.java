package tallestegg.illagersweararmor.loot_tables;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tallestegg.illagersweararmor.IllagersWearArmor;

import java.util.function.Consumer;

@EventBusSubscriber(modid = IllagersWearArmor.MODID, bus = Bus.MOD)
public class IWALootTables {
    public static final BiMap<ResourceLocation, LootContextParamSet> REGISTRY = HashBiMap.create();
    public static final DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, IllagersWearArmor.MODID);
    public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, IllagersWearArmor.MODID);
    public static final LootContextParamSet SLOT = register("slot", (table) -> {
        table.required(LootContextParams.THIS_ENTITY);
    });

    public static final RegistryObject<LootItemConditionType> WAVE = LOOT_ITEM_CONDITION_TYPES.register("wave", () -> new LootItemConditionType(new RaidWaveCondition.Serializer()));
    public static final RegistryObject<LootItemFunctionType> ARMOR_SLOT = LOOT_ITEM_FUNCTION_TYPES.register("slot", () ->  new LootItemFunctionType(new ArmorSlotFunction.Serializer()));

    public static final ResourceLocation ILLAGER_HELMET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_helmet");
    public static final ResourceLocation ILLAGER_CHEST = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_chestplate");
    public static final ResourceLocation ILLAGER_LEGGINGS = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_legs");
    public static final ResourceLocation ILLAGER_FEET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_feet");
    public static final ResourceLocation NATURAL_SPAWN_ILLAGER_HELMET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/illager_helmet");
    public static final ResourceLocation NATURAL_SPAWN_ILLAGER_CHEST = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/illager_chestplate");
    public static final ResourceLocation NATURAL_SPAWN_ILLAGER_LEGGINGS = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/illager_legs");
    public static final ResourceLocation NATURAL_SPAWN_ILLAGER_FEET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/illager_feet");
    public static final ResourceLocation VEX_HELMET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/vex_helmet");
    public static final ResourceLocation VEX_CHEST = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/vex_chestplate");
    public static final ResourceLocation VEX_LEGGINGS = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/vex_legs");
    public static final ResourceLocation VEX_FEET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/natural_spawn/vex_feet");
    
    public static LootContextParamSet register(String p_81429_, Consumer<LootContextParamSet.Builder> p_81430_) {
        LootContextParamSet.Builder lootcontextparamset$builder = new LootContextParamSet.Builder();
        p_81430_.accept(lootcontextparamset$builder);
        LootContextParamSet lootcontextparamset = lootcontextparamset$builder.build();
        ResourceLocation resourcelocation = new ResourceLocation(IllagersWearArmor.MODID + p_81429_);
        LootContextParamSet lootcontextparamset1 = REGISTRY.put(resourcelocation, lootcontextparamset);
        if (lootcontextparamset1 != null) {
           throw new IllegalStateException("Loot table parameter set " + resourcelocation + " is already registered");
        } else {
           return lootcontextparamset;
        }
     }
}
