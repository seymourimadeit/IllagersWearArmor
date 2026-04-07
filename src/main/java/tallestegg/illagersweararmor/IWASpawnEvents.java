package tallestegg.illagersweararmor;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import tallestegg.illagersweararmor.loot_tables.IWALootTables;

import java.util.List;

public class IWASpawnEvents {
    @SubscribeEvent
    public static void finalizeSpawn(FinalizeSpawnEvent event) {
        EntitySpawnReason spawnType = event.getSpawnType();
        LivingEntity entity = event.getEntity();
        if (!IWAConfig.ArmorBlackList.contains(entity.getEncodeId())) {
            if (entity instanceof Raider raider) {
                if (entity instanceof AbstractIllager || entity instanceof Witch) {
                    if (IWAConfig.IllagerArmor) {
                        if (raider.getCurrentRaid() != null && spawnType == EntitySpawnReason.EVENT) {
                            raider.entityTags().add("raidArmorSpawn");
                        } else {
                            raider.entityTags().add("naturalArmorSpawn");
                        }
                    }
                }
            }
        }
    }

    // To prevent weird RNG crashes with threading
    @SubscribeEvent
    public static void tickEntity(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof Raider raider) {
            if (raider.entityTags().contains("raidArmorSpawn")) {
                giveArmorOnRaids(raider, raider.getRandom());
                raider.entityTags().remove("raidArmorSpawn");
            } else if (raider.entityTags().contains("naturalArmorSpawn")) {
                getItemsFromLootTable(raider);
                raider.entityTags().remove("naturalArmorSpawn");
            }
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
        LootTable loot = entity.level().getServer().reloadableRegistries().getLootTable(getLootTable(entity, raiding));
        if (loot == LootTable.EMPTY) {
            String inRaid = raiding ? "_raid" : "";
            ResourceKey<LootTable> lootTable = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/illager" + inRaid));
            loot = entity.level().getServer().reloadableRegistries().getLootTable(lootTable);

        }
        LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) entity.level()).withParameter(LootContextParams.THIS_ENTITY, entity));
        return loot.getRandomItems(lootcontext$builder.create(IWALootTables.SLOT));
    }

    public static ResourceKey<LootTable> getLootTable(LivingEntity entity, boolean raid) {
        String illagerName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
        String inRaid = raid ? "_raid" : "";
        Identifier lootTable = Identifier.fromNamespaceAndPath(IllagersWearArmor.MODID, "entities/" + illagerName + inRaid);
        return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
    }
}
