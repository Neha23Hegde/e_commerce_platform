package com.ecommerce.controller;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.dto.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class PlaceOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String paymentMethod = req.getParameter("payment_method");
        int addressId = Integer.parseInt(req.getParameter("address_id"));

        // Get cart
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect("cart.jsp?msg=Cart is empty!");
            return;
        }

        try {
            // Calculate total
            double totalAmount = 0.0;
            for (Map<String, Object> item : cart) {
                totalAmount += (Integer) item.get("quantity") * (Double) item.get("price");
            }

            // Create order object
            Order order = new Order();
            order.setUserId(userId);
            order.setAddressId(addressId);
            order.setPaymentMethod(paymentMethod);
            order.setTotalAmount(totalAmount);

            OrderDao orderDao = new OrderDao();

            // Insert into orders table and get generated orderId
            int orderId = orderDao.insertOrder(order);

            // Insert each cart item
            for (Map<String, Object> item : cart) {
                int productId = (Integer) item.get("productId");
                int qty = (Integer) item.get("quantity");
                double price = (Double) item.get("price");

                orderDao.insertOrderItem(orderId, productId, qty, price);
                orderDao.updateProductStock(productId, qty);
            }

            // Clear cart after successful order
            session.removeAttribute("cart");

            resp.sendRedirect("orderSuccess.jsp?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error placing order: " + e.getMessage());
        }
    }
}
