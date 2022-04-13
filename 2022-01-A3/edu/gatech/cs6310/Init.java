package edu.gatech.cs6310;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class Init {
    /* connection
    1. 执行语句 创建Class

     */
    private TreeMap<String, Customer> customers = new TreeMap<String, Customer>();
    private ArrayList<Drone> drones = new ArrayList<Drone>();
    private TreeMap<String, Store> stores = new TreeMap<String, Store>();
    private Connection con;

    public Init(Connection con) {
        this.con = con;
    }

    public TreeMap getCustomers() throws ParseException {
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from customers";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String customerID = rs.getString("customerID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastnName");
                String phoneNumber = rs.getString("phoneNumber");
                int rating = Integer.getInteger(rs.getString("rating"));
                int credits = Integer.getInteger(rs.getString("credits"));
                Date time = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = Boolean.parseBoolean(rs.getString("flag"));
                Customer c = new Customer(customerID, firstName, lastName, phoneNumber, rating, credits);
                customers.put(customerID, c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void getItems() {
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from items";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String storeName = rs.getString("storeName");
                Store s = stores.get(storeName);
                String id = rs.getString("itemName");
                int weight = Integer.getInteger(rs.getString("weight"));
                Item item = new Item(id, weight);
                s.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getItemLines() throws ParseException {
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from itemLines";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String storeName = rs.getString("storeName");
                Store s = stores.get(storeName);
                String orderID = rs.getString("orderID");
                Order o = s.getOrder(orderID);
                String itemName = rs.getString("itemName");
                Item item = s.getItem(itemName);
                int lineQuantity = Integer.getInteger(rs.getString("lineQuantity"));
                int lineCost = Integer.getInteger(rs.getString("lineCost"));
                int lineWeight = Integer.getInteger(rs.getString("totalWeight"));
                Date time = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = Boolean.parseBoolean(rs.getString("flag"));
                ItemLine itemline = new ItemLine(item, lineCost, lineWeight, lineQuantity);
                o.addItemline2(itemline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getOrders() throws ParseException {
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from itemLines";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String storeName = rs.getString("storeName");
                Store s = stores.get(storeName);
                String orderID = rs.getString("orderID");
                String customerID = rs.getString("customerID");
                String droneID = rs.getString("droneID");
                int totalCost = Integer.getInteger(rs.getString("totalCost"));
                int totalWeight = Integer.getInteger(rs.getString("totalWeight"));
                Date time = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = Boolean.parseBoolean(rs.getString("flag"));
                Drone d = s.getDrone(droneID);
                Customer c = customers.get(customerID);
                ArrayList orders = s.getOrders();
                Order o = new Order(orderID,c,d, totalCost, totalWeight);
                orders.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDrones() throws ParseException {
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from Drones";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String storeName = rs.getString("storeName");
                Store s = stores.get(storeName);
                String id = rs.getString("droneID");
                int capacity = Integer.getInteger(rs.getString("capacity"));
                int remainingCap = Integer.getInteger(rs.getString("remainingCap"));
                int numOrders = Integer.getInteger(rs.getString("numOrders"));
                int fuel = Integer.getInteger(rs.getString("fuel"));
                int remainFuel = Integer.getInteger(rs.getString("fuel"));
                Date time = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = Boolean.parseBoolean(rs.getString("flag"));
                String pilotID = rs.getString("pilotID");

                if (pilotID == null) {
                    Drone drone = new Drone(storeName, id, capacity, remainingCap, fuel, remainFuel, numOrders, null);
                    s.add(drone);
                } else {
                    Pilot np = pilots.get(pilotID);
                    Drone drone = new Drone(storeName, id, capacity, remainingCap, fuel, remainFuel, numOrders, np);
                    s.add(drone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public TreeMap<String, Store> getStores() throws ParseException{
        TreeMap<String, Store> stores = new TreeMap<>();
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from stores";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String storeName = rs.getString("storeName");
                int revenue = rs.getInt("revenue");
                Store s = new Store(storeName, revenue);
                this.initStore(s);
                stores.put(storeName, s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stores;
    }

    private void initStore(Store s) throws ParseException {
        this.initStoreCatalog(s);
        this.initStoreDrones(s);
        this.initStoreOrders(s);
    }
    private void initStoreCatalog(Store s){
        ArrayList<Item> items = new ArrayList<>();
        try {
            Statement state = con.createStatement();
            String sql = "select * from items where storeName = "+s.getName();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String itemName = rs.getString("itemName");
                int weight = rs.getInt("weight");
                s.addItem(new Item(itemName, weight));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initStoreDrones(Store s){
        try {
            Statement state = con.createStatement();
            String sql = "select * from drones where storeName = "+s.getName();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String droneID = rs.getString("droneID");
                int capacity = rs.getInt("capacity");
                int fuel = rs.getInt("fuel");
                int numOrders = rs.getInt("numsOrders");
                int remainingCap = rs.getInt("remainingCap");
                int remainFuel = rs.getInt("remainFuel");
                String pilotID = rs.getString("pilotID"); //todo drone-pilot reference
                Drone d = new Drone(s.getName(), droneID, capacity, fuel);
                d.setNumOrders(numOrders);
                d.setRemainFuel(remainFuel);
                d.setRemainingCap(remainingCap);
                s.addDrone(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initStoreOrders(Store s) throws ParseException {
        try {
            Statement state = con.createStatement();
            String sql = "select * from orders where storeName = "+s.getName();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                String droneID = rs.getString("droneID");
                String customerID = rs.getString("customerID");
                int totalCost = rs.getInt("totalCost");
                int totalWeight = rs.getInt("totalWeight");
                String status = rs.getString("status");
                Date timeStamp = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = Boolean.parseBoolean(rs.getString("flag"));

                Drone d = s.getDrone(droneID);
                Customer c = this.customers.get(customerID);
                Order o = new Order(orderID, c, d, totalCost, totalWeight, status, timeStamp, flag);
                s.addOrder(o);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
