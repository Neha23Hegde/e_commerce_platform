<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Profile Page</title>
 	<link href="./css/profile.css" rel="stylesheet">   
</head>
<body>
<div class="container">
    <h1>Your Profile</h1>
    <c:if test="${not empty requestScope.message}">
        <p class="message">${requestScope.message}</p>
    </c:if>
    <c:if test="${not empty requestScope.error}">
        <p class="error">${requestScope.error}</p>
    </c:if>

    <form action="UpdateProfileServlet" method="post">
        <label>Name</label>
        <input type="text" name="username" value="${sessionScope.username}" readonly />

        <label>Email</label>
        <input type="email" name="email" value="${sessionScope.email}" required
               pattern="^[a-zA-Z0-9._%+-]+@gmail\.com$"
			   title="Enter a valid Gmail address (e.g., user@gmail.com)"/>

        <label>Phone No.</label>
        <input type="tel" name="mobile" value="${sessionScope.mobile}" required
               pattern="[0-9]{10}" title="Enter a valid 10-digit phone number" />

        <div class="btn-group">
            <button type="submit" class="btn">Update Profile</button>
            <button type="button" class="btn btn-secondary" onclick="window.location.href='home.jsp'">
                Go Back to Home
            </button>
        </div>
    </form>
</div>
</body>
</html>


