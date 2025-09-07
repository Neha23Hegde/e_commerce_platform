package com.ecommerce.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.dto.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MyOrdersServlet")
public class MyOrdersServlet extends HttpServlet {
    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        
        //Fetch all orders for this user
        List<Order> orders = orderDao.getOrdersByUserId(userId);

        //For each order, fetch its order items
        Map<Integer, List<Map<String, Object>>> orderItemsMap = new HashMap<>();
        for (Order order : orders) {
            orderItemsMap.put(order.getOrderId(), orderDao.getOrderItems(order.getOrderId()));
        }

        request.setAttribute("orders", orders);
        request.setAttribute("orderItemsMap", orderItemsMap);

        request.getRequestDispatcher("Myorders.jsp").forward(request, response);
    }
}


