package edu.gatech.cs6310;
import java.util.ArrayList;
import java.lang.Boolean;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.CharSequence.compare;


public class Store implements Comparable<Store>{

    private String name;
    private int revenue;
    private ArrayList<Item> catalog;
    private ArrayList<Drone> drones;
    private ArrayList<Order> orders;

    public Store(String name, int revenue) {
        this.name = name;
        this.revenue = revenue;
        this.catalog = new ArrayList<Item>();
        this.drones = new ArrayList<Drone>();
        this.orders = new ArrayList<Order>();
    }

    /**
     * Helper method to add Item into Store
     *
     * @param newItem the content of Item Class
     * @return
     */

    public boolean addItem(Item newItem) {
        for (Item item:this.catalog){
            if (item.getName().equals(newItem.getName())){
                return false;
            }
        }
        this.catalog.add(newItem);
        Collections.sort(this.catalog);
        return true;
    }

    /**
     * Helper method to display all items in store
     *
     */

    public void displayItems(){
        for(Item item: this.catalog){
            System.out.println(item.getName() + "," + item.getWeight());
        }
        System.out.println("OK:display_completed");
    }

    /**
     * Helper method to add Drone into Store
     *
     * @param nd the content of new Drone Class
     */

    public void addDrone(Drone nd){
        for(Drone d: this.drones){
            if(d.getId().equals(nd.getId())){
                System.out.println("ERROR:drone_identifier_already_exists");
                return;
            }
        }
        this.drones.add(nd);
        Collections.sort(this.drones);
        System.out.println("OK:change_completed");
    }

    /**
     * Helper method to display all drones in store
     *
     */

    public void displayDrones(){
        for(Drone d : this.drones){
            if(d.getPilot() != null){
                System.out.println(d.toString(d.getPilot()));
            } else{
                System.out.println(d.toString());
            }
        }
        System.out.println("OK:display_completed");
    }

    /**
     * Helper method to assign drone to pilot
     *
     * @param droneID the content of drone's ID
     * @param pilotID the content of pilot's ID
     * @param pilots the content of all pilots in system
     */

    public void flyDrone(String droneID, String pilotID,ArrayList<Pilot> pilots){
        for(Drone d:this.drones){
            if (d.getId().equals(droneID)){
                for(Pilot p: pilots){
                    if (p.getAccountID().equals(pilotID)){
                        if(p.getDrone() != null){
                            p.getDrone().setPilot(null);
                        }
                        d.setPilot(p);
                        p.setDrone(d);
                        System.out.println("OK:change_completed");
                        return;
                    }
                }
                System.out.println("ERROR:pilot_identifier_does_not_exist");
                return;
            }
        }
        System.out.println("ERROR:drone_identifier_does_not_exist");
    }

    /**
     * Helper method to start new order for a customer in store
     *  @param orderID the content of order's ID
     * @param droneID the content of drone's ID
     * @param customerID the content of customer's ID
     * @param customers the content of all customers in the system
     */

    public void createOrder(String orderID, String droneID,
                            String customerID, TreeMap<String, Customer> customers){
        if(this.orders != null) {
            for (Order o : this.orders) {
                if (o.getOrderId().equals(orderID)) {
                    System.out.println("ERROR:order_identifier_already_exists");
                    return;
                }
            }
        }
        for(Drone d: this.drones){
            if (d.getId().equals(droneID)){
                for(Map.Entry<String,Customer> c: customers.entrySet()){
                    if(c.getValue().getAccount().equals(customerID)){
                        Order order = new Order(orderID,c.getValue(),d);
                        order.getDrone().addOrders(1);
                        this.orders.add(order);
                        Collections.sort(orders);
                        System.out.println("OK:change_completed");
                        return;
                    }
                }
                System.out.println("ERROR:customer_identifier_does_not_exist");
                return;
            }
        }
        System.out.println("ERROR:drone_identifier_does_not_exist");
    }

    /**
     * Helper method to display all orders in store
     *
     */

    public void displayOrders(){
        for(Order o: this.orders){
            System.out.println("orderID:" + o.getOrderId());
            o.displayItems();
        }
        return;
    }

    /**
     * Helper method to start new order for a customer in store
     *
     * @param orderId the content of order's ID
     * @param item the content of item's name
     * @param quantity the content of number of Item
     * @param price the content of item's price
     */

    public void requestItem(String orderId, String item, int quantity, int price) {
        for (Order o : this.orders) {
            if (o.getOrderId().equals(orderId)) {
                for (Item i : this.catalog) {
                    if (i.getName().equals(item)) {
                        for (ItemLine itemline : o.getItemLines()) {
                            if (itemline.getItem().getName().equals(i.getName())) {
                                System.out.println("ERROR:item_already_ordered");
                                return;
                            }
                        }
                        int total_cost = quantity * price;
                        int total_weight = i.getWeight() * quantity;
                        if (o.getCustomer().getRemainingCredits() >= total_cost ) {
                            if (o.getDrone().getRemainingCap() >= total_weight) {
                                ItemLine newItemLine = new ItemLine(i,total_cost,total_weight,quantity);
                                o.addItemline(newItemLine, total_weight, total_cost);
                                System.out.println("OK:change_completed");
                                return;
                            } else {
                                System.out.println("ERROR:drone_cant_carry_new_item");
                                return;
                            }
                        } else {
                            System.out.println("ERROR:customer_cant_afford_new_item");
                            return;
                        }
                    }
                }
                System.out.println("ERROR:item_identifier_does_not_exist");
                return;
            }
        }
        System.out.println("ERROR:order_identifier_does_not_exist");
    }

    /**
     * Helper method to finish an order for the customer
     *
     * @param orderId the content of order's ID
     */

    public void finishOrder(String orderId) {
        Customer c;
        Drone d;
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                c = o.getCustomer();
                d = o.getDrone();
                if (d.getPilot() != null) {
                    if (d.getRemainFuel() >= 1) {
                        c.purchase(o.getTotalcost());
                        d.finishOrder(1, o.getTotalweight(),1);
                        this.revenue += o.getTotalcost();
                        this.orders.remove(o);
                        System.out.println("OK:change_completed");
                        return;
                    }else {
                        System.out.println("ERROR:drone_needs_fuel");
                        return;
                    }
                } else {
                    System.out.println("ERROR:drone_needs_pilot");
                    return;
                }
            }
        }
        System.out.println("ERROR:order_identifier_does_not_exist");
        return;
    }

    /**
     * Helper method to help custoemr cancel order
     *
     * @param orderId the content of order's ID
     */

    public void cancelOrder(String orderId){
        for(Order o: orders){
            if(o.getOrderId().equals(orderId)){
                o.getDrone().cancelOrder(o.getTotalweight());
                o.getCustomer().changeRemainingCredits(-o.getTotalcost());
                orders.remove(o);
                System.out.println("OK:change_completed");
                return;
            }
        }
        System.out.println("ERROR:order_identifier_does_not_exist");
        return;
    }

    public static int compareStrings(String s1, String s2) {
        return compare(s1,s2);
    }

    @Override
    public int compareTo(Store store) {
        return compareStrings(this.name, store.getName());
    }

    @Override
    public String toString(){
        return "name:" + this.name + ",revenue:" + this.revenue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getName() {
        return name;
    }

    public int getRevenue() {
        return revenue;
    }

    public ArrayList<Item> getCatalog() {
        return catalog;
    }

}
