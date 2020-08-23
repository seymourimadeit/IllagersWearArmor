package tallestegg.illagersweararmor;

import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tallestegg.illagersweararmor.goals.MeleeAttackIfNotHoldingShootableGoal;

public class IWAEvents {
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PillagerEntity) {
            PillagerEntity pillager = (PillagerEntity) event.getEntity();
            pillager.goalSelector.addGoal(3, new RangedBowAttackGoal<>(pillager, 0.5D, 20, 15.0F));
            pillager.goalSelector.addGoal(3, new MeleeAttackIfNotHoldingShootableGoal(pillager, 1.0D, false));
        }

        if (event.getEntity() instanceof IllusionerEntity) {
            IllusionerEntity illusion = (IllusionerEntity) event.getEntity();
            illusion.goalSelector.addGoal(3, new MeleeAttackIfNotHoldingShootableGoal(illusion, 1.0D, false));
        }
        
        if (IWAConfig.ArmorBlackList.contains(event.getEntity().getEntityString()) && event.getEntity().getTags().contains("iwasspawnedwitharmorduetoamod") && !event.getEntity().getTags().contains("armorhasbeenremoveduetoconfig")) {
            AbstractIllagerEntity illager = (AbstractIllagerEntity)event.getEntity();
            illager.removeTag("iwasspawnedwitharmorduetoamod");
            illager.addTag("armorhasbeenremoveduetoconfig");
            illager.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.AIR));
            illager.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Items.AIR));
            illager.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Items.AIR));
            illager.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.AIR));
        }
    }
}
