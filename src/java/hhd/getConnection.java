/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhd;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author asus
 */
public class getConnection {
    static Connection con = null;
    public static Connection connect(){
        try{
            String url = "jdbc:sqlserver://localhost;databaseName=a18138";
            String user = "sa";
            String password = "nhu0tren";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url,user,password);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return con;
    }
}
