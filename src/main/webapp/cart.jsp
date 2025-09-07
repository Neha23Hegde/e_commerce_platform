<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <link href="./css/cart.css" rel="stylesheet">
</head>
<body>
<h2>Your Shopping Cart</h2>
<% // Retrieve cart from session 
	List<Map<String,Object>> cart = (List<Map<String,Object>>) session.getAttribute("cart");
    double grandTotal = 0.0;
    if (cart == null || cart.isEmpty()) {
%>
        <script>
            alert("Your cart is empty 😢 Redirecting to home page...");
            window.location.href = "home.jsp"; 
        </script>
        <p style="text-align:center;">Your cart is empty.</p>
<% 
    } else {
%>
        <table>
            <thead>
                <tr>
                    <th>Product Image</th>
                    <th>Product Name</th>
                    <th>Price (₹)</th>
                    <th>Quantity</th>
                    <th>Total (₹)</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Map<String,Object> item : cart) {
                   
                    Number pidNum = (Number) item.get("pid");
                    int pid = (pidNum != null) ? pidNum.intValue() : 0;

                    String pname = (String) item.get("pname");
                    String imageUrl = (String) item.get("imageUrl");

                    Number priceNum = (Number) item.get("price");
                    double price = (priceNum != null) ? priceNum.doubleValue() : 0.0;

                    Number qtyNum = (Number) item.get("quantity");
                    int quantity = (qtyNum != null) ? qtyNum.intValue() : 1;

                    double total = price * quantity;
                    grandTotal += total;
            %>
                <tr>
                    <td><img src="<%= imageUrl %>" alt="Product"></td>
                    <td><%= pname %></td>
                    <td>₹<%= String.format("%.2f", price) %></td>
                    <td>
                        <form action="CartServlet" method="post" style="display:inline;">
                            <input type="hidden" name="pid" value="<%= pid %>">
                            <input type="hidden" name="action" value="decrease">
                            <button type="submit" class="qty-btn">-</button>
                        </form>

                        <%= quantity %>

                        <form action="CartServlet" method="post" style="display:inline;">
                            <input type="hidden" name="pid" value="<%= pid %>">
                            <input type="hidden" name="action" value="increase">
                            <button type="submit" class="qty-btn">+</button>
                        </form>
                    </td>
                    <td>₹<%= String.format("%.2f", total) %></td>
                </tr>
            <%
                }
            %>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4" class="grand-label">Grand Total</td>
                    <td class="grand-total">₹<%= String.format("%.2f", grandTotal) %></td>
                </tr>
            </tfoot>
        </table>

        <div class="checkout-container">
            <form action="checkout.jsp" method="post">
                <button type="submit">Proceed to Checkout</button>    
            </form>
             <form action="home.jsp" method="post">
        		<button type="submit">Go to Home</button>
   			 </form>
        </div>
<%
    }
%>

</body>
</html>
