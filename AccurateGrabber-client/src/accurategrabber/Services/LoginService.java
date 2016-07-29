/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accurategrabber.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.util.Calendar;

/**
 *
 * @author kranti
 */
public class LoginService {

     Socket skt = null;
    String usr, pwd, mode;
     private InputStreamReader isin;
     private BufferedReader brin;

     public int authenticateUser(String usr,String pwd,String mode,int actid) throws IOException {
         skt = new Socket("localhost",12349);
         System.out.println("socket connected");
         isin = new InputStreamReader(skt.getInputStream());
        brin = new BufferedReader(isin);
        PrintWriter pw = new PrintWriter(skt.getOutputStream(), true);
        pw.println(mode);
        pw.println(usr);
        pw.println(pwd);
        if(mode.equalsIgnoreCase("logout")){
            pw.println(actid);
            Long t = new Time(Calendar.getInstance().getTime().getTime()).getTime();
            String time= String.valueOf(t);
            System.out.println("time client"+ time);
            pw.println(time);
        }
       
         
        String result=brin.readLine();
        int re = Integer.parseInt(result);
        return re;
    }
}
