<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>

body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background: linear-gradient(135deg, #4facfe, #00f2fe);
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

form {
    background: rgba(255, 255, 255, 0.9);
    padding: 30px 40px;
    border-radius: 12px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    width: 350px;
}

fieldset {
    border: none;
    padding: 0;
}

legend {
    font-size: 1.5rem;
    font-weight: bold;
    color: #333;
    margin-bottom: 15px;
    display: block;
    text-align: center;
}

label {
    display: block;
    font-weight: bold;
    color: #444;
    margin-bottom: 5px;
}

input {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-sizing: border-box;
    transition: all 0.3s ease;
}

input:focus {
    border-color: #4facfe;
    outline: none;
    box-shadow: 0 0 8px rgba(79, 172, 254, 0.5);
}

button {
    width: 100%;
    padding: 10px;
    background: linear-gradient(135deg, #4facfe, #00f2fe);
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    font-weight: bold;
    cursor: pointer;
    transition: background 0.3s ease;
}

button:hover {
    background: linear-gradient(135deg, #00f2fe, #4facfe);
}

span {
    display: block;
    margin-top: 10px;
    text-align: center;
    font-size: 0.9rem;
    color: #333;
}

span a {
    color: #4facfe;
    text-decoration: none;
    font-weight: bold;
}

span a:hover {
    text-decoration: none;
}


</style>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<form action="Login" method="post">
<fieldset>
	<legend>Login Page</legend>
	
	<% 
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
    <p style="color:red;"><%= errorMessage %></p>
<% 
    }
%>
	
	<label for="email">Email</label>
	<input type="email" id="email" name="email" required>
	<br><br>
	
	<label for="password">Password</label>
	<input type="password" id="password" name="password" required>
	<br><br>
	
	<button type="submit">Login</button>
	<br><br>
	
	 <span> you  don't have a account? <a href="./register.jsp">SignIn</a></span>
	
</fieldset>
</form>
</body>
</html>