package tallestegg.illagersweararmor.loot_tables;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record RaidWaveCondition(int wave) implements LootItemCondition {
    public static final MapCodec<RaidWaveCondition> CODEC = RecordCodecBuilder.mapCodec((p_297204_) -> {
        return p_297204_.group(Codec.INT.fieldOf("wave").forGetter(raidWaveCondition -> raidWaveCondition.wave())).apply(p_297204_, RaidWaveCondition::new);
    });

    @Override
    public boolean test(LootContext context) {
        Raider raider = (Raider) context.getParameter(LootContextParams.THIS_ENTITY);
        return raider.getCurrentRaid().getGroupsSpawned() == wave;
    }

    public static LootItemCondition.Builder wave(int wave) {
        return () -> new RaidWaveCondition(wave);
    }

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }
}