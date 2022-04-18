package edu.gatech.cs6310;

import java.util.HashMap;

public class AppSettings {


    private static String displayWeightUnit;

    private static String displayCurrency;

    private static int threshold;


    private final static HashMap<String, Double> multiplierLookup_currency = new HashMap<String, Double>() {{
        put("RMB", 6.37);
        put("EUR", 0.93);
        put("JPY", 126.62);
        put("USD", 1.00);
    }};

    private final static HashMap<String, Double> multiplierLookup_weight = new HashMap<String, Double>() {{
        put("LB", 1.00);
        put("KG", 0.45);
    }};


    public AppSettings() {
        displayWeightUnit = "LB";
        displayCurrency = "USD";
        threshold = 30;
    }

    public int editSettings(String name, String value){
        // return 1: success, 2: name doesn't exist, 3: value invalid

        if (name.equals("threshold")){
            this.setThreshold(Integer.parseInt(value));
            return 1;
        }
        else if (name.equals("displayWeightUnit")) {
            if (multiplierLookup_currency.getOrDefault(name, null) != null) {
                this.setWeightUnit(value);
                return 1;
            }
            return 3;
        }
        else if (name.equals("displayCurrency")) {
            if (multiplierLookup_weight.getOrDefault(name, null) != null) {
                this.setCurrency(value);
                return 1;
            }
            return 3;
        }
        else {
            return 2;
        }
    }

    public static double getWeightMultiplier() {
        return multiplierLookup_weight.get(displayWeightUnit);
    }

    public static double getCurrencyMultiplier() {
        return multiplierLookup_currency.get(displayCurrency);
    }

    public static String getDisplayWeightUnit() {
        return displayWeightUnit;
    }

    public static String getDisplayCurrency() {
        return displayCurrency;
    }

    public void setWeightUnit(String unit) {
        this.displayWeightUnit = unit;
    }

    public void setCurrency(String currency) {
        this.displayCurrency = currency;
    }

    public static int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

}
