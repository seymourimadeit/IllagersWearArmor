package tallestegg.illagersweararmor;

public class IWAExtraStuff {
	
    public static float getWaveArmorChances(int waves)
    {
        switch(waves) 
        {
            case 0: 
               return 0.02F;
            case 1:
               return 0.06F;
            case 2:
		       return 0.10F;
            case 3:
               return 0.23F;
            case 4: 
               return 0.32F;
            case 5:
               return 0.45F;
            case 6:
               return 0.67F;
            case 7:
               return 0.69F;
        }
		return 0;
    }
}
