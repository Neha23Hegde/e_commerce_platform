package com.ecommerce.controller;

import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dto.Customer;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        //Get updated profile details from request
        int userId = (int) session.getAttribute("userId");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        
        boolean updated = CustomerDao.updateProfile(userId, email, Long.parseLong(mobile));

        if (updated) {
           //update session attributes  
            session.setAttribute("email", email);
            session.setAttribute("mobile", Long.parseLong(mobile));
            request.setAttribute("message", "Profile updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update profile. Try again.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
        rd.forward(request, response);
    }
}
