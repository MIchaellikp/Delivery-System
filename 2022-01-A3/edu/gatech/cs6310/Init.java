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

    public void getitem() {
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

    public void getItemLine() throws ParseException {
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


}
