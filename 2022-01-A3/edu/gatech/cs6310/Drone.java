package edu.gatech.cs6310;

import java.util.Date;

import static java.lang.CharSequence.compare;

public class Drone implements Comparable<Drone>{
    private String id;
    private String storeName;
    private int capacity;
    private int fuel;
    private int numOrders;
    private int remainingCap;
    private Pilot pilot;
    private int remainFuel;
    private boolean flag;
    private Date timeStamp;

    /**
     * Helper method to initialize a drone
     *
     */

    public Drone(String storeName,String id,  int capacity, int fuel) {
        this.id = id;
        this.storeName = storeName;
        this.capacity = capacity;
        this.fuel = fuel;
        this.remainFuel = fuel;
        this.numOrders = 0;
        this.remainingCap = capacity;
        this.pilot = null;
        this.flag = false;
        this.timeStamp = new Date();
    }

    public Drone(String storeName,String id, int capacity ,int remainingCap, int fuel, int remainFuel, int numOrders, Pilot pilot ) {
        this.id = id;
        this.storeName = storeName;
        this.capacity = capacity;
        this.fuel = fuel;
        this.remainFuel = remainFuel;
        this.numOrders = numOrders;
        this.remainingCap = remainingCap;
        this.pilot = pilot;
        this.flag = false;
        this.timeStamp = new Date();
    }


    /**
     * Helper method to help drone to finish a order
     *
     * @param fuelCost the content of cost of trip
     * @param totalweight the content of order's weight
     * @param exp the content of experience that pilot earned
     */

    public void finishOrder(int fuelCost, int totalweight, int exp) {
        this.remainFuel -= fuelCost;
        this.remainingCap += totalweight;
        this.pilot.addExpcLevel(exp);
        this.numOrders -= 1;
    }

    /**
     * Helper method to help drone to cancel order & reset the remainingcap and ordernumber
     *
     * @param totalweight the content of order's weight
     */

    public void cancelOrder(int totalweight){
        this.remainingCap += totalweight;
        this.numOrders -= 1;
    }

    /**
     * Helper method to add an itemline into the order
     *
     * @param totalweight the content of itemline's weight
     */

    public void addOrderLine(int totalweight) {
        this.remainingCap -= totalweight;
    }

    public void setRemainingCap(int remainingCap) {
        this.remainingCap = remainingCap;
    }


    public String toString(AppSettings settings){
        return "droneID:" + this.id +",total_cap:" + capacity * settings.getWeightMultiplier() + settings.getDisplayWeightUnit()+ ",num_orders:" + numOrders +
                ",remaining_cap:" + remainingCap * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",trips_left:" + remainFuel;
    }

    public String toString_withArchiveState(AppSettings settings){
        if (this.isFlag())
            return "droneID:" + this.id +",total_cap:" + capacity * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",num_orders:" + numOrders +
                ",remaining_cap:" + remainingCap * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",trips_left:" + remainFuel + " (Archived)";
        else
            return "droneID:" + this.id +",total_cap:" + capacity * settings.getWeightMultiplier() + settings.getDisplayWeightUnit()+ ",num_orders:" + numOrders +
                    ",remaining_cap:" + remainingCap * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",trips_left:" + remainFuel + " (Active)";
    }

    public String toString(Pilot p, AppSettings settings){
        return "droneID:" + this.id +",total_cap:" + capacity * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",num_orders:" + numOrders +
                ",remaining_cap:" + remainingCap * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",trips_left:" + remainFuel + ",flown_by:" +
                p.getFirstName() + "_" + p.getLastName();
    }

    public String toString_withArchiveState(Pilot p, AppSettings settings){
        if (this.isFlag())
            return "droneID:" + this.id +",total_cap:" + capacity * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",num_orders:" + numOrders +
                    ",remaining_cap:" + remainingCap * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",trips_left:" + remainFuel + ",flown_by:" +
                    p.getFirstName() + "_" + p.getLastName() + " (Archived)";
        else
            return "droneID:" + this.id +",total_cap:" + capacity * settings.getWeightMultiplier() + settings.getDisplayWeightUnit()+ ",num_orders:" + numOrders +
                    ",remaining_cap:" + remainingCap * settings.getWeightMultiplier() + settings.getDisplayWeightUnit() + ",trips_left:" + remainFuel + ",flown_by:" +
                    p.getFirstName() + "_" + p.getLastName() + " (Active)";
    }

    public static int compareStrings(String s1, String s2) {
        return compare(s1,s2);
    }

    @Override
    public int compareTo(Drone drone) {
        return compareStrings(this.id, drone.getId());
    }

    public void addOrders(int num){
        this.numOrders += 1;
    }

    public int getRemainFuel() {
        return remainFuel;
    }

    public void setRemainFuel(int remainFuel) {
        this.remainFuel = remainFuel;
    }

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    public int getRemainingCap() {
        return remainingCap;
    }

    public void initRemainingcapt(){
        this.remainingCap = this.capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void refreshTimeStamp(){
        this.timeStamp = new Date();
    }
}
