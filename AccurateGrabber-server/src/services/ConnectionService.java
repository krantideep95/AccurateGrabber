/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author kranti
 */
public class ConnectionService {
	pass="";
     public static Connection connect() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
             Class driver_class = Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql:///userlogger";
            Driver driver = (Driver) driver_class.newInstance();
            DriverManager.registerDriver(driver);
            conn = DriverManager.getConnection(url, "root", pass);
        } catch (Exception e) {
            System.out.println(e);

        }

        return conn;
    }
    
}
