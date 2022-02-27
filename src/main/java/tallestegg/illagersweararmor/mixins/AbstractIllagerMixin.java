package tallestegg.illagersweararmor.mixins;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;

import com.google.common.collect.Maps;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.IWAHelper;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;

@Mixin(AbstractIllager.class)
public abstract class AbstractIllagerMixin extends Raider {
    private static final Map<EquipmentSlot, ResourceLocation> EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, IWALootTables.ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, IWALootTables.ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, IWALootTables.ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, IWALootTables.ILLAGER_FEET);
            });

    protected AbstractIllagerMixin(EntityType<? extends Raider> p_37839_, Level p_37840_) {
        super(p_37839_, p_37840_);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_,
            MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
        if (!IWAConfig.ArmorBlackList.contains(this.getStringUUID()) && IWAConfig.IllagerArmor) {
            if (this.getCurrentRaid() != null && p_34299_ == MobSpawnType.EVENT) {
                this.giveArmorOnRaids();
            } else {
                this.giveArmorNaturally(p_34298_);
            }
        }
        return super.finalizeSpawn(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
    }

    public void giveArmorOnRaids() {
        float difficultyChance = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
        int illagerWaves = this.getCurrentRaid().getGroupsSpawned();
        float waveChances = IWAHelper.getWaveArmorChances(illagerWaves);
        if (this.getRandom().nextFloat() < waveChances) {
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && this.random.nextFloat() < difficultyChance) {
                        break;
                    }
                    flag = false;
                    for (ItemStack stack : this.getItemsFromLootTable(equipmentslottype)) {
                        this.setItemSlot(equipmentslottype, stack);
                    }
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

    protected void giveArmorNaturally(DifficultyInstance p_21383_) {
        if (this.random.nextFloat() < 0.15F * p_21383_.getSpecialMultiplier()) {
            int i = this.random.nextInt(2);
            float f = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            if (this.random.nextFloat() < 0.095F) {
                ++i;
            }

            if (this.random.nextFloat() < 0.095F) {
                ++i;
            }

            if (this.random.nextFloat() < 0.095F) {
                ++i;
            }

            boolean flag = true;

            for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack itemstack = this.getItemBySlot(equipmentslot);
                    if (!flag && this.random.nextFloat() < f) {
                        break;
                    }

                    flag = false;
                    if (itemstack.isEmpty()) {
                        Item item = getEquipmentForSlot(equipmentslot, i);
                        if (item != null) {
                            this.setItemSlot(equipmentslot, new ItemStack(item));
                        }
                    }
                }
            }
        }

    }
}
