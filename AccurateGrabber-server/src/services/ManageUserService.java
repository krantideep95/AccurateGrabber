/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.UserBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author kranti
 */
public class ManageUserService {
      public int getmaxUserid()
    {
          Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionService.connect();
            pstmt = conn.prepareStatement("Select max(userid) from usermaster");
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                int id=rs.getInt("max(userid)");
                return id;
            }
            
        } catch (Exception e) {
        }
       return 0;
    }
    public ArrayList getAllUsers()
    {
       Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList al=new ArrayList();
        try {
            conn = ConnectionService.connect();
            pstmt = conn.prepareStatement("Select * from usermaster");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                UserBean objbean=new UserBean();
                objbean.setUserid(rs.getInt("userid"));
                objbean.setUsername(rs.getString("username"));
                objbean.setPassword(rs.getString("password"));
                
                objbean.setName(rs.getString("name"));
                objbean.setContactno(rs.getString("contact"));
                objbean.setAddress(rs.getString("address"));
                objbean.setEmail(rs.getString("email"));
                
                al.add(objbean);
            }
        }
        catch (Exception e)
        {
               System.out.println("manageuserservices"+e);
        }
        return al;
    }
    public boolean addUser(UserBean objbean)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = ConnectionService.connect();
             pstmt=conn.prepareStatement("insert into usermaster values( ?,?,?,?,?,?,?)");
			pstmt.setInt(1,objbean.getUserid());		// first specify position of ? to be replaced then value
			pstmt.setString(2,objbean.getUsername());
			pstmt.setString(3,objbean.getPassword());
                        
                        pstmt.setString(4,objbean.getName());
                        pstmt.setString(5,objbean.getContactno());
                        pstmt.setString(6,objbean.getAddress());
                        pstmt.setString(7,objbean.getEmail());
                        
                        int i= pstmt.executeUpdate();
			if(i>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
    	   	return false;
        }
     public boolean updateUser(UserBean objbean)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = ConnectionService.connect();
             pstmt=conn.prepareStatement("update usermaster set username=?,password=?,name=?,contact=?,address=?,email=? where userid=?");
			pstmt.setInt(7,objbean.getUserid());		// first specify position of ? to be replaced then value
			pstmt.setString(1,objbean.getUsername());
			pstmt.setString(2,objbean.getPassword());
                        
                        pstmt.setString(3,objbean.getName());
                        pstmt.setString(4,objbean.getContactno());
                        pstmt.setString(5,objbean.getAddress());
                        pstmt.setString(6,objbean.getEmail());
                        
			int i= pstmt.executeUpdate();
			if(i>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
    	   	return false;
        }
    
}
