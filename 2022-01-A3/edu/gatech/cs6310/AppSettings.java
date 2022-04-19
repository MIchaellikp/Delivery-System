package edu.gatech.cs6310;

import edu.gatech.cs6310.util.Archive;

import java.util.HashMap;

public class AppSettings {


    private String displayWeightUnit;

    private String displayCurrency;

    private int threshold;


    private final static HashMap<String, Double> multiplierLookup_currency = new HashMap<String, Double>() {{
        put("CNY", 6.37);
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

    public int editSettings(String name, String value, Archive archive){
        // return 1: success, 2: name doesn't exist, 3: value invalid

        if (name.equals("threshold")){
            this.setThreshold(Integer.parseInt(value));
            archive.set_threshold(Integer.parseInt(value));
            return 1;
        }
        else if (name.equals("displayCurrency")) {
            if (multiplierLookup_currency.containsKey(value)) {
                this.setCurrency(value);
                return 1;
            }
            return 3;
        }
        else if (name.equals("displayWeightUnit")) {
            if (multiplierLookup_weight.containsKey(value)) {
                this.setWeightUnit(value);
                return 1;
            }
            return 3;
        }
        else {
            return 2;
        }
    }

    public double getWeightMultiplier() {
        return multiplierLookup_weight.get(this.displayWeightUnit);
    }

    public double getCurrencyMultiplier() {
        return multiplierLookup_currency.get(this.displayCurrency);
    }

    public String getDisplayWeightUnit() {
        return this.displayWeightUnit;
    }

    public String getDisplayCurrency() {
        return this.displayCurrency;
    }

    public void setWeightUnit(String unit) {
        this.displayWeightUnit = unit;
    }

    public void setCurrency(String currency) {
        this.displayCurrency = currency;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

}
