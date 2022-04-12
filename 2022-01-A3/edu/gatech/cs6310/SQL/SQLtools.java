package edu.gatech.cs6310.SQL;

import java.sql.*;

public class SQLtools {
    private Connection con;
    public SQLtools (Connection con){
        this.con = con;
    }

//    public boolean findbyID(String tableName, String columnsName, String id){
//        try {
//            Statement state = con.createStatement();
//            String sql = "Select * from " + tableName + " where " + columnsName + " = " + id;
//            ResultSet rs = state.executeQuery(sql);
//            if(rs.next()){
//                return true;
//            }else{
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public boolean findbyId(String tableName, String[] columnsName, String[] columnsValue ){
        try {
            int i = 0;
            Statement state = con.createStatement();
            String sql = "Select * from " + tableName + " where ";
            for( i=0 ; i< columnsName.length - 1; i++ ) {
                sql += columnsName[i] + " = " + columnsValue[i] + " and ";
            }
            sql += columnsName[i] + " = " + columnsValue[i];
            ResultSet rs = state.executeQuery(sql);
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertValue (String tableName, String[] columnsName, String[] columnsValue){
        try {
            int i = 0;
            Statement state = con.createStatement();
            String sql = "INSERT INTO " + tableName + " ( ";
            String n = String.join(",", columnsName);
            sql += n;
            sql += ") VALUES (";
            String v = String.join(",", columnsValue);
            sql += v + ")";
            ResultSet rs = state.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateValue (String tableName, String[] columnsName, String[] columnsValue,
                             String [] updateName, String[] updateValue){
        try {
            Statement state = con.createStatement();
            // TODO: 4/11/2022
            //UPDATE [LOW_PRIORITY] [IGNORE] table_name
            //SET
            //    column_name1 = expr1,
            //    column_name2 = expr2,
            //    ...
            //[WHERE
            //    condition];
//            String sql = "UPDATE " + tableName + " SET ( ";
//            String n = String.join(",", columnsName);
//            sql += n;
//            sql += ") VALUES (";
//            String v = String.join(",", columnsValue);
//            sql += v + ")";
            ResultSet rs = state.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

}
