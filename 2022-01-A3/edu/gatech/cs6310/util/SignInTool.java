package edu.gatech.cs6310.util;

import java.sql.*;

public class SignInTool {
    private Connection con;

    public SignInTool(Connection con) {
        this.con = con;
    }

    public void insertUser(String username, String password) throws SQLException {
        Statement state = con.createStatement();
        String sql = "insert into Users (username, password) values(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();
    }

    public boolean isUniqueUsername(String username) throws SQLException {
        Statement state = con.createStatement();
        String sql = "select * from Users where username = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean signInUser(String username, String password) throws SQLException {
        Statement state = con.createStatement();
        String sql = "select * from Users where username = ? and password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }


}