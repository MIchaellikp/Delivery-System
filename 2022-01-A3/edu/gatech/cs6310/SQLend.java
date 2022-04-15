package edu.gatech.cs6310;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SQLend {

    private Connection con;

    public SQLend(Connection con) {
        this.con = con;
    }

    public void upsertStore(TreeMap<String, Store> stores) throws SQLException {
        for(Store s: stores.values()){
            String sql = "REPLACE INTO　Stores (storeName,revenue,timeStamp,flag)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,s.getName());
            ps.setInt(2, s.getRevenue());
            ps.setDate(3, (Date) s.getTimeStamp());
            ps.setBoolean(4, (s.isFlag()));
            ps.executeUpdate();
            String storeName = s.getName();
            upsertCatalog(storeName, s.getCatalog());
            upsertDrones(storeName, s.getDrones());
            upsertOrders(storeName, s.getOrders());

        }
    }

    public void upsertCatalog(String storeName, ArrayList<Item> catalog) throws SQLException {
        for(Item i: catalog){
            String sql = "REPLACE INTO　items (itemName,storeName,weight)" +
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
            ps.setString(8, d.getPilot().getAccountID());
            ps.setDate(9, (Date) d.getTimeStamp());
            ps.setBoolean(10, d.isFlag());
            ps.executeUpdate();
        }
    }

    public void upsertOrders(String storeName, ArrayList<Order> orders) throws SQLException{
        for(Order o: orders){
            String sql = "REPLACE INTO Drones (storeName,orderID,droneID,totalCost,totalWeight," +
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
            ps.setDate(8, (Date) o.getTimeStamp());
            ps.setBoolean(9, o.isFlag());
            ps.executeUpdate();
        }
    }



//    public void updateStore(TreeMap<String, Store> stores) throws SQLException {
//        Statement state = con.createStatement();
//        for(Map.Entry<String,Store> s: stores.entrySet()){
//            String sql = "select * from stores where storeName =" + s.getKey();
//            ResultSet rs = state.executeQuery(sql);
//            if(rs.next()){
//                //update
//                String update = "update Stores set revenue = ?, timestamp = ? ," +
//                        "flag = ? where storeName = " + s.getKey();
//                PreparedStatement ps = con.prepareStatement(update);
//
//                ps.setInt(1, (s.getValue().getRevenue()));
//                ps.setDate(2, (Date) s.getValue().getTimeStamp());
//                ps.setBoolean(3, (s.getValue().isFlag()));
//                ps.executeUpdate();
//                updateDrone(s.getValue());
//                updateOrder(s.getValue());
//            } else {
//                //insert
//                String insert = "insert into Stores (storeName, revenue,timeStamp,flag ) values(?,?,?,?)" ;
//                PreparedStatement ps = con.prepareStatement(insert);
//                ps.setString(1,s.getKey());
//                ps.setInt(2, s.getValue().getRevenue());
//                ps.setDate(3, (Date) s.getValue().getTimeStamp());
//                ps.setBoolean(4, (s.getValue().isFlag()));
//                ps.executeUpdate();
//                updateDrone(s.getValue());
//                updateOrder(s.getValue());
//            }
//
//        }
//    }

    public void upsertCustomer(TreeMap<String,Customer> customers) throws SQLException {
        for(Map.Entry<String,Customer> c: customers.entrySet()){
            String insert = "REPLACE into customers (customerID, firstName, lastName, phoneNumber," +
                    " rating,credits, remainingCredit,timeStamp,flag ) values(?,?,?,?,?,?,?,?,?)" ;
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setString(1,c.getKey());
            ps.setString(2,c.getValue().getFirstName());
            ps.setString(3,c.getValue().getLastName());
            ps.setString(4,c.getValue().getPhoneNumber());
            ps.setInt(5, (c.getValue().getRating()));
            ps.setInt(6, (c.getValue().getCredits()));
            ps.setInt(7, c.getValue().getRemainingCredits());
            ps.setDate(8, (Date) c.getValue().getTimeStamp());
            ps.setBoolean(9, (c.getValue().isFlag()));
            ps.executeUpdate();
        }
    }

    public void updatePilot(ArrayList<Pilot> pilots) throws SQLException {
        Statement state = con.createStatement();
        for(Pilot p: pilots){
            String sql = "select * from pilots where accountID =" + p.getAccountID();
            ResultSet rs = state.executeQuery(sql);
            if(rs.next()){
                //update
                String update = "update Pilot set expcLevel = ?, droneID = ? ," +
                        "flag = ? where accountID = " + p.getAccountID();
                PreparedStatement ps = con.prepareStatement(update);

                ps.setInt(1, (p.getExpcLevel()));
                ps.setString(1, (p.getDrone().getId()));
                ps.setDate(2, (Date) p.getTimeStamp());
                ps.setBoolean(3, (p.isFlag()));
                ps.executeUpdate();
            } else {
                //insert
                String insert = "insert into Pilot (accountid, firstName,lastName,phoneNumber,taxID," +
                                "licenseID, expcLevel, droneID, timeStamp,flag ) values(?,?,?,?,?,?,?,?,?,?)" ;
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setString(1,p.getAccountID());
                ps.setString(2,p.getFirstName());
                ps.setString(3,p.getLastName());
                ps.setString(4,p.getPhoneNumber());
                ps.setString(5,p.getTaxID());
                ps.setString(6,p.getLicenseID());
                ps.setInt(7, p.getExpcLevel());
                ps.setString(8,(p.getDrone() == null ? null: p.getDrone().getId()));
                ps.setDate(9, (Date) p.getTimeStamp());
                ps.setBoolean(10, (p.isFlag()));
                ps.executeUpdate();
            }
        }
    }

    public void updateDrone(Store s) throws SQLException {
        Statement state = con.createStatement();
        for(Drone d: s.getDrones()){
            String sql = "select * from Drones where droneID =" + d.getId();
            ResultSet rs = state.executeQuery(sql);
            if(rs.next()){
                //update
                String update = "update Drones set storeName = ?, remainingCap = ? , " +
                        "numsOrders = ?, remainingFuel = ?, pilotID = ?, timestamp = ?," +
                        " flag = ? where storeName = " + d.getId();
                PreparedStatement ps = con.prepareStatement(update);
                ps.setString(1,d.getStoreName());
                ps.setInt(2,d.getRemainingCap());
                ps.setInt(3,d.getNumOrders());
                ps.setInt(4, d.getRemainFuel());
                ps.setString(5,(d.getPilot() == null ? null: d.getPilot().getAccountID()));
                ps.setDate(6, (Date) d.getTimeStamp());
                ps.setBoolean(3, (d.isFlag()));
                ps.executeUpdate();
            } else {
                //insert
                String insert = "insert into Stores (storeName, revenue,timeStamp,flag ) values(?,?,?,?)" ;
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setString(1,d.getStoreName());
                ps.setInt(2,d.getRemainingCap());
                ps.setInt(3,d.getNumOrders());
                ps.setInt(4, d.getRemainFuel());
                ps.executeUpdate();
            }
        }
    }

    public void updateOrder(Store s) throws SQLException {
        Statement state = con.createStatement();
        for(Order o: s.getOrders()){
            String sql = "select * from Order where orderID =" + o.getOrderId();
            ResultSet rs = state.executeQuery(sql);
            if(rs.next()){
                //update
                String update = "update Orders set totalCost = ? , " +
                        "totalWeight = ?, status = ?, timestamp = ?," +
                        " flag = ? where orderID = " +o.getOrderId();
                PreparedStatement ps = con.prepareStatement(update);
                ps.setInt(1,o.getTotalcost());
                ps.setInt(2,o.getTotalweight());
                ps.setString(3,o.getStatus());
                ps.setDate(4, (Date) o.getTimeStamp());
                ps.setBoolean(5, (o.isFlag()));
                ps.executeUpdate();
            } else {
                //insert
                String insert = "insert into Orders (storeName, orderID, droneID,totalCost, totalWeight, customerID," +
                        "status,  timeStamp, flag ) values(?,?,?,?,?,?,?,?,?)" ;
                PreparedStatement ps = con.prepareStatement(insert);

                ps.setString(1,s.getName());
                ps.setString(2,o.getOrderId());
                ps.setString(3,o.getDrone().getId());
                ps.setInt(4,o.getTotalcost());
                ps.setInt(5,o.getTotalweight());
                ps.setString(6,o.getCustomer().getAccount());
                ps.setString(7,o.getStatus());
                ps.setDate(8, (Date) o.getTimeStamp());
                ps.setBoolean(9, (o.isFlag()));
                ps.executeUpdate();
            }
        }
    }




}
