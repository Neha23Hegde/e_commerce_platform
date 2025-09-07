<%@ page import="java.util.*, com.ecommerce.dao.AddressDao, com.ecommerce.dto.Address" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <link href="./css/checkout.css" rel="stylesheet">
</head>
<body>
	<h2>Checkout</h2>
	
	<%  // Ensure user is logged in
		Integer userId = (Integer) session.getAttribute("userId");
    	if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    	//Fetch cart from session
    List<Map<String,Object>> cart = (List<Map<String,Object>>) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) { %>
    
    <script>
        alert("Your cart is empty 😢 Redirecting to home page...");
        window.location.href = "home.jsp";
    </script>
    
<% return;
    }
	//Calculate Grand Total
    double grandTotal = 0.0;
    for (Map<String,Object> item : cart) {
        Number priceNum = (Number) item.get("price");
        Number qtyNum = (Number) item.get("quantity");
        double price = (priceNum != null) ? priceNum.doubleValue() : 0.0;
        int qty = (qtyNum != null) ? qtyNum.intValue() : 1;
        grandTotal += price * qty;
    }
	//Fetch user’s saved addresses from DB
    AddressDao addrDao = new AddressDao();
    List<Address> addresses = addrDao.getAddressesByUserId(userId);
%>

<div class="checkout-container">
    <h3>Cart Summary</h3>
    <table>
        <thead>
            <tr>
                <th>Product</th>
                <th>Price (₹)</th>
                <th>Quantity</th>
                <th>Total (₹)</th>
            </tr>
        </thead>
        <tbody>
        <% // Display each product in cart
        		for (Map<String,Object> item : cart) {
                String pname = (String) item.get("pname");
                Number priceNum = (Number) item.get("price");
                double price = (priceNum != null) ? priceNum.doubleValue() : 0.0;
                Number qtyNum = (Number) item.get("quantity");
                int qty = (qtyNum != null) ? qtyNum.intValue() : 1;
        %>
            <tr>
                <td><%= pname %></td>
                <td>₹<%= String.format("%.2f", price) %></td>
                <td><%= qty %></td>
                <td>₹<%= String.format("%.2f", price*qty) %></td>
            </tr>
        <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="3" style="text-align:right;">Grand Total</td>
                <td>₹<%= String.format("%.2f", grandTotal) %></td>
            </tr>
        </tfoot>
    </table>

    <h3>Select Address</h3>
    <form action="CheckoutServlet" method="post">
        <% if (addresses != null && !addresses.isEmpty()) { %>
            <h4>Saved Addresses</h4>
            <% //Show all saved addresses with radio buttons
            	for (Address a : addresses) { %>
                <label>
                    <input type="radio" name="address_option" value="existing_<%=a.getAddressId()%>" required>
                    <%= a.getAddressLine()%>, <%=a.getCity()%>, <%=a.getState()%>, <%=a.getCountry()%> - <%=a.getPincode()%>
                </label>
                <br>
            <% } %>
        <% } %>

        <h4>Add New Address</h4>
        <label>
            <input type="radio" name="address_option" value="new" required> New Address
        </label>
        <div id="newAddressFields" style="display:none; margin-top:10px;">
            Address Line: <input type="text" name="address_line"><br>
            City: <input type="text" name="city"><br>
            State: <input type="text" name="state"><br>
            Country: <input type="text" name="country"><br>
            Pincode: <input type="text" name="pincode"><br>
        </div>
        <h3>Payment Method</h3>
        <label><input type="radio" name="payment_method" value="COD" required> Cash on Delivery</label><br>
        <label><input type="radio" name="payment_method" value="Card"> Credit/Debit Card</label><br>
        <label><input type="radio" name="payment_method" value="UPI"> UPI</label><br>
		<br>
        <div></div>
        <input type="submit" value="Place Order">
    </form>
</div>
<!-- Script to toggle New Address input fields -->
<script>
    const addressRadios = document.querySelectorAll("input[name='address_option']");
    const newAddressFields = document.getElementById("newAddressFields");
    const newInputs = newAddressFields.querySelectorAll("input[type=text]");

    addressRadios.forEach(radio => {
        radio.addEventListener("change", function() {
        	// Show new address fields and make them required
            if (this.value === "new") {
                newAddressFields.style.display = "block";
                newInputs.forEach(input => input.required = true);
            }
         	// Hide new address fields if existing is selected
            else {
                newAddressFields.style.display = "none";
                newInputs.forEach(input => input.required = false);
            }
        });
    });
</script>
</body>
</html>
