package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.dto.Customer;
import com.ecommerce.util.DbConnection;

public class CustomerDao {
	 static Connection con = DbConnection.getConnect();
	  public boolean saveCustomer(Customer c){
		 String save="INSERT INTO users(name, email, mobile, password) VALUES(?,?,?,?)";
		 
		try { 
		
		PreparedStatement pstmt=con.prepareStatement(save);
		pstmt.setString(1,c.getName());
		pstmt.setString(2,c.getEmail());
		pstmt.setLong(3,c.getMobile());
		pstmt.setString(4,c.getPassword());
		
			
			int count=pstmt.executeUpdate();
			if(count>0) {
				return true;
			}
			else {
				return false;
			}
		} 
		catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		return false;
		 
	 }
	  
	  public static Customer login(String email, String pass) {
	        String sqry = "SELECT * FROM users WHERE email = ? AND password = ?";
	        try (PreparedStatement ps = con.prepareStatement(sqry)) {
	            ps.setString(1, email);
	            ps.setString(2, pass);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	            	  Customer c = new Customer();
	                 
	                  c.setName(rs.getString("name"));
	                  c.setEmail(rs.getString("email"));
	                  c.setMobile(rs.getLong("mobile"));
	                  c.setPassword(rs.getString("password"));
	                  return c;
	                  
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
