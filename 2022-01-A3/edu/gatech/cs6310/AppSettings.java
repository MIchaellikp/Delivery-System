package edu.gatech.cs6310;

public final class AppSettings {

    public static boolean isPilotAllowedWorkingMultipleStores;

    public static WeightUnit weightUnit;

    public static Currency currency;

    public AppSettings() {
        weightUnit = WeightUnit.lbs;
        currency = Currency.USD;
        DronesSetting = new DronesSetting();
    }

    public static void allowPilotWorkingMultipleStores(boolean allow) {
        isPilotAllowedWorkingMultipleStores = allow;
    }

    public static void setWeightUnit(WeightUnit unit) {
        weightUnit = unit;
    }

    public static void setCurrency(Currency currency) {
        currency = currency;
    }

    public DronesSetting DronesSetting;

    public static class DronesSetting {
        public static int liftCapacity;

        public static int fuel;

        public DronesSetting(){
            liftCapacity = 10;
            fuel = 100;
        }

        public static void setLiftCapacity(int capacity) {
            liftCapacity = capacity;
        }

        public static void setFuel(int fuel) {
            fuel = fuel;
        }
    }
}
enum WeightUnit{
    lbs,
    kg
}

enum Currency{
    USD,
    EUR,
    RMB,
    JPY
}
