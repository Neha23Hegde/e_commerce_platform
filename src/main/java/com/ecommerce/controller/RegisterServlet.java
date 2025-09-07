package com.ecommerce.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dto.Customer;
import com.ecommerce.util.EmailUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;


@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDao dao;
       
    
	   @Override
		public void init() throws ServletException {
			dao= new CustomerDao();
		}
	   
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			Long mobile =Long.parseLong(request.getParameter("mobile"));
			String password=request.getParameter("password");
			
			Customer c=new Customer(name,email,mobile,password);
			boolean isSaved=dao.saveCustomer(c);
			if (isSaved) {
	            // Send Welcome Email
	            String subject = "Welcome to Swift Shop!";
	            String body = "Hello " + name + ",\n\n" +
	                          "Thank you for registering with Swift Shop.\n" +
	                          "Happy Shopping!\n\nTeam Swift Shop";

	            EmailUtil.sendEmail(email, subject, body);

	            HttpSession session = request.getSession();
	            session.setAttribute("username", name);
	            response.sendRedirect("login.jsp");
	        } else {
	            out.print("error!");
	        }
	    }
}
