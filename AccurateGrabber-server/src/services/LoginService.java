/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author kranti
 */
public class LoginService {

    public int authenticateUser(String un, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionService().connect();
            pstmt = conn.prepareStatement("Select * from usermaster where username=?");
            pstmt.setString(1, un);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String p = rs.getString("password");
                if (p.equals(pwd)) {
                    return 1;
                }
                else
                    return 0;
            } else {
                return 0;
            }

        } catch (Exception e) {
            System.out.println("authenicateUser():" + e);

        }
        return 0;
    }

    

   

}
