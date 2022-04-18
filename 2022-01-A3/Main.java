import edu.gatech.cs6310.DeliveryService;
import edu.gatech.cs6310.util.Init;
import edu.gatech.cs6310.util.SignInTool;
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
    private static String signUp() throws SQLException, IOException {
        SignInTool signintool = new SignInTool(con);
        Scanner scanner = new Scanner(System.in);
        try{
            while(true) {
                System.out.println("Please enter username");
                String username = scanner.nextLine();
                System.out.println("Please enter password");
                String password1 = scanner.nextLine();
                System.out.println("Please enter password again (Note: it must matches with password)");
                String password2 = scanner.nextLine();
                if (password1.equals(password2) ){
                    if (signintool.isUniqueUsername(username)) {
                        signintool.insertUser(username, password1);
                        scanner.close();
                        return username;
                    }else{
                        System.out.println("Username already exists.");
                    }
                } else {
                    System.out.println("Passwords do not match.");
                }
                System.out.println("Do you want to quit? (Y/N)");
                String toQuit = scanner.nextLine();
                if(toQuit.equals("Y")){
                    scanner.close();
                    return null;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }
    private static String logIn() throws SQLException, IOException {
        SignInTool signintool = new SignInTool(con);
        Scanner scanner = new Scanner(System.in);
        try{
            while(true) {
                System.out.println("Please enter username");
                String username = scanner.nextLine();
                System.out.println("Please enter password");
                String password = scanner.nextLine();
                if (signintool.signInUser(username, password)) {
                    scanner.close();
                    return username;
                }else{
                    System.out.println("Username or password is wrong.");
                }

                System.out.println("Do you want to quit? (Y/N)");
                String toQuit = scanner.nextLine();
                if(toQuit.equals("Y")){
                    scanner.close();
                    return null;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws ParseException, SQLException, IOException {
        initConenction();
        initDatabase();

//todo Signup and Signin
        System.out.println("Welcome to the Grocery Express Delivery Service!");
        System.out.println("Please type L to Log in or type S to Sign up or E to Exit:");
        Scanner commandLineInput = new Scanner(System.in);
        String username = null;
        while(username == null) {
            String wholeInputLine = commandLineInput.nextLine();
            if (wholeInputLine.equals("S")) {
                System.out.println("Before signup");
                username = signUp();
                System.out.println("After signup");
                break;
            } else if (wholeInputLine.equals("L")) {
                System.out.println("Before login");
                username = logIn();
            } else if (wholeInputLine.equals("E")) {
                System.out.println("Before exit");
                return;
            } else {
                System.out.println("Please type L to Log in or type S to Sign up or E to Exit:");
            }
        }
        commandLineInput.close();
        // add into userName
        DeliveryService simulator = new DeliveryService();
        Init data = new Init(con);
        simulator.commandLoop(username, data, con);
    }
}
