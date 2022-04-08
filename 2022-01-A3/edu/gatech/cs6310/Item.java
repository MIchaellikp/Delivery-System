package edu.gatech.cs6310;

import static java.lang.CharSequence.compare;

public class Item implements Comparable<Item>{
    private String name;
    private int weight;

    /**
     * Helper method to initialize a item
     *
     */
    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public static int compareStrings(String s1, String s2) {
        return compare(s1,s2);
    }

    @Override
    public int compareTo(Item item) {
        return compareStrings(this.name, item.getName());
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
