import edu.gatech.cs6310.DeliveryService;
import edu.gatech.cs6310.util.Init;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    private static Connection con;
    //private static DriverManagerDataSource ds;

    private static void initConenction() throws SQLException{
        final String url = "jdbc:mysql://localhost:3306/delivery?allowMultiQueries=true";
        final String userName = "demo_java";
        final String password = "1234";
        try {
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    //todo - create tables
    private static void initDatabase() throws SQLException, IOException {
        try{
            Statement stmt = con.createStatement();
            String sqlStr = Files.readString(Paths.get("2022-01-A3/edu/gatech/cs6310/SQL/DeliverySystem.sql"));
            stmt.execute(sqlStr);
            System.out.println("Database initialization completed!");
        } catch (IOException e) {
            System.out.format("I/O error: %s%n", e);
            throw e;
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }

    }

    public static void main(String[] args) throws ParseException, SQLException, IOException {
        initConenction();
        initDatabase();

//todo Signup and Signin
        System.out.println("Welcome to the Grocery Express Delivery Service!");
        System.out.println("Please Login");
        //Scanner username = new Scanner(System.in);
        System.out.println("Please Password");
        //Scanner password = new Scanner(System.in);
        String username = "a";
        String password = "a";
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
