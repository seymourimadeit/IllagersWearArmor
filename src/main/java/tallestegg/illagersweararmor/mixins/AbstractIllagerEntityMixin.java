AbstractIllagerEntityMixin
 
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.IWAExtraStuff;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
 
@Mixin(AbstractIllagerEntity.class)
public abstract class AbstractIllagerEntityMixin extends AbstractRaiderEntity {
    protected AbstractIllagerEntityMixin(EntityType<? extends AbstractRaiderEntity> type, World worldIn) {
        super(type, worldIn);
    }
 
    // this is a temp way to get illagers to spawn with armor until pr = merged
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (IWAConfig.IllagerArmor && !IWAConfig.ArmorBlackList.contains(this.getEntityString())) {
            if (reason == SpawnReason.EVENT && this.getRaid() != null) {
                this.giveArmorOnRaids();
            } else {
                this.giveArmorNaturally(difficultyIn);
            }
            super.setEnchantmentBasedOnDifficulty(difficultyIn);
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
 
    public void giveArmorOnRaids() {
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
 
    protected void giveArmorNaturally(DifficultyInstance difficulty) {
        // Determine the maximum number of armor pieces to equip (2–3).
        int maxSlots = 2 + this.rand.nextInt(2); // Equip 2-3 armor pieces
        int equippedSlots = 0;
 
        // Get the list of available armor slots and shuffle them for randomness.
        List<EquipmentSlotType> slots = Arrays.asList(
                EquipmentSlotType.HEAD, EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS, EquipmentSlotType.FEET
        );
        Collections.shuffle(slots, this.rand); // Randomize the order of slots
 
        // Loop through all armor slots
        for (EquipmentSlotType slot : slots) {
            if (equippedSlots >= maxSlots) {
                break; // Stop processing if max number of armor slots has been equipped
            }
 
            // Check if the slot is empty before attempting to equip armor
            ItemStack currentItem = this.getItemStackFromSlot(slot);
            if (currentItem.isEmpty() && this.rand.nextFloat() <= 0.15F) { // % chance to equip this slot
                // Determine armor tier based on difficulty and randomness
                int armorTier = this.rand.nextInt(2); // Base tier: 0 or 1
                if (this.world.getDifficulty() == Difficulty.HARD) {
                    if (this.rand.nextFloat() < 0.3F) armorTier++; // % chance to increase tier
                    if (this.rand.nextFloat() < 0.2F) armorTier++; // % chance for another bump
                }
 
                // Get an armor item for the specified slot and tier
                Item item = getArmorByChance(slot, armorTier);
                if (item != null) {
                    this.setItemStackToSlot(slot, new ItemStack(item)); // Equip the armor
                    equippedSlots++; // Increment the equipped slots count
                }
            }
        }
    }
}
