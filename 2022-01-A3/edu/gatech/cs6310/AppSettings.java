package edu.gatech.cs6310;

import java.util.HashMap;

public final class AppSettings {


    private static String displayWeightUnit;

    private static String displayCurrency;

    private static int defaultLiftCapacity;

    private static int defaultFuel;

    private static int threshold;


    private final static HashMap<String, Double> multiplierLookup = new HashMap<String, Double>() {{
        put("RMB", 6.37);
        put("EUR", 0.93);
        put("JPY", 126.62);
        put("USD", 1.00);
        put("LB", 1.00);
        put("KG", 0.45);
    }};


    public AppSettings() {
        displayWeightUnit = "LB";
        displayCurrency = "USD";
        defaultLiftCapacity = 10;
        defaultFuel = 100;
        threshold = 30;
    }

    public static double getWeightMultiplier() {
        return multiplierLookup.get(displayWeightUnit);
    }

    public static double getCurrencyMultiplier() {
        return multiplierLookup.get(displayCurrency);
    }

    public static String getDisplayWeightUnit() {
        return displayWeightUnit;
    }

    public static String getDisplayCurrency() {
        return displayCurrency;
    }

    public static void setWeightUnit(String unit) {
        displayWeightUnit = unit;
    }

    public static void setCurrency(String currency) {
        currency = currency;
    }

    public static int getThreshold() {
        return threshold;
    }

    public static void setThreshold(int threshold) {
        AppSettings.threshold = threshold;
    }

    public static int getDefaultLiftCapacity() {
        return defaultLiftCapacity;
    }

    public static void setDefaultLiftCapacity(int defaultLiftCapacity) {
        AppSettings.defaultLiftCapacity = defaultLiftCapacity;
    }

    public static int getDefaultFuel() {
        return defaultFuel;
    }

    public static void setDefaultFuel(int defaultFuel) {
        defaultFuel = defaultFuel;
    }
}
