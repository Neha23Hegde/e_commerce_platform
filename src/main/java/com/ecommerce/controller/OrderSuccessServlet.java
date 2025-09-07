package com.ecommerce.controller;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dao.AddressDao;
import com.ecommerce.dto.Order;
import com.ecommerce.dto.OrderItem;
import com.ecommerce.dto.Customer;
import com.ecommerce.dto.Address;
import com.ecommerce.util.EmailUtil;
import com.ecommerce.util.PdfGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/OrderSuccessServlet")
public class OrderSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            OrderDao orderDao = new OrderDao();
            CustomerDao customerDao = new CustomerDao();
            AddressDao addressDao = new AddressDao();

            // Fetch Order
            Order order = orderDao.getOrderById(orderId);

            if (order == null) {
                response.sendRedirect("checkout.jsp?error=Order not found");
                return;
            }

            // Fetch Customer using userId from Order
            Customer customer = customerDao.getCustomerById(order.getUserId());

            if (customer == null) {
                response.sendRedirect("checkout.jsp?error=Customer not found");
                return;
            }

            // Fetch Address using addressId from order
            Address address = addressDao.getAddressById(order.getAddressId());

            String deliveryAddress = "";
            if (address != null) {
                deliveryAddress = address.getAddressLine() + ", "
                        + address.getCity() + ", "
                        + address.getState() + " - "
                        + address.getPincode() + ", "
                        + address.getCountry();
            } else {
                deliveryAddress = "No address found";
            }

            // Fetch Order Items
            List<Map<String, Object>> orderItems = orderDao.getOrderItems(orderId);

            // Convert to PdfGenerator.OrderItem list
            List<OrderItem> pdfItems = new ArrayList<>();
            for (Map<String, Object> item : orderItems) {
                String productName = (String) item.get("name");
                int quantity = ((Number) item.get("quantity")).intValue();
                double price = ((Number) item.get("price")).doubleValue();
                pdfItems.add(new OrderItem(productName, quantity, price));
            }

            // Generate PDF Invoice
            String pdfPath = PdfGenerator.generateInvoice(
                    order.getOrderId(),
                    customer.getName(),
                    deliveryAddress,   
                    order.getPaymentMethod(),
                    order.getTotalAmount(),
                    pdfItems
            );

            // Send Email with PDF
            String subject = "Your Order Confirmation - Order #" + order.getOrderId();
            String body = "Hello " + customer.getName() + ",\n\n" +
                          "Thank you for your order with Swift Shop!\n" +
                          "Please find attached your invoice.\n\n" +
                          "Team Swift Shop";

            EmailUtil.sendEmailWithAttachment(
                    customer.getEmail(),   // send to customer's email
                    subject,
                    body,
                    new File(pdfPath)
            );

            // Forward to JSP 
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("customer", customer);
            request.setAttribute("deliveryAddress", deliveryAddress);
            request.getRequestDispatcher("orderSuccess.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("checkout.jsp?error=Unable to fetch order details");
        }
    }
}
