package com.ecommerce.controller;

import com.ecommerce.dto.Product;
import com.ecommerce.dao.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/productdetails")
public class ProductDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pidParam = request.getParameter("pid");
        Product product = null;

        if (pidParam != null && !pidParam.isEmpty()) {
            try {
                int pid = Integer.parseInt(pidParam);
                product = ProductDAO.getProductDetails(pid);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("product", product);
        request.getRequestDispatcher("productdetails.jsp").forward(request, response);
    }
}
