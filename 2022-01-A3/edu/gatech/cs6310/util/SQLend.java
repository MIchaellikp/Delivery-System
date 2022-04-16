package edu.gatech.cs6310.util;

import edu.gatech.cs6310.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SQLend {

    private Connection con;

    public SQLend(Connection con, ArrayList<Pilot> pilots, TreeMap<String, Customer> customers, TreeMap<String, Store> stores) throws SQLException {
        this.con = con;
        this.upsertCustomer(customers);
        this.upsertPilots(pilots);
        this.upsertStores(stores);
    }

    public void upsertStores(TreeMap<String, Store> stores) throws SQLException {
        for(Store s: stores.values()){
            String sql = "REPLACE INTO Stores (storeName,revenue,timeStamp,flag)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,s.getName());
            ps.setInt(2, s.getRevenue());
            ps.setTimestamp(3, new java.sql.Timestamp(s.getTimeStamp().getTime()));
            ps.setBoolean(4, (s.isFlag()));
            ps.executeUpdate();
            String storeName = s.getName();
            this.upsertCatalog(storeName, s.getCatalog());
            this.upsertDrones(storeName, s.getDrones());
            this.upsertOrders(storeName, s.getOrders());

        }
    }

    public void upsertCatalog(String storeName, ArrayList<Item> catalog) throws SQLException {
        for(Item i: catalog){
            String sql = "REPLACE INTO items (itemName,storeName,weight)" +
                    "VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, i.getName());
            ps.setString(2, storeName);
            ps.setInt(3, i.getWeight());
            ps.executeUpdate();
        }
    }

    public void upsertDrones(String storeName, ArrayList<Drone> drones) throws SQLException {
        for(Drone d: drones){
            String sql = "REPLACE INTO Drones (droneID,storeName,capacity,remainingCap,numsOrders," +
                    "fuel,remainFuel,pilotID,timeStamp,flag)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, d.getId());
            ps.setString(2, storeName);
            ps.setInt(3, d.getCapacity());
            ps.setInt(4, d.getRemainingCap());
            ps.setInt(5, d.getNumOrders());
            ps.setInt(6, d.getFuel());
            ps.setInt(7, d.getRemainFuel());
            ps.setObject(8, d.getPilot().getAccountID(), Types.VARCHAR);
            ps.setTimestamp(9, new java.sql.Timestamp(d.getTimeStamp().getTime()));
            ps.setBoolean(10, d.isFlag());
            ps.executeUpdate();
        }
    }
    public void upsertOrders(String storeName, ArrayList<Order> orders) throws SQLException{
        for(Order o: orders){
            String sql = "REPLACE INTO Orders (storeName,orderID,droneID,totalCost,totalWeight," +
                    "customerID,status,timeStamp,flag)" +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, storeName);
            ps.setString(2, o.getOrderId());
            ps.setString(3, o.getDrone().getId());
            ps.setInt(4, o.getTotalcost());
            ps.setInt(5, o.getTotalweight());
            ps.setString(6, o.getCustomer().getAccount());
            ps.setString(7, o.getStatus());
            ps.setTimestamp(8, new java.sql.Timestamp(o.getTimeStamp().getTime()));
            ps.setBoolean(9, o.isFlag());
            ps.executeUpdate();
            this.upsertItemLines(o.getOrderId(),o.getItemLines());
        }
    }

    public void upsertItemLines(String orderId, ArrayList<ItemLine> itemLines) throws SQLException{
        for(ItemLine il: itemLines){
            String sql = "REPLACE INTO itemLines (orderId,itemName,lineQuantity,lineCost,lineWeight)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, orderId);
            ps.setString(2, il.getItem().getName());
            ps.setInt(3, il.getTotalQuantity());
            ps.setInt(4, il.getTotalCost());
            ps.setInt(5, il.getTotalWeight());
            ps.executeUpdate();
        }
    }


    public void upsertCustomer(TreeMap<String,Customer> customers) throws SQLException {
        for(Customer c: customers.values()){
            String insert = "REPLACE into Customers (customerID, firstName, lastName, phoneNumber," +
                    " rating,credits, remainingCredit,timeStamp,flag ) values(?,?,?,?,?,?,?,?,?)" ;
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setString(1,c.getAccount());
            ps.setString(2,c.getFirstName());
            ps.setString(3,c.getLastName());
            ps.setString(4,c.getPhoneNumber());
            ps.setInt(5, c.getRating());
            ps.setInt(6, c.getCredits());
            ps.setInt(7, c.getRemainingCredits());
            ps.setTimestamp(8, new java.sql.Timestamp(c.getTimeStamp().getTime()));
            ps.setBoolean(9, (c.isFlag()));
            ps.executeUpdate();
        }
    }

    public void upsertPilots(ArrayList<Pilot> pilots) throws SQLException {
        Statement state = con.createStatement();
        for(Pilot p: pilots){
            String insert = "REPLACE into Pilots (accountID, firstName,lastName,phoneNumber,taxID," +
                            "licenseID, expcLevel, droneID, timeStamp,flag ) values(?,?,?,?,?,?,?,?,?,?)" ;
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setString(1,p.getAccountID());
            ps.setString(2,p.getFirstName());
            ps.setString(3,p.getLastName());
            ps.setString(4,p.getPhoneNumber());
            ps.setString(5,p.getTaxID());
            ps.setString(6,p.getLicenseID());
            ps.setInt(7, p.getExpcLevel());
            ps.setObject(8, p.getDrone().getId(), Types.VARCHAR);
            ps.setTimestamp(9, new java.sql.Timestamp(p.getTimeStamp().getTime()));
            ps.setBoolean(10, (p.isFlag()));
            ps.executeUpdate();
        }
    }



}
