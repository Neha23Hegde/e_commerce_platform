package com.ecommerce.controller;

import java.io.IOException;

import com.ecommerce.dao.OrderDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {
    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int orderId = Integer.parseInt(request.getParameter("orderId"));

       // Call DAO method to cancel the order and ensuring it belongs to the particular user
        boolean success = orderDao.cancelOrder(orderId, userId);

        if (success) {
            request.setAttribute("success", "Order cancelled successfully.");
        } else {
            request.setAttribute("error", "Unable to cancel order.");
        }

        response.sendRedirect("MyOrdersServlet");
    }
}

