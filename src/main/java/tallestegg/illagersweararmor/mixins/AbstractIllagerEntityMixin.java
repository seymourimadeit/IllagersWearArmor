package tallestegg.illagersweararmor.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tallestegg.illagersweararmor.IWAExtraStuff;

@Mixin(AbstractIllagerEntity.class)
public class AbstractIllagerEntityMixin extends AbstractRaiderEntity {
    protected AbstractIllagerEntityMixin(EntityType<? extends AbstractRaiderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    // this is a temp way to get illagers to spawn with armor until pr = merged
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (reason == SpawnReason.EVENT) {
            this.giveArmorOnRaids();
        } else {
            super.setEquipmentBasedOnDifficulty(difficultyIn);
        }
        super.setEnchantmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void giveArmorOnRaids() {
        this.addTag("iwasspawnedwitharmorduetoamod");
        float f = this.world.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
        int illagerWaves = this.getRaid().getGroupsSpawned();
        int armorChance = illagerWaves > 4 ? 4 : illagerWaves;
        float waveChances = IWAExtraStuff.getWaveArmorChances(illagerWaves);
        if (this.rand.nextFloat() < waveChances) {
            if (this.rand.nextFloat() < 0.045F) {
                ++armorChance;
            }
            
            boolean flag = true;

            for (EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
                if (equipmentslottype.getSlotType() == EquipmentSlotType.Group.ARMOR) {
                    ItemStack itemstack = this.getItemStackFromSlot(equipmentslottype);
                    if (!flag && this.rand.nextFloat() < f) {
                        break;
                    }

                    flag = false;
                    if (itemstack.isEmpty()) {
                        Item item = getArmorByChance(equipmentslottype, armorChance);
                        if (item != null) {
                            this.setItemStackToSlot(equipmentslottype, new ItemStack(item));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {

    }

    @Override
    public SoundEvent getRaidLossSound() {
        return null;
    }
}
