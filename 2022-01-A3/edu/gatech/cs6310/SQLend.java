package edu.gatech.cs6310;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

public class SQLend {

    private Connection con;

    public SQLend(Connection con) {
        this.con = con;
    }

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
                ps.setDate(2, (Date) s.getValue().getDataStamp());
                ps.setBoolean(3, (s.getValue().isFlag()));
                ps.executeUpdate();
            } else {
                //insert
                String insert = "insert into Stores (storeName, revenue,timeStamp,flag ) values(?,?,?,?)" ;
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setString(1,s.getKey());
                ps.setInt(2, s.getValue().getRevenue());
                ps.setDate(3, (Date) s.getValue().getDataStamp());
                ps.setBoolean(4, (s.getValue().isFlag()));
                ps.executeUpdate();
            }
        }
    }

    public void updateCustomer(TreeMap<String,Customer> customers) throws SQLException {
        /*if custmersid 在sql update
            not in SQL 的话 insert
         */
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
                ps.setDate(4, (Date) c.getValue().getDataStamp());
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
                ps.setDate(8, (Date) c.getValue().getDataStamp());
                ps.setBoolean(9, (c.getValue().isFlag()));
                ps.executeUpdate();
            }

        }


    }




}
