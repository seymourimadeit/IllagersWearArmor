package tallestegg.illagersweararmor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = IllagersWearArmor.MODID, bus = EventBusSubscriber.Bus.MOD)
public class IWAConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;
    static {
        {
            final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
            CLIENT = specPair.getLeft();
            CLIENT_SPEC = specPair.getRight();
        }
        {
            final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
            COMMON = specPair.getLeft();
            COMMON_SPEC = specPair.getRight();
        }
    }

    public static float Wave1Chances;
    public static float Wave2Chances;
    public static float Wave3Chances;
    public static float Wave4Chances;
    public static float Wave5Chances;
    public static float Wave6Chances;
    public static float Wave7Chances;
    public static float Wave8Chances;
    public static Boolean PillagerRenderArmor;
    public static Boolean EvokerRenderArmor;
    public static Boolean IllusionerRenderArmor;
    public static Boolean VindicatorRenderArmor;
    public static List<String> ArmorBlackList;

    public static void bakeCommonConfig() {
        Wave1Chances = COMMON.Wave1.get().floatValue();
        Wave2Chances = COMMON.Wave2.get().floatValue();
        Wave3Chances = COMMON.Wave3.get().floatValue();
        Wave4Chances = COMMON.Wave4.get().floatValue();
        Wave5Chances = COMMON.Wave5.get().floatValue();
        Wave6Chances = COMMON.Wave6.get().floatValue();
        Wave7Chances = COMMON.Wave7.get().floatValue();
        ArmorBlackList = COMMON.ArmorBlackList.get();
    }

    public static void bakeClientConfig() {
        PillagerRenderArmor = CLIENT.PillagerArmor.get();
        EvokerRenderArmor = CLIENT.EvokerArmor.get();
        IllusionerRenderArmor = CLIENT.IllusionerArmor.get();
        VindicatorRenderArmor = CLIENT.VindicatorArmor.get();
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == IWAConfig.COMMON_SPEC) {
            bakeCommonConfig();
        }
        if (configEvent.getConfig().getSpec() == IWAConfig.CLIENT_SPEC) {
            bakeClientConfig();
        }
    }

    public static class CommonConfig {

        public final ForgeConfigSpec.DoubleValue Wave1;
        public final ForgeConfigSpec.DoubleValue Wave2;
        public final ForgeConfigSpec.DoubleValue Wave3;
        public final ForgeConfigSpec.DoubleValue Wave4;
        public final ForgeConfigSpec.DoubleValue Wave5;
        public final ForgeConfigSpec.DoubleValue Wave6;
        public final ForgeConfigSpec.DoubleValue Wave7;
        public final ForgeConfigSpec.ConfigValue<List<String>> ArmorBlackList;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            Wave1 = builder.translation(IllagersWearArmor.MODID + ".config.wave1").defineInRange("Wave 1 Armor Chances", 0.30F, 0.0001F, 100F);
            Wave2 = builder.translation(IllagersWearArmor.MODID + ".config.wave2").defineInRange("Wave 2 Armor Chances", 0.32F, 0.0001F, 100F);
            Wave3 = builder.translation(IllagersWearArmor.MODID + ".config.wave3").defineInRange("Wave 3 Armor Chances", 0.34F, 0.0001F, 100F);
            Wave4 = builder.translation(IllagersWearArmor.MODID + ".config.wave4").defineInRange("Wave 4 Armor Chances", 0.36F, 0.0001F, 100F);
            Wave5 = builder.translation(IllagersWearArmor.MODID + ".config.wave5").defineInRange("Wave 5 Armor Chances", 0.38F, 0.0001F, 100F);
            Wave6 = builder.translation(IllagersWearArmor.MODID + ".config.wave6").defineInRange("Wave 6 Armor Chances", 0.40F, 0.0001F, 100F);
            Wave7 = builder.translation(IllagersWearArmor.MODID + ".config.wave7").defineInRange("Wave 7 Armor Chances", 0.42F, 0.0001F, 100F);
            ArmorBlackList = builder.translation(IllagersWearArmor.MODID + ".config.blacklist").comment("This will make sure any entity id in this list wont spawn with armor in raids.").define("Illager Armor BlackList", new ArrayList<>());

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class ClientConfig {
        public final ForgeConfigSpec.BooleanValue PillagerArmor;
        public final ForgeConfigSpec.BooleanValue EvokerArmor;
        public final ForgeConfigSpec.BooleanValue IllusionerArmor;
        public final ForgeConfigSpec.BooleanValue VindicatorArmor;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            PillagerArmor = builder.translation(IllagersWearArmor.MODID + ".config.pillagerarmor").define("Have Pillagers render armor?", true);
            EvokerArmor = builder.translation(IllagersWearArmor.MODID + ".config.evokerarmor").define("Have Evokers render armor?", true);
            IllusionerArmor = builder.translation(IllagersWearArmor.MODID + ".config.illusionarmor").define("Have Illusioners render armor?", true);
            VindicatorArmor = builder.translation(IllagersWearArmor.MODID + ".config.vindiarmor").define("Have Vindicators render armor?", true);
        }
    }
}