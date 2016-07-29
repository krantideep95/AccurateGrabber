/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Time;

/**
 *
 * @author kranti
 */
public class ActivityBean {
    String username;
    Time login;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Time getLogin() {
        return login;
    }

    public void setLogin(Time login) {
        this.login = login;
    }

    public Time getLogout() {
        return logout;
    }

    public void setLogout(Time logout) {
        this.logout = logout;
    }
    Time logout;
    
}
