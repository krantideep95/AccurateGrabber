/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kranti
 */
class LogoutThreadService extends Thread {

    BufferedReader brin = null;
    PrintWriter pw = null;
    Socket skt = null;
    String usrname = null;
    Time time = null;
    InputStream in = null;
    OutputStream out = null;

    public LogoutThreadService(Socket skt, BufferedReader br, PrintWriter p) {
        this.skt = skt;
        brin = br;
        pw = p;
    }

    public void run() {
        try {
            usrname = brin.readLine();
            String pwd = brin.readLine();
            int actid=Integer.parseInt(brin.readLine());
            String timestring= brin.readLine();
            Time timeout= new Time(Long.parseLong(timestring));
            int status = new LoginService().authenticateUser(usrname, pwd);
            if (status == 1) {
                boolean logstatus;
                //time = new Time(Calendar.getInstance().getTime().getTime());
               
            Connection conn = new ConnectionService().connect();
                    PreparedStatement pstmt = conn.prepareStatement("Select * from userlog where actid=?");
            pstmt.setInt(1, actid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Timestamp timein = rs.getTimestamp("login");
                logstatus = new LogService().logSignOut(actid,timein ,timeout);
                if (logstatus) {
                    pw.println("1");
                    if(true){ //handleZip()
                      pw.println("0");
                    }else{
                        pw.println("-1");
                    }
                } else {
                    pw.println("0");
                }
            } else {
                pw.println("-1");
            }
        } 
    }   catch (SQLException ex) {
            Logger.getLogger(LogoutThreadService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LogoutThreadService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean handleZip() {
        try {
            in = skt.getInputStream();
            String filepath="/home/kranti/data/"+usrname+"/"+time.toString()+".zip";
            out = new FileOutputStream(filepath);
            byte[] bytes = new byte[8192];
            
            
            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LogoutThreadService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
