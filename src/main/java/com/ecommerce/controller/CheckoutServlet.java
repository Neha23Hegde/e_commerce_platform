package com.ecommerce.controller;

import com.ecommerce.dao.AddressDao;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.dto.Address;
import com.ecommerce.dto.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

       // Retrieve cart from session
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }
        //Get address and payment details from request
        String addressOption = request.getParameter("address_option");
        String paymentMethod = request.getParameter("payment_method");

        AddressDao addressDao = new AddressDao();
        Address selectedAddress = null;

        try {
        	//Handle address selection
            
            if (addressOption != null && addressOption.startsWith("existing_")) {
                int addressId = Integer.parseInt(addressOption.split("_")[1]);
                selectedAddress = addressDao.getAddressById(addressId);

               
            } 
            else if ("new".equals(addressOption)) {
                Address newAddr = new Address();
                newAddr.setUserId(userId);
                newAddr.setAddressLine(request.getParameter("address_line"));
                newAddr.setCity(request.getParameter("city"));
                newAddr.setState(request.getParameter("state"));
                newAddr.setCountry(request.getParameter("country"));
                newAddr.setPincode(request.getParameter("pincode"));

                boolean inserted = addressDao.insertAddress(newAddr);
                if (inserted) {
                	//Fetch all addresses of this user and select the latest one
                    List<Address> userAddresses = addressDao.getAddressesByUserId(userId);
                    selectedAddress = userAddresses.get(userAddresses.size() - 1);
                }
            }

            if (selectedAddress != null) {
            	 //Calculate total order amount
                double totalAmount = 0.0;
                for (Map<String, Object> item : cart){
                    Number priceNum = (Number) item.get("price");
                    Number qtyNum = (Number) item.get("quantity");
                    double price = (priceNum != null) ? priceNum.doubleValue() : 0.0;
                    int qty = (qtyNum != null) ? qtyNum.intValue() : 1;
                    totalAmount += price * qty;
                }

                //Create new order object
                Order order = new Order();
                order.setUserId(userId);
                order.setAddressId(selectedAddress.getAddressId());
                order.setPaymentMethod(paymentMethod);
                order.setTotalAmount(totalAmount);

                OrderDao orderDao = new OrderDao();
                int orderId = orderDao.insertOrder(order);

                if (orderId > 0) {
                	//Insert each order item & update stock
                    for (Map<String, Object> item : cart) {
                        int productId = (Integer) item.get("pid");   
                        int qty = (Integer) item.get("quantity");
                        double price = (Double) item.get("price");

                        
                        orderDao.insertOrderItem(orderId, productId, qty, price);

                       
                        orderDao.updateProductStock(productId, qty);
                    }

                    
                    session.removeAttribute("cart");
                    session.setAttribute("orderId", orderId);

                    
                    response.sendRedirect("OrderSuccessServlet?orderId=" + orderId);
                } else {
                    request.setAttribute("error", "Order placement failed.");
                    request.getRequestDispatcher("checkout.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Please select or enter an address.");
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong during checkout.");
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }
}
