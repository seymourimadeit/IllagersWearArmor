package tallestegg.illagersweararmor.client.model.render_states;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;

public class WitchBipedRenderState extends IllagerBipedRenderState {
    public boolean isHoldingItem;
    public boolean isHoldingPotion;
    public final ItemStackRenderState heldItem = new ItemStackRenderState();

    public static void extractHoldingEntityRenderState(LivingEntity entity, WitchBipedRenderState state, ItemModelResolver itemModelResolver) {
        itemModelResolver.updateForLiving(state.heldItem, entity.getMainHandItem(), ItemDisplayContext.GROUND, entity);
    }
}
