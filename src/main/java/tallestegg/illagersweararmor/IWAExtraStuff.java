package tallestegg.illagersweararmor;

public class IWAExtraStuff {

    public static float getWaveArmorChances(int waves) {
        switch (waves) {
        case 0:
            return IWAConfig.Wave1Chances;
        case 1:
            return IWAConfig.Wave2Chances;
        case 2:
            return IWAConfig.Wave3Chances;
        case 3:
            return IWAConfig.Wave4Chances;
        case 4:
            return IWAConfig.Wave5Chances;
        case 5:
            return IWAConfig.Wave6Chances;
        case 6:
            return IWAConfig.Wave7Chances;
        case 7:
            return IWAConfig.Wave8Chances;
        }
        return 0;
    }
}
