package tallestegg.illagersweararmor.client.renderer.mod_compat;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Archer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import tallestegg.illagersweararmor.client.renderer.IllagerBipedRenderer;

public class ArcherBipedRenderer extends IllagerBipedRenderer<Archer> {
    private static final ResourceLocation ARCHER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/archer.png");

    public ArcherBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()));
    }

    @Override
    protected HumanoidModel.ArmPose getArmPose(Archer entityIn, ItemStack itemStackMain, ItemStack itemStackOff, InteractionHand handIn) {
        HumanoidModel.ArmPose bipedmodel$armpose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemstack = handIn == InteractionHand.MAIN_HAND ? itemStackMain : itemStackOff;
        if (!itemstack.isEmpty()) {
            bipedmodel$armpose = HumanoidModel.ArmPose.ITEM;
            UseAnim useaction = itemstack.getUseAnimation();
            switch (useaction) {
                case BLOCK:
                    bipedmodel$armpose = HumanoidModel.ArmPose.BLOCK;
                    break;
                case BOW:
                    if (entityIn.getUseItemRemainingTicks() > 0)
                        bipedmodel$armpose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                    break;
                case SPEAR:
                    bipedmodel$armpose = HumanoidModel.ArmPose.THROW_SPEAR;
                    break;
                case CROSSBOW:
                    if (handIn == entityIn.getUsedItemHand() && entityIn.getUseItemRemainingTicks() > 0)
                        bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                    break;
                case SPYGLASS:
                    if (entityIn.getUseItemRemainingTicks() > 0)
                        bipedmodel$armpose = HumanoidModel.ArmPose.SPYGLASS;
                    break;
                default:
                    bipedmodel$armpose = HumanoidModel.ArmPose.EMPTY;
                    break;
            }
        }
        if (!entityIn.swinging && itemstack.getItem() instanceof CrossbowItem && !entityIn.isUsingItem()) {
            bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
        return bipedmodel$armpose;
    }

    @Override
    public ResourceLocation getTextureLocation(Archer archer) {
        return ARCHER;
    }
}
