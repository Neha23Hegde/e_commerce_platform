<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="./css/login.css" rel="stylesheet">
	</head>
<body>
	<form action="Login" method="post">
		<fieldset>
		<legend>Login to SwiftShop</legend>
	
		<% String errorMessage = (String) request.getAttribute("errorMessage");
    		if (errorMessage != null) { %>
    	<p style="color:red;"><%= errorMessage %></p>
		<% } %>
	
		<label for="email">Email</label>
		<input type="email" id="email" name="email" required>
		<br><br>
	
		<label for="password">Password</label>
		<input type="password" id="password" name="password" required>
		<br><br>
	
		<button type="submit">Login</button>
		<br><br>
	
	 	<span> you  don't have a account? <a href="./register.jsp">SignUp</a></span>
	
		</fieldset>
	</form>
</body>
</html>