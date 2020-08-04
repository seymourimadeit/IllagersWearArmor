package tallestegg.illagersweararmor;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tallestegg.illagersweararmor.goals.MeleeAttackIfNotHoldingShootableGoal;

public class IWAEvents 
{
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) 
	{
		if (event.getEntity() instanceof PillagerEntity) 
		{
			PillagerEntity pillager = (PillagerEntity)event.getEntity();
			pillager.goalSelector.addGoal(3, new RangedBowAttackGoal<>(pillager, 0.5D, 20, 15.0F));
			pillager.goalSelector.addGoal(3, new MeleeAttackIfNotHoldingShootableGoal(pillager, 1.0D, false));
		}
		
		if (event.getEntity() instanceof IllusionerEntity) 
		{
			IllusionerEntity illusion = (IllusionerEntity)event.getEntity();
			illusion.goalSelector.addGoal(3, new MeleeAttackIfNotHoldingShootableGoal(illusion, 1.0D, false));
		}
	}
	
	//doesnt work
	/*
	@SubscribeEvent 
	public static void onEntitySpawn(EntityEvent.EntityConstructing event)
	{
		if (event.getEntity() instanceof AbstractIllagerEntity) 
		{
			AbstractIllagerEntity illager = (AbstractIllagerEntity)event.getEntity();
			giveIllagerArmor(illager.world.getDifficultyForLocation(illager.func_233580_cy_()), illager);
		}
	}*/
	
	public static void giveIllagerArmor(DifficultyInstance difficulty, MobEntity mob) 
	{
	  if (mob.getRNG().nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty()) 
	  {
	     int i = mob.getRNG().nextInt(2);
	     float f = mob.world.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
	     if (mob.getRNG().nextFloat() < 0.095F) {
	            ++i;
	     }

	     if (mob.getRNG().nextFloat() < 0.095F) {
	            ++i;
	     }

	     if (mob.getRNG().nextFloat() < 0.095F) {
	            ++i;
	     }

	     boolean flag = true;

	     for(EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
	       if (equipmentslottype.getSlotType() == EquipmentSlotType.Group.ARMOR) {
	           ItemStack itemstack = mob.getItemStackFromSlot(equipmentslottype);
	           if (!flag && mob.getRNG().nextFloat() < f) {
	                  break;
	           }

	           flag = false;
	           if (itemstack.isEmpty()) {
	            Item item = MobEntity.getArmorByChance(equipmentslottype, i);
	            if (item != null) 
	            {
	             mob.setItemStackToSlot(equipmentslottype, new ItemStack(item));
	            }
	           }
	          }
	         }
	    }
	  }
}
