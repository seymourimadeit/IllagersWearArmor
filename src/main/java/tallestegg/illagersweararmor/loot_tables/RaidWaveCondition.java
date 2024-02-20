package tallestegg.illagersweararmor.loot_tables;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record RaidWaveCondition(int wave) implements LootItemCondition {
    public static final Codec<RaidWaveCondition> CODEC = RecordCodecBuilder.create((p_297204_) -> {
        return p_297204_.group(Codec.INT.fieldOf("wave").forGetter(RaidWaveCondition::wave)).apply(p_297204_, RaidWaveCondition::new);
    });

    public static final LootItemConditionType TYPE = new LootItemConditionType(CODEC);

    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    @Override
    public boolean test(LootContext context) {
        Raider raider = (Raider) context.getParamOrNull(LootContextParams.THIS_ENTITY);
        return raider.getCurrentRaid().getGroupsSpawned() == wave;
    }

    public static LootItemCondition.Builder wave(int wave) {
        return () -> {
            return new RaidWaveCondition(wave);
        };
    }
}