/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phudds27
 */
public class LoginThreadService extends Thread {

    BufferedReader brin = null;
    PrintWriter pw = null;
    public LoginThreadService(BufferedReader br,PrintWriter p) {
        brin = br;
        pw =p;
    }

    public void run() {
        
        try {
            String usrname = brin.readLine();
            String pwd = brin.readLine();
            int status = new LoginService().authenticateUser(usrname, pwd);
            if (status == 1) {
                int logstatus;
                logstatus = new LogService().logSignIn(usrname, new Time(Calendar.getInstance().getTime().getTime()));
                if (logstatus>-1) {
                    pw.println(logstatus);
                } else {
                    pw.println("0");
                }
            } else {
                pw.println("-1");
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginThreadService.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
