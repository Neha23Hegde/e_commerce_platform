<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders</title>
    <link href="./css/myorders.css" rel="stylesheet">   
</head>
<body>
<!--  cancel order success and error-->
<c:if test="${not empty sessionScope.success}">
    <p style="color:green">${sessionScope.success}</p>
    <c:remove var="success" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <p style="color:red">${sessionScope.error}</p>
    <c:remove var="error" scope="session"/>
</c:if>
<div class="orders-header">
     <h2>📦 My Orders</h2>
    <a href="home.jsp" class="back-btn">🏠 Back to Home</a>
</div>
    <c:if test="${empty orders}">
        <p class="empty-msg">No orders found.</p>
    </c:if>

    <c:forEach var="order" items="${orders}">
        <div class="order-box">
            <div class="order-header">
    			<span>Status: ${order.status}</span>
    			<!-- cancel button -->
    			<c:if test="${order.status ne 'Cancelled' && order.status ne 'Delivered'}">
                <form action="CancelOrderServlet" method="post" style="display:inline;">
            	<input type="hidden" name="orderId" value="${order.orderId}" />
            	<button type="submit" style="background:#dc3545; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;">
                			Cancel Order</button>
        		</form>
    		</c:if>
			</div>
            <table>
                <tr>
                	<th>Image</th>
                    <th>Product</th>
                    <th>Qty</th>
                    <th>Price (₹)</th>
                    <th>Subtotal (₹)</th>
                </tr>
                <c:forEach var="item" items="${orderItemsMap[order.orderId]}">
                    <tr>
                    	<td><img src="${item.imageUrl}" alt="${item.name}" style="width:60px; height:60px; object-fit:cover; border-radius:4px;"/></td>
                        <td>${item.name}</td>
                        <td>${item.quantity}</td>
                        <td>${item.price}</td>
                        <td>${item.subtotal}</td>   
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:forEach>    
</body>
</html>