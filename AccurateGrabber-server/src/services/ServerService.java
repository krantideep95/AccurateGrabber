/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kranti
 */
public class ServerService extends Thread {

    boolean running = true;
    int PORT = 12345;
    protected ServerSocket mServerSocket = null;
    InputStreamReader isin = null;
    BufferedReader brin = null;

    public ServerService() {

    }

    public void run() {
        try {
            mServerSocket = new ServerSocket(12349);
            System.out.println("server started");
            System.out.println("server add"+ mServerSocket.getLocalSocketAddress() + " port "+ mServerSocket.getLocalPort());
            while (running) {

                Socket skt = mServerSocket.accept();
                isin = new InputStreamReader(skt.getInputStream());
                brin = new BufferedReader(isin);
                PrintWriter pw = new PrintWriter(skt.getOutputStream(), true);
                String input = brin.readLine();
                if (input.equalsIgnoreCase("login")) {
                    new LoginThreadService(brin, pw).start();
                } else if (input.equalsIgnoreCase("logout")) {
                    new LogoutThreadService(skt, brin, pw).start();
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                mServerSocket.close();
                System.out.println("server socket closed");
            } catch (IOException ex) {
                Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void terminate() {
        try {
            running = false;
            mServerSocket.close();
             System.out.println("server socket closed");
            // use terminate and then join the process.
            // threadclass.terminate();threadclass.join();
        } catch (IOException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
