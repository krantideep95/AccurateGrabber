/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author kranti
 */
public class LogService {

    public int logSignIn(String usr, Time time) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionService.connect();
            pstmt = conn.prepareStatement("insert into userlog(username, login) values( ?,?)");
            pstmt.setString(1, usr);
            pstmt.setTimestamp(2, new Timestamp(time.getTime()));
            int i = pstmt.executeUpdate();
            if (i > 0) {
                pstmt = conn.prepareStatement("Select max(actid) from userlog");
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
                int id=rs.getInt("max(actid)");
                return id;
            }
            } else {
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;

    }
    public boolean logSignOut( int actid,Timestamp timein ,Time timeout){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionService.connect();
            pstmt = conn.prepareStatement("update userlog set login=? , logout=? where actid = ?");
            pstmt.setInt(3, actid);
            pstmt.setTimestamp(1,timein);//new Timestamp( new Time( Calendar.getInstance().getTime().getTime()).getTime())
            pstmt.setTimestamp(2,new Timestamp(timeout.getTime()));
            int i = pstmt.executeUpdate();
            if (i > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}
