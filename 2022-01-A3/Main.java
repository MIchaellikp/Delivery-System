import edu.gatech.cs6310.DeliveryService;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection con;
    //private static DriverManagerDataSource ds;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/delivery";
            String userName = "root";
            String password = "1234";
            con = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Grocery Express Delivery Service!");
        System.out.println("Please Login");
        Scanner username = new Scanner(System.in);
        System.out.println("Please Password");
        Scanner password = new Scanner(System.in);
        //find the User or password
        try {
            Statement state = con.createStatement();
            String sql = "Select * from Users where username = " + username + "and password = " + password;
            ResultSet rs = state.executeQuery(sql);
            if(rs.next()){
                //execute the system
            }else{
                //recall the Name & Password
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        DeliveryService simulator = new DeliveryService();
        simulator.commandLoop();
    }
}
