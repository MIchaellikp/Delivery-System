package edu.gatech.cs6310;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SQLend {

    private Connection con;

    public SQLend(Connection con) { this.con = con;}

    public void updateStore(TreeMap<String, Store> stores) throws SQLException {
        Statement state = con.createStatement();
        for(Map.Entry<String,Store> s: stores.entrySet()){
            String sql = "select * from stores where storeName =" + s.getKey();
            ResultSet rs = state.executeQuery(sql);
            if(rs.next()){
                //update
                String update = "update Stores set revenue = ?, timestamp = ? ," +
                        "flag = ? where storeName = " + s.getKey();
                PreparedStatement ps = con.prepareStatement(update);

                ps.setInt(1, (s.getValue().getRevenue()));
                ps.setDate(2, (Date) s.getValue().getTimeStamp());
                ps.setBoolean(3, (s.getValue().isFlag()));
                ps.executeUpdate();
                updateDrone(s.getValue());
            } else {
                //insert
                String insert = "insert into Stores (storeName, revenue,timeStamp,flag ) values(?,?,?,?)" ;
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setString(1,s.getKey());
                ps.setInt(2, s.getValue().getRevenue());
                ps.setDate(3, (Date) s.getValue().getTimeStamp());
                ps.setBoolean(4, (s.getValue().isFlag()));
                ps.executeUpdate();
                updateDrone(s.getValue());
            }

        }
    }

    public void updateCustomer(TreeMap<String,Customer> customers) throws SQLException {
        Statement state = null;
        for(Map.Entry<String,Customer> c: customers.entrySet()){
            state = con.createStatement();
            String sql = "select * from customers where customerID =" + c.getKey();
            ResultSet rs = state.executeQuery(sql);
            if(rs.next()){
                //update
                String update = "update customers set rating = ?, credits = ?, remainingCredit = ? , timeStamp = ?, " +
                                "flag = ? where customerID = " + c.getKey();
                PreparedStatement ps = con.prepareStatement(update);

                ps.setInt(1, (c.getValue().getRating()));
                ps.setInt(2, (c.getValue().getCredits()));
                ps.setInt(3, c.getValue().getRemainingCredits());
                ps.setDate(4, (Date) c.getValue().getTimeStamp());
                ps.setBoolean(5, (c.getValue().isFlag()));
                ps.executeUpdate();
            } else {
                //insert
                String insert = "insert into customers (customerID, firstName, lastName, phoneNumber," +
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




}
