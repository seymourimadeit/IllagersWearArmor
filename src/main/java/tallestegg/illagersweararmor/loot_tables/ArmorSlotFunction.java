package tallestegg.illagersweararmor.loot_tables;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.FillPlayerHead;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

import static tallestegg.illagersweararmor.loot_tables.IWALootTables.ARMOR_SLOT;

public class ArmorSlotFunction extends LootItemConditionalFunction {
    final EquipmentSlot slot;
    public static final MapCodec<ArmorSlotFunction> CODEC = RecordCodecBuilder.mapCodec(
            p_298087_ -> commonFields(p_298087_)
                    .and(EquipmentSlot.CODEC.fieldOf("slot").forGetter(p_298086_ -> p_298086_.slot))
                    .apply(p_298087_, ArmorSlotFunction::new)
    );
    ArmorSlotFunction(List<LootItemCondition> pConditions, EquipmentSlot slot) {
        super(pConditions);
        this.slot = slot;
    }

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        LivingEntity livingEntity = (LivingEntity) pContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (!livingEntity.hasItemInSlot(slot))
            livingEntity.setItemSlot(slot, pStack);
        return pStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return ARMOR_SLOT.get();
    }

    public static LootItemConditionalFunction.Builder<?> armorSlotFunction(EquipmentSlot slot) {
        return simpleBuilder(conditions -> new ArmorSlotFunction(conditions, slot));
    }
}
