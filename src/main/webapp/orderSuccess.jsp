<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Success</title>
    <link href="./css/ordersuccess.css" rel="stylesheet">
</head>
<body>
    <h2>🎉 Order Placed Successfully!</h2>

    <div class="details">
        <p><b>Order ID:</b> ${order.orderId}</p>
        <p><b>Order Date and Time:</b> ${order.orderDate}</p>
        <p><b>Customer:</b> ${customer.name}</p>
        <p><b>Delivery Address:</b> ${deliveryAddress}</p>
        <p><b>Payment Method:</b> ${order.paymentMethod}</p>
    </div>

    <h3>Order Summary</h3>
    <table>
        <tr>
            <th>Product</th><th>Quantity</th><th>Price</th>
        </tr>
        <c:forEach var="item" items="${orderItems}">
            <tr>
                <td>${item.name}</td>
                <td>${item.quantity}</td>
                <td>₹${item.price}</td>
            </tr>
        </c:forEach>
        <tr class="total">
            <td colspan="2">Total</td>
            <td>₹${order.totalAmount}</td>
        </tr>
    </table>

    <a href="home.jsp">🛍 Continue Shopping</a>
</body>
</html>
