package edu.gatech.cs6310;
import java.util.*;
import static java.lang.CharSequence.compare;



public class Store implements Comparable<Store>{

    private String name;
    private int revenue;
    private ArrayList<Item> catalog;
    private ArrayList<Drone> drones;
    private ArrayList<Order> orders;
    private boolean flag;
    private Date timeStamp;

    public Store(String name, int revenue) {
        this.name = name;
        this.revenue = revenue;
        this.catalog = new ArrayList<Item>();
        this.drones = new ArrayList<Drone>();
        this.orders = new ArrayList<Order>();
        this.flag = false;
        this.timeStamp = new Date();
    }

    /**
     * Helper method to add Item into Store
     *
     * @param newItem the content of Item Class
     * @return
     */

    public boolean addItem(Item newItem) {
        this.refreshTimeStamp(); // Update Store TimeStamp when new items added to the store
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

    public String displayItems(){
        for(Item item: this.catalog){
            System.out.println(item.getName() + "," + item.getWeight());
        }
        return ("OK:display_completed");
    }

    /**
     * Helper method to add Drone into Store
     *
     * @param nd the content of new Drone Class
     */

    public String addDrone(Drone nd){
        this.refreshTimeStamp(); // Update Store TimeStamp when new drones are added to the store
        for(Drone d: this.drones){
            if(d.getId().equals(nd.getId())){
                //System.out.println("ERROR:drone_identifier_already_exists");
                return "ERROR:drone_identifier_already_exists";
            }
        }
        this.drones.add(nd);
        nd.refreshTimeStamp(); // Update Drone TimeStamp when it's added to a store
        Collections.sort(this.drones);
        //System.out.println("OK:change_completed");
        return "OK:change_completed";
    }

    /**
     * Helper method to display all drones in store
     *
     */

    public String displayDrones(){
        for(Drone d : this.drones){
            if(d.getPilot() != null){
                if (!d.isFlag())
                    System.out.println(d.toString(d.getPilot()));
            } else{
                if (!d.isFlag())
                    System.out.println(d.toString());
            }
        }
        return "OK:display_completed";
    }

    public String displayDrones_withArchiveState(){
        for(Drone d : this.drones){
            if(d.getPilot() != null){
                System.out.println(d.toString_withArchiveState(d.getPilot()));
            } else{
                System.out.println(d.toString_withArchiveState());
            }
        }
        return "OK:display_completed";
    }

    /**
     * Helper method to assign drone to pilot
     *
     * @param droneID the content of drone's ID
     * @param pilotID the content of pilot's ID
     * @param pilots the content of all pilots in system
     */

    public String flyDrone(String droneID, String pilotID,ArrayList<Pilot> pilots){
        for(Drone d:this.drones){
            if (d.getId().equals(droneID)){
                for(Pilot p: pilots){
                    if (p.getAccountID().equals(pilotID)){
                        if(p.getDrone() != null){
                            p.getDrone().setPilot(null);
                        }
                        d.setPilot(p);
                        p.setDrone(d);
                        d.refreshTimeStamp(); // Update Drone TimeStamp
                        p.refreshTimeStamp(); // Update pilot TimeStamp
                        this.refreshTimeStamp(); // Update store Timestamp
                        //System.out.println("OK:change_completed");
                        return "OK:change_completed";
                    }
                }
                //System.out.println("ERROR:pilot_identifier_does_not_exist");
                return "ERROR:pilot_identifier_does_not_exist";
            }
        }
        return "ERROR:drone_identifier_does_not_exist";
    }

    /**
     * Helper method to start new order for a customer in store
     *  @param orderID the content of order's ID
     * @param droneID the content of drone's ID
     * @param customerID the content of customer's ID
     * @param customers the content of all customers in the system
     */

    public String createOrder(String orderID, String droneID,
                            String customerID, TreeMap<String, Customer> customers){
        if(this.orders != null) {
            for (Order o : this.orders) {
                if (o.getOrderId().equals(orderID)) {
                    //System.out.println("ERROR:order_identifier_already_exists");
                    return "ERROR:order_identifier_already_exists";
                }
            }
        }
        for(Drone d: this.drones){
            if (d.getId().equals(droneID)){
                for(Map.Entry<String,Customer> c: customers.entrySet()){
                    if(c.getValue().getAccount().equals(customerID)){
                        Order order = new Order(orderID,c.getValue(),d);
                        order.getDrone().addOrders(1);
                        this.addOrder(order);
                        this.refreshTimeStamp(); // Update store Timestamp
                        order.refreshTimeStamp(); // Update order Timestamp
                        order.getDrone().refreshTimeStamp(); // Update drone Timestamp
                        c.getValue().refreshTimeStamp(); // Update customer Timestamp
                        Collections.sort(orders);
                        //System.out.println("OK:change_completed");
                        return "OK:change_completed";
                    }
                }
                //System.out.println("ERROR:customer_identifier_does_not_exist");
                return "ERROR:customer_identifier_does_not_exist";
            }
        }
        //System.out.println("ERROR:drone_identifier_does_not_exist");
        return "ERROR:drone_identifier_does_not_exist";
    }

    /**
     * Helper method to display all orders in store
     *
     */

    public void displayOrders(){
        for(Order o: this.orders){
            if (!o.isFlag()) {
                System.out.println("orderID:" + o.getOrderId());
                o.displayItems();
            }
        }
        return;
    }

    public void displayOrders_withArchiveState(){
        for(Order o: this.orders){
            if (o.isFlag()) {
                System.out.println("orderID:" + o.getOrderId() + " (Archived)");
                o.displayItems();
            }
            else {
                System.out.println("orderID:" + o.getOrderId() + " (Active)");
                o.displayItems();
            }
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

    public String requestItem(String orderId, String item, int quantity, int price) {
        for (Order o : this.orders) {
            if (o.getOrderId().equals(orderId)) {
                for (Item i : this.catalog) {
                    if (i.getName().equals(item)) {
                        for (ItemLine itemline : o.getItemLines()) {
                            if (itemline.getItem().getName().equals(i.getName())) {
                                //System.out.println("ERROR:item_already_ordered");
                                return "ERROR:item_already_ordered";
                            }
                        }
                        int total_cost = quantity * price;
                        int total_weight = i.getWeight() * quantity;
                        if (o.getCustomer().getRemainingCredits() >= total_cost ) {
                            if (o.getDrone().getRemainingCap() >= total_weight) {
                                ItemLine newItemLine = new ItemLine(i,total_cost,total_weight,quantity);
                                o.addItemline(newItemLine, total_weight, total_cost);
                                o.refreshTimeStamp(); // Update order Timestamp
                                o.getDrone().refreshTimeStamp(); // Update drone Timestamp
                                o.getCustomer().refreshTimeStamp(); // Update customer Timestamp
                                this.refreshTimeStamp(); // Update store Timestamp
                                //System.out.println("OK:change_completed");
                                return "OK:change_completed";
                            } else {
                                //System.out.println("ERROR:drone_cant_carry_new_item");
                                return "ERROR:drone_cant_carry_new_item";
                            }
                        } else {
                            //System.out.println("ERROR:customer_cant_afford_new_item");
                            return "ERROR:customer_cant_afford_new_item";
                        }
                    }
                }
                //System.out.println("ERROR:item_identifier_does_not_exist");
                return "ERROR:item_identifier_does_not_exist";
            }
        }
        return "ERROR:order_identifier_does_not_exist";
    }

    /**
     * Helper method to finish an order for the customer
     *
     * @param orderId the content of order's ID
     */

    public String finishOrder(String orderId) {
        Customer c;
        Drone d;
        //this.refreshTimeStamp();
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                c = o.getCustomer();
                d = o.getDrone();
                if (d.getPilot() != null) {
                    if (d.getRemainFuel() >= 1) {
                        c.purchase(o.getTotalcost());
                        d.finishOrder(1, o.getTotalweight(),1);
                        this.revenue += o.getTotalcost();
                        o.setStatus("Delivered");
                        o.setFlag(true);
                        //this.orders.remove(o);
                        //System.out.println("OK:change_completed");
                        this.refreshTimeStamp(); // Update store Timestamp
                        o.refreshTimeStamp(); // Update order Timestamp
                        c.refreshTimeStamp(); // Update customer Timestamp
                        d.refreshTimeStamp(); // Update drone Timestamp
                        return "OK:change_completed";
                    }else {
                        //System.out.println("ERROR:drone_needs_fuel");
                        return "ERROR:drone_needs_fuel";
                    }
                } else {
                    //System.out.println("ERROR:drone_needs_pilot");
                    return "ERROR:drone_needs_pilot";
                }
            }
        }
        //System.out.println("ERROR:order_identifier_does_not_exist");
        return "ERROR:order_identifier_does_not_exist";
    }

    /**
     * Helper method to help custoemr cancel order
     *
     * @param orderId the content of order's ID
     */

    public String cancelOrder(String orderId){
        for(Order o: orders){
            if(o.getOrderId().equals(orderId)){
                o.getDrone().cancelOrder(o.getTotalweight());
                o.getCustomer().changeRemainingCredits(-o.getTotalcost());
                //orders.remove(o);
                o.getCustomer().refreshTimeStamp();
                o.getDrone().refreshTimeStamp();
                o.refreshTimeStamp();
                this.refreshTimeStamp();
                o.setStatus("Cancelled");
                o.setFlag(true);
                //System.out.println("OK:change_completed");
                return "OK:change_completed";
            }
        }
        //System.out.println("ERROR:order_identifier_does_not_exist");
        return "ERROR:order_identifier_does_not_exist";
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

    public String toString_withArchiveState(){
        if (this.isFlag())
            return "name:" + this.name + ",revenue:" + this.revenue + " (Archived)";
        else
            return "name:" + this.name + ",revenue:" + this.revenue + " (Active)";
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

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Order getOrder(String orderId) {
        for(Order o : this.orders){
            if (o.getOrderId().equals(orderId)){
                return o;
            }
        }
        return null;
    }

    public Item getItem(String itemName) {
        for(Item i : this.catalog){
            if (i.getName().equals(itemName)){
                return i;
            }
        }
        return null;
    }

    public Drone getDrone(String droneID) {
        for(Drone d : this.drones){
            if (d.getId().equals(droneID)){
                return d;
            }
        }
        return null;
    }

    public void addOrder(Order o){
        this.orders.add(o);
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

    public void setCatalog(ArrayList<Item> catalog) {
        this.catalog = catalog;
    }

    public ArrayList<Drone> getDrones() {
        return drones;
    }

    public void setDrones(ArrayList<Drone> drones) {
        this.drones = drones;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void refreshTimeStamp(){
        this.timeStamp = new Date();
    }
}
