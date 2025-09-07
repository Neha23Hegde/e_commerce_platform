<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin - Dashboard</title>
   <link href="./css/admin.css" rel="stylesheet">
</head>
<body>
	<div class="header">
  		<div class="brand">Swift Shop</div>
  		<div class="title">Admin Dashboard</div>
 		<a href="Logout" class="logout-btn">Logout</a>
	</div>
	<table>
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Product Name</th>
        <th>Products</th>
        <th>Quantity</th>
        <th>Payment Method</th> 
        <th>Status</th>
        <th>Total Amount</th>
        <th>Order Date</th>
        <th>Actions</th>
    </tr>
     <!-- Loop through all orders -->
    <c:forEach var="order" items="${orders}">
        <tr>
        <!-- Display Order Info -->
            <td>${order.orderId}</td>
            <td>${order.userId}</td>
            <!-- Display Product Names for the Order -->
            <td><c:forEach var="item" items="${orderItemsMap[order.orderId]}">
        			${item.name} <br/></c:forEach></td>
            <td><c:forEach var="item" items="${orderItemsMap[order.orderId]}">
        			<img src="${item.imageUrl}" alt="${item.name}" style="width:50px; height:50px; object-fit:cover; margin:2px;"  />
   				 </c:forEach></td>
   			<td><c:forEach var="item" items="${orderItemsMap[order.orderId]}">
        		${item.quantity} <br/>
    		</c:forEach></td>
            <td>${order.paymentMethod}</td>
            <td>${order.status}</td>
            <td>${order.totalAmount}</td>
            <td>${order.orderDate}</td>
            <td>
    <c:choose>
    <c:when test="${order.status eq 'Cancelled'}">
        <span style="color:red; font-weight:bold;">Order Cancelled</span>
    </c:when>
    <c:when test="${order.status eq 'Delivered'}">
        <span style="color:green; font-weight:bold;">Order Delivered</span>
    </c:when>
    <c:otherwise>
        <form method="post" action="adminOrders" style="display:inline;">
            <input type="hidden" name="orderId" value="${order.orderId}" />
            <select name="status">
                <option value="Pending" <c:if test="${order.status eq 'Pending'}">selected</c:if>>Pending</option>
                <option value="Shipped" <c:if test="${order.status eq 'Shipped'}">selected</c:if>>Shipped</option>
                <option value="Delivered" <c:if test="${order.status eq 'Delivered'}">selected</c:if>>Delivered</option>
            </select>
            <button type="submit" name="action" value="updateStatus">Update</button>
        </form>
        <form method="post" action="adminOrders" style="display:inline;">
            <input type="hidden" name="orderId" value="${order.orderId}" />
            <button type="submit" name="action" value="cancelOrder">Cancel</button>
        </form>
    </c:otherwise>
	</c:choose>
	</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
