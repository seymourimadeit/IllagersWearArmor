package tallestegg.illagersweararmor.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.nbt.CompoundTag;
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
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.IWAHelper;

@Mixin(AbstractIllager.class)
public abstract class AbstractIllagerMixin extends Raider {

    protected AbstractIllagerMixin(EntityType<? extends Raider> p_37839_, Level p_37840_) {
        super(p_37839_, p_37840_);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_,
            MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
        if (!IWAConfig.ArmorBlackList.contains(this.getStringUUID())) {
            this.giveArmorNaturally(p_34298_);
            this.giveArmorOnRaids();
        }
        return super.finalizeSpawn(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
    }

    public void giveArmorOnRaids() {
        float f = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
        int illagerWaves = this.getCurrentRaid().getGroupsSpawned();
        int armorChance = illagerWaves > 4 ? 4 : illagerWaves;
        float waveChances = IWAHelper.getWaveArmorChances(illagerWaves);
        if (this.random.nextFloat() < waveChances) {
            if (this.random.nextFloat() < 0.045F) {
                ++armorChance;
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
                        Item item = getEquipmentForSlot(equipmentslot, armorChance);
                        if (item != null) {
                            this.setItemSlot(equipmentslot, new ItemStack(item));
                        }
                    }
                }
            }
        }
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
