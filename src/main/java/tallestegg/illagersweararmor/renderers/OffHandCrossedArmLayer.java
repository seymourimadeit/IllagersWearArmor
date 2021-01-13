package tallestegg.illagersweararmor.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;

import baguchan.hunterillager.entity.HunterIllagerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import tallestegg.illagersweararmor.models.HunterBipedModel;

public class OffHandCrossedArmLayer<T extends HunterIllagerEntity, M extends HunterBipedModel<T> & IHasArm> extends LayerRenderer<T, M> {

    public OffHandCrossedArmLayer(IEntityRenderer<T, M> p_i50916_1_) {
        super(p_i50916_1_);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isAggressive()) {
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, (double) 0.4F, (double) -0.4F);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180.0F));
            ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.pop();
        }
    }
}
