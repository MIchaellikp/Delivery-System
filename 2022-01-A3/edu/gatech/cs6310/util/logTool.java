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
                 sql = "select * from System_Log where username = " + username;
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

//
//    public boolean findbyId(String tableName, String[] columnsName, String[] columnsValue ){
//        try {
//            int i = 0;
//            Statement state = con.createStatement();
//            String sql = "Select * from " + tableName + " where ";
//            for( i=0 ; i< columnsName.length - 1; i++ ) {
//                sql += columnsName[i] + " = " + columnsValue[i] + " and ";
//            }
//            sql += columnsName[i] + " = " + columnsValue[i];
//            ResultSet rs = state.executeQuery(sql);
//            if(rs.next()){
//                return true;
//            }else{
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public void insertValue (String tableName, String[] columnsName, String[] columnsValue){
//        try {
//            int i = 0;
//            Statement state = con.createStatement();
//            String sql = "INSERT INTO " + tableName + " ( ";
//            String n = String.join(",", columnsName);
//            sql += n;
//            sql += ") VALUES (";
//            String v = String.join(",", columnsValue);
//            sql += v + ")";
//            ResultSet rs = state.executeQuery(sql);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void updateValue (String tableName, String[] columnsName, String[] columnsValue,
//                             String [] updateName, String[] updateValue){
//        try {
//            Statement state = con.createStatement();
//            // TODO: 4/11/2022
//            //UPDATE [LOW_PRIORITY] [IGNORE] table_name
//            //SET
//            //    column_name1 = expr1,
//            //    column_name2 = expr2,
//            //    ...
//            //[WHERE
//            //    condition];
////            String sql = "UPDATE " + tableName + " SET ( ";
////            String n = String.join(",", columnsName);
////            sql += n;
////            sql += ") VALUES (";
////            String v = String.join(",", columnsValue);
////            sql += v + ")";
//            ResultSet rs = state.executeQuery(sql);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }




