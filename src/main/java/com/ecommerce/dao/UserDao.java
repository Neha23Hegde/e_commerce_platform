package com.ecommerce.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import com.ecommerce.util.DbConnection;

public class UserDao {

    public boolean updateProfile(String username, String email, long mobile) {
        boolean updated = false;
        try (Connection conn = DbConnection.getConnect()) {
        	 // update user profile by username
            String sql = "UPDATE users SET email=?, mobile=? WHERE username=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setLong(2, mobile);
            pst.setString(3, username);

            updated = pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;  //  if update succeeded return true, else false
    }
}

