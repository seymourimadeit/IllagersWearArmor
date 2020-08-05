package tallestegg.illagersweararmor.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@Mixin(AbstractIllagerEntity.class)
public class AbstractIllagerEntityMixin extends AbstractRaiderEntity
{
    protected AbstractIllagerEntityMixin(EntityType<? extends AbstractRaiderEntity> type, World worldIn) {
		super(type, worldIn);
		// TODO Auto-generated constructor stub
	}

	@Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) 
    {
		super.setEquipmentBasedOnDifficulty(difficultyIn);
		super.setEnchantmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
	
	@Override
	public void func_213660_a(int p_213660_1_, boolean p_213660_2_) 
	{
		
	}	
	
	@Override
    public SoundEvent getRaidLossSound() 
	{
		return null;
	}
}
