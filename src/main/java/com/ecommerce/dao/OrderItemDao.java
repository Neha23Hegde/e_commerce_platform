package com.ecommerce.dao;
import com.ecommerce.dto.OrderItem;
import com.ecommerce.util.DbConnection;
import java.sql.*;
public class OrderItemDao {
	//save data into order_items
	    public void saveOrderItem(OrderItem item) {
	    	 //insert an order item into the 'order_items' table
	        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

	        try (Connection conn = DbConnection.getConnect();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, item.getOrderId());
	            ps.setInt(2, item.getProductId());
	            ps.setInt(3, item.getQuantity());
	            ps.setDouble(4, item.getPrice());

	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}



