package tallestegg.illagersweararmor.loot_tables;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tallestegg.illagersweararmor.IllagersWearArmor;


public class IWALootTables {
    public static final DeferredRegister<MapCodec<? extends LootItemFunction>> LOOT_ITEM_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, IllagersWearArmor.MODID);
    public static final DeferredRegister<ContextKeySet> CONTEXT_KEY_SET = DeferredRegister.create(Registries.CONTEXT_KEY_SET, IllagersWearArmor.MODID);
    public static final DeferredRegister<MapCodec<? extends LootItemCondition>> LOOT_ITEM_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, IllagersWearArmor.MODID);
    public static final DeferredHolder<MapCodec<? extends LootItemCondition>, MapCodec<RaidWaveCondition>> WAVE = LOOT_ITEM_CONDITION_TYPES.register("wave", () -> RaidWaveCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<ArmorSlotFunction>> ARMOR_SLOT = LOOT_ITEM_FUNCTION_TYPES.register("slot", () -> ArmorSlotFunction.CODEC);
    public static final DeferredHolder<ContextKeySet, ContextKeySet> SLOT = CONTEXT_KEY_SET.register("slot", () -> new ContextKeySet.Builder().required(LootContextParams.THIS_ENTITY).build());
}
