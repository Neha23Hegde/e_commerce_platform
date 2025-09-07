package com.ecommerce.dao;
import com.ecommerce.util.*;
import com.ecommerce.dto.Address;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {

   //Insert a new address record into the database
    public boolean insertAddress(Address address) {
        String sql = "INSERT INTO addresses (user_id, address_line, city, state, country, pincode) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        	// Set parameters for placeholders in SQL
            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getAddressLine());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getCountry());
            ps.setString(6, address.getPincode());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

 // Fetch a single address by its unique address_id
    public Address getAddressById(int addressId) {
        String sql = "SELECT * FROM addresses WHERE address_id = ?";
        try (Connection conn = DbConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, addressId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Address addr = new Address();
                addr.setAddressId(rs.getInt("address_id"));
                addr.setUserId(rs.getInt("user_id"));
                addr.setAddressLine(rs.getString("address_line"));
                addr.setCity(rs.getString("city"));
                addr.setState(rs.getString("state"));
                addr.setCountry(rs.getString("country"));
                addr.setPincode(rs.getString("pincode"));
                return addr;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Fetch all addresses belonging to a specific user 
    public List<Address> getAddressesByUserId(int userId) {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM addresses WHERE user_id = ?";

        try (Connection conn = DbConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Address addr = new Address();
                addr.setAddressId(rs.getInt("address_id"));
                addr.setUserId(rs.getInt("user_id"));
                addr.setAddressLine(rs.getString("address_line"));
                addr.setCity(rs.getString("city"));
                addr.setState(rs.getString("state"));
                addr.setCountry(rs.getString("country"));
                addr.setPincode(rs.getString("pincode"));
                list.add(addr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
