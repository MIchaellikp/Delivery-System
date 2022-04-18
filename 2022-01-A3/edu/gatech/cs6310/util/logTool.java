package edu.gatech.cs6310.util;
import java.util.Date;
import java.sql.*;

public class logTool {
    private Connection con;
    public logTool (Connection con){
        this.con = con;
    }

    public void insertLog(String username, Date timeStamp, String command, String result) throws SQLException {
        Statement state = con.createStatement();
        String sql = "insert into System_Log (username, commandLine, resultMessage , timeStamp) values(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,command);
        ps.setString(3,result);
        ps.setTimestamp(4, new java.sql.Timestamp(timeStamp.getTime()));
        ps.executeUpdate();

    }

    public String printLog(String username ){
        try {
            Statement state = con.createStatement();
            String sql;
            if(username.equals("Security_Admin")){
                 sql = "select * from System_Log";
            }else{
                 sql = "select * from System_Log where username = '" + username+"'";
            }

            ResultSet rs = state.executeQuery(sql);

            while(rs.next()){
                System.out.println(rs.getString("username")+ " / " +
                                rs.getString("commandLine") + " / " +
                                rs.getString("resultMessage")+ " / " +
                                rs.getString("timeStamp"));
            }
            return "OK:display_completed";
        }catch (SQLException e){
            e.printStackTrace();
            return "OK:display_completed";
        }
    }
}




