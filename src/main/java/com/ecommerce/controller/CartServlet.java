package com.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Check if user is logged in or not 
        String username = (String) session.getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        //Retrieve cart from session 
        List<Map<String,Object>> cart = (List<Map<String,Object>>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        String action = Optional.ofNullable(request.getParameter("action")).orElse("add").trim().toLowerCase();
        String pidStr = request.getParameter("pid");

        try {
            switch (action) {
                case "increase":
                case "decrease":
                case "remove": {
                    
                    if (pidStr == null) break;
                    int pid = Integer.parseInt(pidStr);

                    Iterator<Map<String,Object>> it = cart.iterator();
                    while (it.hasNext()) {
                        Map<String,Object> item = it.next();
                        int itemPid = ((Number)item.get("pid")).intValue();
                        if (itemPid == pid) {
                            int qty = ((Number)item.get("quantity")).intValue();

                            if ("increase".equals(action)) {
                                item.put("quantity", qty + 1);
                            } else if ("decrease".equals(action)) {
                                if (qty > 1) {
                                    item.put("quantity", qty - 1);
                                } else {
                                    
                                    it.remove();
                                }
                            } else { 
                                it.remove();
                            }
                            break;
                        }
                    }
                    break;
                }
                //add to cart

                case "add":
                default: {
                    
                    if (pidStr == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing pid");
                        return;
                    }
                    int pid = Integer.parseInt(pidStr);
                    String pname = request.getParameter("pname");
                    String imageUrl = request.getParameter("imageUrl");
                    double price = Double.parseDouble(request.getParameter("price"));
                    int quantity = Integer.parseInt(
                        Optional.ofNullable(request.getParameter("quantity")).orElse("1")
                    );
                    
                    // Check if product already exists in cart
                    boolean exists = false;
                    for (Map<String,Object> item : cart) {
                        int itemPid = ((Number)item.get("pid")).intValue();
                        if (itemPid == pid) {
                            int currentQty = ((Number)item.get("quantity")).intValue();
                            item.put("quantity", currentQty + quantity);
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        Map<String,Object> item = new HashMap<>();
                        item.put("pid", pid);
                        item.put("pname", pname);
                        item.put("imageUrl", imageUrl);
                        item.put("price", price);
                        item.put("quantity", quantity);
                        cart.add(item);
                    }
                }
            }

            session.setAttribute("cart", cart);
            response.sendRedirect("cart.jsp");
        }
        
        catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        }
    }
}