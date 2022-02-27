package tallestegg.illagersweararmor.loot_tables;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tallestegg.illagersweararmor.IllagersWearArmor;

@EventBusSubscriber(modid = IllagersWearArmor.MODID, bus = Bus.MOD)
public class IWALootTables {
    public static final LootContextParamSet SLOT = LootContextParamSets.register("slot", (table) -> {
        table.required(LootContextParams.THIS_ENTITY);
    });

    public static final ResourceLocation ILLAGER_HELMET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_helmet");
    public static final ResourceLocation ILLAGER_CHEST = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_chestplate");
    public static final ResourceLocation ILLAGER_LEGGINGS = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_legs");
    public static final ResourceLocation ILLAGER_FEET = new ResourceLocation(IllagersWearArmor.MODID,
            "entities/illager_feet");
}
