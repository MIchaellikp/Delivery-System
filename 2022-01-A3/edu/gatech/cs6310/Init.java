package edu.gatech.cs6310;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

public class Init {
    /* connection
    1. 执行语句 创建Class

     */
    private TreeMap<String,Customer> customers = new TreeMap<String, Customer>();
    private Connection con;
    public Init(Connection con){
        this.con = con;
    }

    public TreeMap getCustomers(){
        Statement state = null;
        try {
            state = con.createStatement();
            String sql = "select * from customers";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()){
                 String customerID = rs.getString("customerID");
                 String firstName =rs.getString("firstName");
                 String lastName = rs.getString("lastnName");
                 String phoneNumber = rs.getString("phoneNumber");
                 int rating = Integer.getInteger(rs.getString("rating")) ;
                 int credits = Integer.getInteger(rs.getString("credits"));
                 Customer c = new Customer(customerID, firstName, lastName, phoneNumber,rating, credits);
                 customers.put(customerID,c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
