package tallestegg.illagersweararmor;

import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.PillagerEntity;
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
    }
}
