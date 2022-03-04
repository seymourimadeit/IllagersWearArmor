package tallestegg.illagersweararmor.loot_tables;

import java.util.function.Consumer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tallestegg.illagersweararmor.IllagersWearArmor;

@EventBusSubscriber(modid = IllagersWearArmor.MODID, bus = Bus.MOD)
public class IWALootTables {
    public static final BiMap<ResourceLocation, LootContextParamSet> REGISTRY = HashBiMap.create();
    public static final LootContextParamSet SLOT = register("slot", (table) -> {
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
    
    public static LootContextParamSet register(String p_81429_, Consumer<LootContextParamSet.Builder> p_81430_) {
        LootContextParamSet.Builder lootcontextparamset$builder = new LootContextParamSet.Builder();
        p_81430_.accept(lootcontextparamset$builder);
        LootContextParamSet lootcontextparamset = lootcontextparamset$builder.build();
        ResourceLocation resourcelocation = new ResourceLocation(IllagersWearArmor.MODID + p_81429_);
        LootContextParamSet lootcontextparamset1 = REGISTRY.put(resourcelocation, lootcontextparamset);
        if (lootcontextparamset1 != null) {
           throw new IllegalStateException("Loot table parameter set " + resourcelocation + " is already registered");
        } else {
           return lootcontextparamset;
        }
     }
}
