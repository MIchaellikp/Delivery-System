//Usage:
//AppSettings.setWeightUnit(WeightUnit.lbs);
//AppSettings.DronesSetting.setLiftCapacity(10);

public final class AppSettings {

    public static boolean isPilotAllowedWorkingMultipleStores;

    public static WeightUnit weightUnit;

    public static Currency currency;

    public private AppSettings(){
        DronesSetting = new DronesSetting();
    }

    public static void allowPilotWorkingMultipleStores(boolean allow){
        isPilotAllowedWorkingMultipleStores = allow;
    }

    public static void setWeightUnit(WeightUnit unit){
        weightUnit = unit;
    }

    public static void setCurrency(Currency currency){
        currency = currency;
    }

    public static class DronesSetting{
        public static int liftCapacity;

        public static int fuel;

        public static void setLiftCapacity(int capacity){
            liftCapacity = capacity;
        }

        public static void setFuel(int fuel){
            fuel = fuel;
        }
    }
}

public enum WeightUnit{
    lbs,
    kg
}

public enum Currency{
    USD,
    EUR,
    RMB,
    JPY
}
