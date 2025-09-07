package com.ecommerce.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;

import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dto.Customer;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Customer user = CustomerDao.login(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getName());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("mobile", user.getMobile());
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());

            // redirect based on role
            if ("admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("adminOrders");
            } else {
                response.sendRedirect("home.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid user credentials");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }
}
