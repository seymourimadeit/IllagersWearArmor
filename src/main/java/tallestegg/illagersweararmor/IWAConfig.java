package tallestegg.illagersweararmor;

import com.google.common.collect.Lists;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@EventBusSubscriber(modid = IllagersWearArmor.MODID, bus = EventBusSubscriber.Bus.MOD)
public class IWAConfig {
    public static final ModConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    public static final ModConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;
    public static float Wave1Chances;
    public static float Wave2Chances;
    public static float Wave3Chances;
    public static float Wave4Chances;
    public static float Wave5Chances;
    public static float Wave6Chances;
    public static float Wave7Chances;
    public static float Wave8Chances;
    public static float EnchanterHelmetHeight;
    public static List<String> ArmorBlackList;
    public static boolean IllagerArmor;
    public static boolean crossArms;

    static {
        {
            final Pair<CommonConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder()
                    .configure(CommonConfig::new);
            COMMON = specPair.getLeft();
            COMMON_SPEC = specPair.getRight();
        }
        {
            final Pair<ClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder()
                    .configure(ClientConfig::new);
            CLIENT = specPair.getLeft();
            CLIENT_SPEC = specPair.getRight();
        }
    }

    public static void bakeCommonConfig() {
        Wave1Chances = COMMON.Wave1.get().floatValue();
        Wave2Chances = COMMON.Wave2.get().floatValue();
        Wave3Chances = COMMON.Wave3.get().floatValue();
        Wave4Chances = COMMON.Wave4.get().floatValue();
        Wave5Chances = COMMON.Wave5.get().floatValue();
        Wave6Chances = COMMON.Wave6.get().floatValue();
        Wave7Chances = COMMON.Wave7.get().floatValue();
        Wave8Chances = COMMON.Wave8.get().floatValue();
        ArmorBlackList = COMMON.ArmorBlackList.get();
        IllagerArmor = COMMON.IllagerArmor.get();
    }

    public static void bakeClientConfig() {
        EnchanterHelmetHeight = CLIENT.EnchanterHelmetHeight.get().floatValue();
        crossArms = CLIENT.IllagerCrossArms.get();
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getSpec() == IWAConfig.COMMON_SPEC) {
            bakeCommonConfig();
        }
        if (configEvent.getConfig().getSpec() == IWAConfig.CLIENT_SPEC) {
            bakeClientConfig();
        }
    }

    public static class CommonConfig {

        public final ModConfigSpec.DoubleValue Wave1;
        public final ModConfigSpec.DoubleValue Wave2;
        public final ModConfigSpec.DoubleValue Wave3;
        public final ModConfigSpec.DoubleValue Wave4;
        public final ModConfigSpec.DoubleValue Wave5;
        public final ModConfigSpec.DoubleValue Wave6;
        public final ModConfigSpec.DoubleValue Wave7;
        public final ModConfigSpec.DoubleValue Wave8;
        public final ModConfigSpec.BooleanValue IllagerArmor;
        public final ModConfigSpec.ConfigValue<List<String>> ArmorBlackList;

        public CommonConfig(ModConfigSpec.Builder builder) {
            Wave1 = builder.translation(IllagersWearArmor.MODID + ".config.wave1").defineInRange("Wave 1 Armor Chances",
                    0.30F, 0.0001F, 100F);
            Wave2 = builder.translation(IllagersWearArmor.MODID + ".config.wave2").defineInRange("Wave 2 Armor Chances",
                    0.32F, 0.0001F, 100F);
            Wave3 = builder.translation(IllagersWearArmor.MODID + ".config.wave3").defineInRange("Wave 3 Armor Chances",
                    0.34F, 0.0001F, 100F);
            Wave4 = builder.translation(IllagersWearArmor.MODID + ".config.wave4").defineInRange("Wave 4 Armor Chances",
                    0.36F, 0.0001F, 100F);
            Wave5 = builder.translation(IllagersWearArmor.MODID + ".config.wave5").defineInRange("Wave 5 Armor Chances",
                    0.38F, 0.0001F, 100F);
            Wave6 = builder.translation(IllagersWearArmor.MODID + ".config.wave6").defineInRange("Wave 6 Armor Chances",
                    0.40F, 0.0001F, 100F);
            Wave7 = builder.translation(IllagersWearArmor.MODID + ".config.wave7").defineInRange("Wave 7 Armor Chances",
                    0.42F, 0.0001F, 100F);
            Wave8 = builder.translation(IllagersWearArmor.MODID + ".config.wave8").defineInRange("Wave 8 Armor Chances",
                    0.48F, 0.0001F, 100F);
            ArmorBlackList = builder.translation(IllagersWearArmor.MODID + ".config.blacklist")
                    .comment("This will make sure any entity id in this list wont spawn with armor. Example outputs would be \"minecraft:vex\" and \"minecraft:vex\",\"minecraft:witch\"")
                    .define("Illager Armor BlackList", Lists.newArrayList("minecraft:vex"));
            IllagerArmor = builder.translation(IllagersWearArmor.MODID + ".config.illagerArmor")
                    .define("Have Illagers spawn with armor at all?", true);
        }
    }

    public static class ClientConfig {
        public final ModConfigSpec.BooleanValue IllagerCrossArms;
        public final ModConfigSpec.DoubleValue EnchanterHelmetHeight;
        public final ModConfigSpec.BooleanValue pillagerRenderer;

        public ClientConfig(ModConfigSpec.Builder builder) {
            IllagerCrossArms = builder.translation(IllagersWearArmor.MODID + ".config.illagerCrossArms")
                    .define("Have Illagers cross their arms when neutral?", true);
            EnchanterHelmetHeight = builder.translation(IllagersWearArmor.MODID + ".config.height").defineInRange("Height of the Enchanters helmet", -15.0F, -500.0F, 100F);
            pillagerRenderer = builder.define("Allow new pillager renderer", true);
        }
    }
}