package tallestegg.illagersweararmor.client.model.render_states;

import net.minecraft.client.renderer.entity.state.UndeadRenderState;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.phys.Vec3;

public class IllagerBipedRenderState extends UndeadRenderState {
    public AbstractIllager.IllagerArmPose armPose = AbstractIllager.IllagerArmPose.NEUTRAL;
    public boolean isWearingChestplateOrLegging;
    public int entityId;
    public boolean isAggressive;
    public boolean isCastingSpell = false;
    public Vec3[] illusionOffsets = new Vec3[0];
}
