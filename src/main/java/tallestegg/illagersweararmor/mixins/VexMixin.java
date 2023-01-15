package tallestegg.illagersweararmor.mixins;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tallestegg.illagersweararmor.IWAConfig;

@Mixin(Vex.class)
public abstract class VexMixin extends Monster {
    protected VexMixin(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(at = @At("HEAD"), method = "populateDefaultEquipmentSlots")
    public void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty, CallbackInfo ci) {
        if (!IWAConfig.ArmorBlackList.contains(this.getStringUUID()))
            super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
    }
}
