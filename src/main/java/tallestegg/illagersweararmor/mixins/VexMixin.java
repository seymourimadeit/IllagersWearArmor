package tallestegg.illagersweararmor.mixins;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tallestegg.illagersweararmor.IWAConfig;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;

import java.util.List;
import java.util.Map;

@Mixin(Vex.class)
public abstract class VexMixin extends Monster {
    private static final Map<EquipmentSlot, ResourceLocation> NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, IWALootTables.VEX_HELMET);
                slotItems.put(EquipmentSlot.CHEST, IWALootTables.VEX_CHEST);
                slotItems.put(EquipmentSlot.LEGS, IWALootTables.VEX_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, IWALootTables.VEX_FEET);
            });

    protected VexMixin(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(at = @At("HEAD"), method = "populateDefaultEquipmentSlots")
    public void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty, CallbackInfo ci) {
        if (!IWAConfig.ArmorBlackList.contains(this.getEncodeId()))
            this.giveArmorNaturally(pRandom, pDifficulty);
    }

    public List<ItemStack> getNaturalSpawnItemsFromLootTable(EquipmentSlot slot) {
        if (NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = this.level.getServer().getLootTables().get(NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) this.level))
                    .withParameter(LootContextParams.THIS_ENTITY, this).withRandom(this.getRandom());
            return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
        }
        return null;
    }

    protected void giveArmorNaturally(RandomSource random, DifficultyInstance instance) {
        if (random.nextFloat() < 0.15F * instance.getSpecialMultiplier()) {
            float difficultyChance = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && random.nextFloat() < difficultyChance) {
                        break;
                    }
                    flag = false;
                    for (ItemStack stack : this.getNaturalSpawnItemsFromLootTable(equipmentslottype)) {
                        this.setItemSlot(equipmentslottype, stack);
                    }
                }
            }
        }
    }
}
