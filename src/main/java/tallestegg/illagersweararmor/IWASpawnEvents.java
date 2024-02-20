package tallestegg.illagersweararmor;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = IllagersWearArmor.MODID)
public class IWASpawnEvents {
    private static final Map<EquipmentSlot, ResourceLocation> VEX_NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, IWALootTables.VEX_HELMET);
                slotItems.put(EquipmentSlot.CHEST, IWALootTables.VEX_CHEST);
                slotItems.put(EquipmentSlot.LEGS, IWALootTables.VEX_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, IWALootTables.VEX_FEET);
            });

    private static final Map<EquipmentSlot, ResourceLocation> EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, IWALootTables.ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, IWALootTables.ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, IWALootTables.ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, IWALootTables.ILLAGER_FEET);
            });
    private static final Map<EquipmentSlot, ResourceLocation> NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, IWALootTables.NATURAL_SPAWN_ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, IWALootTables.NATURAL_SPAWN_ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, IWALootTables.NATURAL_SPAWN_ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, IWALootTables.NATURAL_SPAWN_ILLAGER_FEET);
            });

    @SubscribeEvent
    public static void finalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        MobSpawnType spawnType = event.getSpawnType();
        LivingEntity entity = event.getEntity();
        if (!IWAConfig.ArmorBlackList.contains(entity.getEncodeId())) {
            if (entity instanceof Raider raider) {
                if (entity instanceof AbstractIllager || entity instanceof Witch) {
                    if (IWAConfig.IllagerArmor) {
                        if (raider.getCurrentRaid() != null && spawnType == MobSpawnType.EVENT) {
                            raider.getTags().add("raidArmorSpawn");
                        } else {
                            raider.getTags().add("naturalArmorSpawn");
                        }
                    }
                }
            }
            if (event.getEntity() instanceof Vex vex)
                vex.getTags().add("naturalArmorSpawn");
        }
    }

    @SubscribeEvent
    public static void tickEntity(LivingEvent.LivingTickEvent event) {
        RandomSource rSource = event.getEntity().level().getRandom();
        if (event.getEntity() instanceof Raider raider) {
            if (raider.getTags().contains("raidArmorSpawn")) {
                giveArmorOnRaids(raider, raider.getRandom());
                raider.getTags().remove("raidArmorSpawn");
            } else if (raider.getTags().contains("naturalArmorSpawn")) {
                giveArmorNaturally(raider, rSource);
                raider.getTags().remove("naturalArmorSpawn");
            }
        }
        if (event.getEntity() instanceof Vex vex && vex.getTags().contains("naturalArmorSpawn")) {
            giveArmorNaturally(vex, rSource, vex.level().getCurrentDifficultyAt(vex.blockPosition()));
            vex.getTags().remove("naturalArmorSpawn");
        }
    }

    public static void giveArmorOnRaids(Raider raider, RandomSource pRandom) {
        float difficultyChance = raider.level().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
        int illagerWaves = raider.getCurrentRaid().getGroupsSpawned();
        float waveChances = IWAHelper.getWaveArmorChances(illagerWaves);
        if (pRandom.nextFloat() < waveChances) {
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (!flag && pRandom.nextFloat() < difficultyChance) {
                    break;
                }
                flag = false;
                if (getItemsFromLootTable(raider, equipmentslottype) != null) {
                    for (ItemStack stack : getItemsFromLootTable(raider, equipmentslottype)) {
                        raider.setItemSlot(equipmentslottype, stack);
                    }
                }
            }
        }
    }

    public static List<ItemStack> getItemsFromLootTable(Raider raider, EquipmentSlot slot) {
        if (EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = raider.level().getServer().getLootData().getLootTable(EQUIPMENT_SLOT_ITEMS.get(slot));
            LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) raider.level()).withParameter(LootContextParams.THIS_ENTITY, raider));
            return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
        }
        return null;
    }

    public static List<ItemStack> getNaturalSpawnItemsFromLootTable(Raider raider, EquipmentSlot slot) {
        if (NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = raider.level().getServer().getLootData().getLootTable(NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot));
            LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) raider.level()).withParameter(LootContextParams.THIS_ENTITY, raider));
            return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
        }
        return null;
    }

    public static List<ItemStack> getVexNaturalSpawnItemsFromLootTable(Vex vex, EquipmentSlot slot) {
        if (NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = vex.level().getServer().getLootData().getLootTable(VEX_NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot));
            LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) vex.level()).withParameter(LootContextParams.THIS_ENTITY, vex));
            return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
        }
        return null;
    }

    public static void giveArmorNaturally(Raider raider, RandomSource random) {
            float difficultyChance = raider.level().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && random.nextFloat() < difficultyChance) {
                        break;
                    }
                    flag = false;
                    for (ItemStack stack : getNaturalSpawnItemsFromLootTable(raider, equipmentslottype)) {
                        raider.setItemSlot(equipmentslottype, stack);
                    }
                }
            }
    }

    public static void giveArmorNaturally(Vex vex, RandomSource random, DifficultyInstance instance) {
            float difficultyChance = vex.level().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && random.nextFloat() < difficultyChance) {
                        break;
                    }
                    flag = false;
                    for (ItemStack stack : getVexNaturalSpawnItemsFromLootTable(vex, equipmentslottype)) {
                        vex.setItemSlot(equipmentslottype, stack);
                    }
                }
        }
    }
}
