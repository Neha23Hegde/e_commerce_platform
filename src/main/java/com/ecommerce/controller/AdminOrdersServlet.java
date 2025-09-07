package com.ecommerce.controller;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.dto.Order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/adminOrders")
public class AdminOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        OrderDao orderDao = new OrderDao();
        List<Order> orders = orderDao.getAllOrders();

        // Build orderItemsMap for images
        Map<Integer, List<Map<String, Object>>> orderItemsMap = new HashMap<>();
        for (Order order : orders) {
            List<Map<String, Object>> items = orderDao.getOrderItems(order.getOrderId());
            orderItemsMap.put(order.getOrderId(), items);
        }

        // Set attributes for JSP
        request.setAttribute("orders", orders);
        request.setAttribute("orderItemsMap", orderItemsMap);

        RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
        rd.forward(request, response);
    }
    //cancel order 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDao orderDao = new OrderDao();

        if ("updateStatus".equals(action)) {
            String newStatus = request.getParameter("status");
            orderDao.updateOrderStatus(orderId, newStatus);
        } else if ("cancelOrder".equals(action)) {
            orderDao.cancelOrder(orderId);
        }

        response.sendRedirect("adminOrders");
    }
}

