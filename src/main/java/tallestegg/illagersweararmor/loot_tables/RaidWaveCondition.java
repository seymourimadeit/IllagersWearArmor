package tallestegg.illagersweararmor.loot_tables;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class RaidWaveCondition implements LootItemCondition {
    final int wave;

    RaidWaveCondition(int wave) {
        this.wave = wave;
    }

    @Override
    public LootItemConditionType getType() {
        return IWALootTables.WAVE.get();
    }

    @Override
    public boolean test(LootContext context) {
        Raider raider = (Raider) context.getParamOrNull(LootContextParams.THIS_ENTITY);
        return raider.getCurrentRaid().getGroupsSpawned() == wave;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<RaidWaveCondition> {
        @Override
        public void serialize(JsonObject json, RaidWaveCondition condition, JsonSerializationContext serializer) {
            json.addProperty("wave", condition.wave);
        }

        @Override
        public RaidWaveCondition deserialize(JsonObject p_81991_, JsonDeserializationContext p_81992_) {
            return new RaidWaveCondition(GsonHelper.getAsInt(p_81991_, "wave"));
        }
    }
}