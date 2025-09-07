package com.ecommerce.controller;

import com.ecommerce.dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = request.getParameter("email");
        long mobile = Long.parseLong(request.getParameter("mobile"));

        boolean success = userDao.updateProfile(username, email, mobile);

        if (success) {
            session.setAttribute("email", email);
            session.setAttribute("mobile", mobile);
            request.setAttribute("message", "Profile updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update profile.");
        }

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
