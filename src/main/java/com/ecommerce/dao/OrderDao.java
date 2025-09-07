package com.ecommerce.dao;

import com.ecommerce.dto.Order;
import com.ecommerce.util.DbConnection;

import java.sql.*;
import java.util.*;

public class OrderDao {

   //insert address payment methods in orders
    public int insertOrder(Order order) {
        int generatedId = -1;

        String sql = "INSERT INTO orders(user_id, address_id, payment_method, total_amount) VALUES(?,?,?,?)";

        try (Connection con = DbConnection.getConnect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getAddressId());
            ps.setString(3, order.getPaymentMethod());
            ps.setDouble(4, order.getTotalAmount());

            int count = ps.executeUpdate();

            if (count > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    //insert order item details
    public void insertOrderItem(int orderId, int productId, int quantity, double price) {
        String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
        try (Connection con = DbConnection.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //after inserting order update the stock
    public void updateProductStock(int productId, int quantity) {
        String sql = "UPDATE products SET pquantity = pquantity - ? WHERE pid = ?";
        try (Connection con = DbConnection.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 //retrive the orders using orderId
    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection con = DbConnection.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setAddressId(rs.getInt("address_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setTotalAmount(rs.getDouble("total_amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    
    public List<Map<String, Object>> getOrderItems(int orderId) {
        List<Map<String, Object>> items = new ArrayList<>();
        
// Joining order_items with products to fetch item details and compute subtotal per item
        String sql = "SELECT p.pname, p.image_url, oi.quantity, oi.price, (oi.quantity * oi.price) AS subtotal " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.pid " +
                "WHERE oi.order_id = ?";


        try (Connection con = DbConnection.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("imageUrl", rs.getString("image_url"));
                item.put("name", rs.getString("pname"));
                item.put("quantity", rs.getInt("quantity"));
                item.put("price", rs.getDouble("price"));
                item.put("subtotal", rs.getDouble("subtotal"));
                
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
    
    //Displaying the order Details by Selecting the UserId
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_id DESC";

        try (Connection con = DbConnection.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setAddressId(rs.getInt("address_id"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getDouble("total_amount"));
               
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    //cancel Order from User Side
    
    public boolean cancelOrder(int orderId, int userId) {
        String updateOrderSql = "UPDATE orders SET status = 'Cancelled' WHERE order_id = ? AND user_id = ?";
        String getItemsSql = "SELECT product_id, quantity FROM order_items WHERE order_id = ?";
        String updateProductSql = "UPDATE products SET pquantity = pquantity + ? WHERE pid = ?";

        Connection conn = null;
        

        try {
            conn = DbConnection.getConnect();
            conn.setAutoCommit(false); // start transaction

            // Update order status
            PreparedStatement psUpdateOrder = conn.prepareStatement(updateOrderSql);
            psUpdateOrder.setInt(1, orderId);
            psUpdateOrder.setInt(2, userId);
            int rowsAffected = psUpdateOrder.executeUpdate();

            if (rowsAffected == 0) {
                conn.rollback();
                return false; // no order updated
            }

            // Get order items
            PreparedStatement psGetItems = conn.prepareStatement(getItemsSql);
            psGetItems.setInt(1, orderId);
            ResultSet rs = psGetItems.executeQuery();

            // Update product quantities
            PreparedStatement psUpdateProduct = conn.prepareStatement(updateProductSql);
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                psUpdateProduct.setInt(1, quantity); // add back canceled quantity
                psUpdateProduct.setInt(2, productId);
                psUpdateProduct.executeUpdate();
            }

            conn.commit(); // commit transaction
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // rollback on error
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /*===Admin Actions===*/
    //Display all the orders
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";

        try (Connection con = DbConnection.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setAddressId(rs.getInt("address_id"));
                order.setPaymentMethod(rs.getString("payment_method")); 
                
                order.setTotalAmount(rs.getDouble("total_amount"));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    //update the order Status
    public void updateOrderStatus(int orderId, String status) {
        try {
            Connection con = DbConnection.getConnect();
            String sql = "UPDATE orders SET `status`=? WHERE order_id=? AND `status` <> 'Cancelled'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    //cancel order by admin
    public void cancelOrder(int orderId) {
        String updateOrderSql = "UPDATE orders SET status = 'Cancelled' WHERE order_id = ?";
        String getItemsSql = "SELECT product_id, quantity FROM order_items WHERE order_id = ?";
        String updateProductSql = "UPDATE products SET pquantity = pquantity + ? WHERE pid = ?";

        Connection conn =null;
        try {
        	conn = DbConnection.getConnect();
            conn.setAutoCommit(false); // start transaction

            // Update order status
            PreparedStatement psUpdateOrder = conn.prepareStatement(updateOrderSql);
            psUpdateOrder.setInt(1, orderId);
            psUpdateOrder.executeUpdate();

            // Get order items
            PreparedStatement psGetItems = conn.prepareStatement(getItemsSql);
            psGetItems.setInt(1, orderId);
            ResultSet rs = psGetItems.executeQuery();

            // Update product quantities
            PreparedStatement psUpdateProduct = conn.prepareStatement(updateProductSql);
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                psUpdateProduct.setInt(1, quantity); // add back canceled quantity
                psUpdateProduct.setInt(2, productId);
                psUpdateProduct.executeUpdate();
            }

            conn.commit(); // commit transaction

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // rollback on error
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }



}
