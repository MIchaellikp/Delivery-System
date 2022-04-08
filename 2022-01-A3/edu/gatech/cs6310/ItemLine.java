package edu.gatech.cs6310;

import java.util.ArrayList;

import static java.lang.CharSequence.compare;

public class ItemLine implements Comparable<ItemLine>{
    private Item item;
    private int totalCost;
    private int totalWeight;
    private int totalQuantity;

    /**
     * Helper method to initialize a ItemLine
     *
     */

    public ItemLine(Item item, int totalPrice, int totalWeight, int totalQuantity) {
        this.item = item;
        this.totalCost = totalPrice;
        this.totalWeight = totalWeight;
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "item_name:" + this.item.getName() + ",total_quantity:" + this.totalQuantity+ ",total_cost:"
                +  this.totalCost + ",total_weight:" + this.totalWeight;
    }

    public static int compareStrings(String s1, String s2) {
        return compare(s1,s2);
    }

    @Override
    public int compareTo(ItemLine itemline) {
        return compareStrings(this.item.getName(), itemline.getItem().getName());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
