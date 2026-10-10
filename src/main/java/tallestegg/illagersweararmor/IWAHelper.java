package tallestegg.illagersweararmor;

public class IWAHelper {
    public static float getWaveArmorChances(int waves) {
        return switch (waves) {
            case 1 -> IWAConfig.Wave1Chances;
            case 2 -> IWAConfig.Wave2Chances;
            case 3 -> IWAConfig.Wave3Chances;
            case 4 -> IWAConfig.Wave4Chances;
            case 5 -> IWAConfig.Wave5Chances;
            case 6 -> IWAConfig.Wave6Chances;
            case 7 -> IWAConfig.Wave7Chances;
            case 8 -> IWAConfig.Wave8Chances;
            default -> 0;
        };
    }
}
