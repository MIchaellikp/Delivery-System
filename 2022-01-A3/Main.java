import edu.gatech.cs6310.DeliveryService;
import edu.gatech.cs6310.Init;
import edu.gatech.cs6310.SQL.logTool;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    private static Connection con;
    //private static DriverManagerDataSource ds;

    private static void initConenction() throws SQLException{
        //todo
        // Run SQL Script in JAVA: http://hulen.com/run-a-mysql-script-using-java/

        final String url = "jdbc:mysql://localhost:3306/delivery";
        final String userName = "demo_java";
        final String password = "1234";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection established!");
//        } catch (ClassNotFoundException | SQLException e) {
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    //todo - create tables

    public static void main(String[] args) throws ParseException, SQLException, ClassNotFoundException {
        initConenction();

        System.out.println("Welcome to the Grocery Express Delivery Service!");
        System.out.println("Please Login");
        Scanner username = new Scanner(System.in);
        System.out.println("Please Password");
        Scanner password = new Scanner(System.in);
        //find the User or password
//        try {
//            SQLtools st = new SQLtools(con);
//
//            Statement state = con.createStatement();
//            String sql = "Select * from Users where username = " + username + "and password = " + password;
//            ResultSet rs = state.executeQuery(sql);
//            if(rs.next()){
//                //execute the system
//            }else{
//                //recall the Name & Password
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // add into userName
        DeliveryService simulator = new DeliveryService();
        Init data = new Init(con);
        simulator.commandLoop(String.valueOf(username), data, con);
    }
}
