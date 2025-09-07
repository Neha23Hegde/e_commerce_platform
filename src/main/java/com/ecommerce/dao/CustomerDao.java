package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.dto.Customer;
import com.ecommerce.util.DbConnection;

public class CustomerDao {
	 static Connection con = DbConnection.getConnect();
	 //check email
	 public boolean saveCustomer(Customer c){
		    String check = "SELECT COUNT(*) FROM users WHERE email = ?";
		    try (PreparedStatement ps = con.prepareStatement(check)) {
		        ps.setString(1, c.getEmail());
		        ResultSet rs = ps.executeQuery();
		        if (rs.next() && rs.getInt(1) > 0) {
		            System.out.println("Email already exists: " + c.getEmail());
		            return false; 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    //insert new users
		    String save="INSERT INTO users(name, email, mobile, password) VALUES(?,?,?,?)";
		    try (PreparedStatement pstmt=con.prepareStatement(save)) {
		        pstmt.setString(1,c.getName());
		        pstmt.setString(2,c.getEmail());
		        pstmt.setLong(3,c.getMobile());
		        pstmt.setString(4,c.getPassword());
		        return pstmt.executeUpdate() > 0;
		    } catch (SQLException e1) {
		        e1.printStackTrace();
		    }
		    return false;
		}

	  //Login using Email and Password
	  public static Customer login(String email, String pass) {
	        String sqry = "SELECT * FROM users WHERE email = ? AND password = ?";
	        try (PreparedStatement ps = con.prepareStatement(sqry)) {
	            ps.setString(1, email);
	            ps.setString(2, pass);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	            	  Customer c = new Customer();
	            	  c.setUserId(rs.getInt("user_id"));
	                  c.setName(rs.getString("name"));
	                  c.setEmail(rs.getString("email"));
	                  c.setMobile(rs.getLong("mobile"));
	                  c.setPassword(rs.getString("password"));
	                  c.setRole(rs.getString("role"));
	                  return c;
	                  
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	  //Update the profile
	  public static boolean updateProfile(int userId, String email, long mobile) {
		    try (Connection con = DbConnection.getConnect()) {
		        String sql = "UPDATE users SET email=?, mobile=? WHERE user_id=?";
		        PreparedStatement ps = con.prepareStatement(sql);
		        ps.setString(1, email);
		        ps.setLong(2, mobile);
		        ps.setInt(3, userId);
		        return ps.executeUpdate() > 0;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return false;
		}
	  //retrive Customer Details using ID
	  public Customer getCustomerById(int userId) {
		    String sql = "SELECT * FROM users WHERE user_id = ?";
		    try (Connection con = DbConnection.getConnect();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setInt(1, userId);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            Customer c = new Customer();
		            c.setUserId(rs.getInt("user_id"));
		            c.setName(rs.getString("name"));
		            c.setEmail(rs.getString("email"));
		            c.setMobile(rs.getLong("mobile"));
		            c.setPassword(rs.getString("password"));
		            c.setRole(rs.getString("role"));
		            return c;
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null;
		}



}
