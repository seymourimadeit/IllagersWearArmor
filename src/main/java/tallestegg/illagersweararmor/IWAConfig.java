package tallestegg.illagersweararmor;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = IllagersWearArmor.MODID, bus = EventBusSubscriber.Bus.MOD)
public class IWAConfig 
{
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final CommonConfig COMMON;
	static {
		final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static float Wave1Chances;
	public static float Wave2Chances;
	public static float Wave3Chances;
	public static float Wave4Chances;
	public static float Wave5Chances;
	public static float Wave6Chances;
	public static float Wave7Chances;
	public static float Wave8Chances;
	public static float IllusionerArmor;
	public static float PillagerArmor;
	public static float VindicatorArmor;
	public static float EvokerArmor;
	
	public static void bakeConfig() {
		Wave1Chances = COMMON.Wave1.get().floatValue();
		Wave2Chances = COMMON.Wave2.get().floatValue();
		Wave3Chances = COMMON.Wave3.get().floatValue();
		Wave4Chances = COMMON.Wave4.get().floatValue();
		Wave5Chances = COMMON.Wave5.get().floatValue();
		Wave6Chances = COMMON.Wave6.get().floatValue();
		Wave7Chances = COMMON.Wave7.get().floatValue();
	}

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
		if (configEvent.getConfig().getSpec() == IWAConfig.COMMON_SPEC) {
			bakeConfig();
		}
	}
	
	public static class CommonConfig 
	{

		public final ForgeConfigSpec.DoubleValue Wave1;
		public final ForgeConfigSpec.DoubleValue Wave2;
		public final ForgeConfigSpec.DoubleValue Wave3;
		public final ForgeConfigSpec.DoubleValue Wave4;
		public final ForgeConfigSpec.DoubleValue Wave5;
		public final ForgeConfigSpec.DoubleValue Wave6;
		public final ForgeConfigSpec.DoubleValue Wave7;
		
		public CommonConfig(ForgeConfigSpec.Builder builder) 
		{	
			Wave1 = builder
					.translation(IllagersWearArmor.MODID + ".config.wave1")
					.defineInRange("Wave 1 Armor Chances", 0.02F, 0.0001F, 100F);
			Wave2 = builder
					.translation(IllagersWearArmor.MODID + ".config.wave2")
					.defineInRange("Wave 2 Armor Chances", 0.06F, 0.0001F, 100F);
			Wave3 = builder
					.translation(IllagersWearArmor.MODID + ".config.wave3")
					.defineInRange("Wave 3 Armor Chances", 0.12F, 0.0001F, 100F);
			Wave4 = builder
					.translation(IllagersWearArmor.MODID + ".config.wave4")
					.defineInRange("Wave 4 Armor Chances", 0.23F, 0.0001F, 100F);
			Wave5 = builder
					.translation(IllagersWearArmor.MODID + ".config.wave5")
					.defineInRange("Wave 5 Armor Chances", 0.32F, 0.0001F, 100F);
			Wave6 = builder
					.translation(IllagersWearArmor.MODID + ".config.wave6")
					.defineInRange("Wave 6 Armor Chances", 0.45F, 0.0001F, 100F);
			Wave7 = builder
					.translation(IllagersWearArmor.MODID + ".config.wave7")
					.defineInRange("Wave 7 Armor Chances", 0.67F, 0.0001F, 100F);
			
	    }
	}
}