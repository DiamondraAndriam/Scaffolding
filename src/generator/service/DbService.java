/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generator.service;

import generator.utils.ObjectUtility;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Mamisoa
 */
public class DbService {
    
    public static String formatString(String column){
        String[] splited = column.split("_");
        if(splited.length <2)
            return column;
        String res = splited[0];
        for(int i = 1; i < splited.length; i++){
            res += ObjectUtility.capitalize(splited[i]);
        }
        return res;
    }
    public static List<String> getAllTables(Connection con) throws Exception{
        List<String> lst = new ArrayList<>();
        DatabaseMetaData meta = (DatabaseMetaData) con.getMetaData();
        ResultSet rs = meta.getTables(null, null,  null, new String[] {
         "TABLE"
      });
        while (rs.next()) {
          lst.add(rs.getString(3));
        }
        return lst;
    }
    public static String[] getAllTablesArrays(Connection con) throws Exception{
        List<String> lst = getAllTables(con);
        String[] array= new String[lst.size()];
        for(int i = 0; i < lst.size(); i++){
            array[i] = lst.get(i);
        }
        return array;
    }
    
    
    public static HashMap<String, String> getColumnNameAndType(Connection con, String tableName) throws Exception{
        HashMap<String, String> map = new HashMap<>();
        
        String query = "SELECT * FROM "+tableName;
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        for(int i = 1; i <= count; i++){
            String key = rsmd.getColumnName(i);
            String value = rsmd.getColumnClassName(i);
            map.put(key, value);
        }
        return map;
    }
    
    public static HashMap<String, String> getDetailsColumn(Connection con, String tableName) throws Exception{
        HashMap<String, String> map = new HashMap<>();
        
        String query = "SELECT * FROM "+tableName;
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        for(int i = 1; i <= count; i++){
            String key = rsmd.getColumnClassName(i);
            String column = rsmd.getColumnName(i);
            map.put(column, key);
        }
        return map;
    }
    
    public static void getTableConstraints(Connection con, String tableName) throws Exception{
//        HashMap<String, String> map = new HashMap<>();
        
        String query = "SELECT * FROM "+tableName;
        
        DatabaseMetaData meta = con.getMetaData();
        ResultSet tablesRs = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        ResultSet primaryKeys = meta.getPrimaryKeys(null, null, tableName);
        
        ResultSetMetaData rsmd = primaryKeys.getMetaData();
        int count  = rsmd.getColumnCount();
        
        System.out.println("--------- PRIMARY KEY ----------------");
        while(primaryKeys.next()){
            for(int i = 1; i <= count; i++){
                System.out.println(" column NAME = " + rsmd.getColumnName(i) + " column VALUES = "+primaryKeys.getObject(i) + " column TYPE = " + rsmd.getColumnClassName(i));
            }
        }
        
        ResultSet foreignKeys = meta.getExportedKeys(null, null, tableName);
        ResultSetMetaData rsmd2 = foreignKeys.getMetaData();
        
        int count2  = rsmd2.getColumnCount();
        System.out.println("--------- FOREIGN KEY ----------------");     
        System.out.println(foreignKeys.next());
//        while(foreignKeys.next()){
//            for(int i = 1; i <= count2; i++){
//                System.out.println(" column NAME = " + rsmd2.getColumnName(i) + " column VALUES = " + foreignKeys.getString(i) + " column TYPE = "+rsmd2.getColumnClassName(i));
//            }
//            System.out.println("-------------------------------------");
//        }
//        return map;
    }

}
