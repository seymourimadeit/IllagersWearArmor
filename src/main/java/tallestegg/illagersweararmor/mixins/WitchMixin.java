package tallestegg.illagersweararmor.mixins;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.IWAHelper;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Mixin(Witch.class)
public abstract class WitchMixin extends Raider {
    private static final Map<EquipmentSlot, ResourceLocation> EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, IWALootTables.ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, IWALootTables.ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, IWALootTables.ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, IWALootTables.ILLAGER_FEET);
            });
    private static final Map<EquipmentSlot, ResourceLocation> NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, IWALootTables.NATURAL_SPAWN_ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, IWALootTables.NATURAL_SPAWN_ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, IWALootTables.NATURAL_SPAWN_ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, IWALootTables.NATURAL_SPAWN_ILLAGER_FEET);
            });

    protected WitchMixin(EntityType<? extends Raider> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_, MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
        RandomSource randomSource = p_34297_.getRandom();
        if (!IWAConfig.ArmorBlackList.contains(this.getEncodeId()) && IWAConfig.IllagerArmor) {
            if (this.getCurrentRaid() != null && p_34299_ == MobSpawnType.EVENT) {
                this.giveArmorOnRaids(randomSource);
            } else {
                this.giveArmorNaturally(randomSource, p_34298_);
            }
        }
        return super.finalizeSpawn(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
    }

    public void giveArmorOnRaids(RandomSource pRandom) {
        float difficultyChance = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
        int illagerWaves = this.getCurrentRaid().getGroupsSpawned();
        float waveChances = IWAHelper.getWaveArmorChances(illagerWaves);
        if (pRandom.nextFloat() < waveChances) {
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (!flag && pRandom.nextFloat() < difficultyChance) {
                    break;
                }
                flag = false;
                for (ItemStack stack : this.getItemsFromLootTable(equipmentslottype)) {
                    this.setItemSlot(equipmentslottype, stack);
                }
            }
        }
    }

    public List<ItemStack> getItemsFromLootTable(EquipmentSlot slot) {
        if (EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = this.level.getServer().getLootTables().get(EQUIPMENT_SLOT_ITEMS.get(slot));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) this.level))
                    .withParameter(LootContextParams.THIS_ENTITY, this).withRandom(this.getRandom());
            return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
        }
        return null;
    }

    public List<ItemStack> getNaturalSpawnItemsFromLootTable(EquipmentSlot slot) {
        if (NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = this.level.getServer().getLootTables().get(NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) this.level))
                    .withParameter(LootContextParams.THIS_ENTITY, this).withRandom(this.getRandom());
            return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
        }
        return null;
    }

    protected void giveArmorNaturally(RandomSource random, DifficultyInstance instance) {
        if (random.nextFloat() < 0.15F * instance.getSpecialMultiplier()) {
            float difficultyChance = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && random.nextFloat() < difficultyChance) {
                        break;
                    }
                    flag = false;
                    for (ItemStack stack : this.getNaturalSpawnItemsFromLootTable(equipmentslottype)) {
                        this.setItemSlot(equipmentslottype, stack);
                    }
                }
            }
        }
    }
}
