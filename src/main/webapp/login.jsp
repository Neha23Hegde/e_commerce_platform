<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>

/* Full-page gradient background */
body {
    font-family: Arial, sans-serif;
    background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
    margin: 0;
    padding: 0;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

/* Form container */
form {
    width: 350px;
    background: rgba(255, 255, 255, 0.95);
    padding: 25px 30px;
    border-radius: 10px;
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

/* Fieldset */
fieldset {
    border: none;
    padding: 0;
    margin: 0;
}

/* Legend */
legend {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 20px;
    color: #333;
    text-align: center;
}

/* Labels */
label {
    display: block;
    margin-bottom: 6px;
    font-size: 0.95em;
    color: #444;
}

/* Inputs */
input[type="email"],
input[type="password"] {
    width: 100%;
    padding: 12px;
    margin-bottom: 18px;
    border:


</style>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="Login" method="post">
<fieldset>
	<legend>Login Page</legend>
	
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