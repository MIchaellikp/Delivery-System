package edu.gatech.cs6310.util;

import edu.gatech.cs6310.*;

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

    private TreeMap<String, Customer> customers = new TreeMap<String, Customer>();
    private ArrayList<Pilot> pilots = new ArrayList<>();
    private TreeMap<String, Store> stores = new TreeMap<String, Store>();
    private Connection con;

    public Init(Connection con) throws ParseException {
        this.con = con;
        this.customers = this.initCustomers();
        this.pilots = this.initPilots();
        this.initStores();
    }

    private TreeMap initCustomers() throws ParseException {
        TreeMap<String, Customer> customers = new TreeMap<>();
        try {
            Statement state = con.createStatement();
            String sql = "select * from Customers";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String customerID = rs.getString("customerID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phoneNumber = rs.getString("phoneNumber");
                int rating = rs.getInt("rating");
                int credits = rs.getInt("credits");
                Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = rs.getBoolean("flag");
                Customer c = new Customer(customerID, firstName, lastName, phoneNumber, rating, credits);
                customers.put(customerID, c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private ArrayList<Pilot> initPilots() throws ParseException{
        ArrayList<Pilot> pilots = new ArrayList<>();
        try {
            Statement state = con.createStatement();
            String sql = "select * from Pilots";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String accountID = rs.getString("accountID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phoneNumber = rs.getString("phoneNumber");
                String taxID = rs.getString("taxID");
                String licenseID = rs.getString("licenseID");
                int expcLevel = rs.getInt("expcLevel");
//                String droneID = rs.getString("droneID");
                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = rs.getBoolean("flag");
                pilots.add(new Pilot(accountID, firstName, lastName,
                        phoneNumber, taxID, licenseID, expcLevel, timeStamp, flag));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pilots;
    }

    private void initStores() throws ParseException{
        //TreeMap<String, Store> stores = new TreeMap<>();
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from Stores";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String storeName = rs.getString("storeName");
                int revenue = rs.getInt("revenue");
                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = rs.getBoolean("flag");
                Store s = new Store(storeName, revenue);
                s.setTimeStamp(timeStamp);
                s.setFlag(flag);
                this.stores.put(storeName, s);
                this.initStore(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            String sql = "select * from items where storeName = '"+s.getName()+"'";
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
            String sql = "select * from Drones where storeName = '"+s.getName()+"'";
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
                for(Pilot p:this.pilots){
                    if(p.getAccountID().equals(pilotID)){
                        d.setPilot(p);
                        p.setDrone(d);
                        break;
                    }
                }
                s.addDrone(d);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initStoreOrders(Store s) throws ParseException {
        try {
            Statement state = con.createStatement();
            String sql = "select * from Orders where storeName = '"+s.getName()+"'";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                String droneID = rs.getString("droneID");
                String customerID = rs.getString("customerID");
                int totalCost = rs.getInt("totalCost");
                int totalWeight = rs.getInt("totalWeight");
                String status = rs.getString("status");
                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("timeStamp"));
                boolean flag = rs.getBoolean("flag");

                Drone d = s.getDrone(droneID);
                Customer c = this.customers.get(customerID);
                Order o = new Order(orderID, c, d, totalCost, totalWeight, status, timeStamp, flag);
                this.initOrder(s.getName(), o);
                s.addOrder(o);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void initOrder(String storeName, Order o) throws ParseException {
        ArrayList<ItemLine> itemLines = new ArrayList<ItemLine>();
        try {
            Statement state = con.createStatement();
            String sql = "select * from itemLines where orderId = '"+ o.getOrderId()+"'";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                String itemName = rs.getString("itemName");
                int lineQuantity = rs.getInt("lineQuantity");
                int lineCost = rs.getInt("lineCost");
                int lineWeight = rs.getInt("lineWeight");

                Item i = this.stores.get(storeName).getItem(itemName);
                ItemLine itemLine = new ItemLine(i, lineCost, lineWeight, lineQuantity);
                itemLines.add(itemLine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        o.setItemLines(itemLines);
    }


    public TreeMap<String, Customer> getCustomers() {
        return customers;
    }

    public TreeMap<String, Store> getStores() {
        return stores;
    }

    public ArrayList<Pilot> getPilots() {
        return pilots;
    }
}
