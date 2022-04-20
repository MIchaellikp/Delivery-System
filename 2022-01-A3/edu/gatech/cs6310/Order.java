package edu.gatech.cs6310;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static java.lang.CharSequence.compare;


public class Order implements Comparable<Order>{
     private ArrayList<ItemLine> itemLines ;
     private String orderId;
     private Customer customer;
     private Drone drone;
     private int totalcost;
     private int totalweight;

     private String status; // Pending, Delivered, Cancelled

     Date timeStamp;

     private boolean flag;

    /**
     * Helper method to initialize the order
     *
     * @param orderId the content of unique order id
     * @param customer the content of customer who own this order
     * @param drone the content of drone which gonna deliver this order
     */

    public Order(String orderId, Customer customer, Drone drone) {
        this.itemLines = new ArrayList<ItemLine>();
        this.orderId = orderId;
        this.customer = customer;
        this.drone = drone;
        this.totalcost = 0;
        this.totalweight = 0;
        this.status = "Pending";
        this.timeStamp = new Date();
        this.flag = false;
    }

    public Order(String orderId, Customer customer, Drone drone,
                 int totalCost, int totalWeight, String status, Date timeStamp, boolean flag) {
        this.itemLines = new ArrayList<ItemLine>();
        this.orderId = orderId;
        this.customer = customer;
        this.drone = drone;
        this.totalcost = totalCost;
        this.totalweight = totalWeight;
        this.status = status;
        this.timeStamp = timeStamp;
        this.flag = flag;
    }

    public Order(String orderId, Customer customer, Drone drone, int totalcost, int totalweight) {
        this.itemLines = new ArrayList<ItemLine>();
        this.orderId = orderId;
        this.customer = customer;
        this.drone = drone;
        this.totalcost = totalcost;
        this.totalweight = totalweight;
    }

    /**
     * Helper method to display all itemlines in order
     *
     */

    public void displayItems(AppSettings settings){
        for(ItemLine il: this.itemLines){
            System.out.println(il.toString(settings));
        }
        return;
    }

    public static int compareStrings(String s1, String s2) {
        return compare(s1,s2);
    }

    @Override
    public int compareTo(Order o) {
        return compareStrings(this.orderId, o.getOrderId());
    }

    /**
     * Helper method to add new Item line into Order
     *
     * @param itemLine the content of new itemLine
     * @param lineweight the content of new itemLine's weight
     * @param linecost the content of new itemLine's cost
     */

    public void addItemline(ItemLine itemLine, int lineweight, int linecost){
        this.itemLines.add(itemLine);
        this.totalweight += lineweight;
        this.totalcost += linecost;
        Collections.sort(itemLines);
        this.getDrone().addOrderLine(lineweight);
        this.getCustomer().changeRemainingCredits(linecost);
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public ArrayList<ItemLine> getItemLines() {
        return itemLines;
    }

    public void setItemLines(ArrayList<ItemLine> itemLines) {
        this.itemLines = itemLines;
        Collections.sort(this.itemLines);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderid) {
        this.orderId = orderid;
    }

    public int getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(int totalcost) {
        this.totalcost = totalcost;
    }

    public int getTotalweight() {
        return totalweight;
    }

    public void setTotalweight(int totalweight) {
        this.totalweight = totalweight;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void refreshTimeStamp(){
        this.timeStamp = new Date();
    }
}
