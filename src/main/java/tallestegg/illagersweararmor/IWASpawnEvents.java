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
import net.minecraftforge.registries.ForgeRegistries;
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
        if (event.getEntity() instanceof Raider raider) {
            if (raider.getTags().contains("raidArmorSpawn")) {
                giveArmorOnRaids(raider, raider.getRandom());
                raider.getTags().remove("raidArmorSpawn");
            } else if (raider.getTags().contains("naturalArmorSpawn")) {
                giveArmorNaturally(raider);
                raider.getTags().remove("naturalArmorSpawn");
            }
        }
        if (event.getEntity() instanceof Vex vex && vex.getTags().contains("naturalArmorSpawn")) {
            giveArmorNaturally(vex);
            vex.getTags().remove("naturalArmorSpawn");
        }
    }

    public static void giveArmorOnRaids(Raider raider, RandomSource pRandom) {
        if (raider.getCurrentRaid() == null)
            return;
        if (pRandom.nextFloat() < IWAHelper.getWaveArmorChances(raider.getWave()))
            getItemsFromLootTable(raider);
    }

    public static List<ItemStack> getItemsFromLootTable(LivingEntity entity) {
        boolean raiding = entity instanceof Raider raider && raider.getCurrentRaid() != null;
        LootTable loot = entity.level().getServer().getLootData().getLootTable(getLootTable(entity, raiding));
        if (loot == LootTable.EMPTY) {
            String inRaid = raiding ? "_raid" : "";
            ResourceLocation lootTable = new ResourceLocation(IllagersWearArmor.MODID, "entities/illager" + inRaid);
            loot = entity.level().getServer().getLootData().getLootTable(lootTable);

        }
        LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) entity.level()).withParameter(LootContextParams.THIS_ENTITY, entity));
        return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
    }

    public static void giveArmorNaturally(LivingEntity raider) {
        getItemsFromLootTable(raider);
    }

    public static ResourceLocation getLootTable(LivingEntity entity, boolean raid) {
        String illagerName = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).getPath();
        String inRaid = raid ? "_raid" : "";
        ResourceLocation lootTable = new ResourceLocation(IllagersWearArmor.MODID, "entities/" + illagerName + inRaid);
        return lootTable;
    }
}
