package tallestegg.illagersweararmor.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.item.ShootableItem;

public class MeleeAttackIfNotHoldingShootableGoal extends MeleeAttackGoal 
{

	public MeleeAttackIfNotHoldingShootableGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
		super(creature, speedIn, useLongMemory);
	}
	
	@Override
	public boolean shouldExecute() {
		return !(this.attacker.getHeldItemMainhand().getItem() instanceof ShootableItem) && super.shouldExecute();
	}

}
