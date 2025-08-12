package com.ecommerce.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dto.Customer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Customer customer = CustomerDao.login(email, password);

        if (customer != null) {
            // Create a session and store the username
            HttpSession session = request.getSession();
            session.setAttribute("username", customer.getName());

            // Redirect to home.jsp
            response.sendRedirect("home.jsp");
        } else {
            // Invalid credentials - send back to login.jsp with error message
            request.setAttribute("errorMessage", "Invalid user credentials!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }

}
