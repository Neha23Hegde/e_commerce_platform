<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<style>
   body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(120deg, #4facfe, #00f2fe);
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
}

form {
    background: #fff;
    padding: 30px 40px;
    border-radius: 12px;
    box-shadow: 0px 10px 25px rgba(0,0,0,0.1);
    width: 350px;
    animation: fadeIn 0.8s ease-in-out;
}

@keyframes fadeIn {
    from { opacity: 0; transform: translateY(-10px); }
    to { opacity: 1; transform: translateY(0); }
}

fieldset {
    border: none;
    padding: 0;
    margin: 0;
}

legend {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 15px;
    text-align: center;
    color: #333;
}

label {
    display: block;
    margin: 10px 0 5px;
    font-weight: 500;
    color: #444;
}

input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 6px;
    outline: none;
    transition: all 0.3s ease;
}

input:focus {
    border-color: #4facfe;
    box-shadow: 0 0 5px rgba(79,172,254,0.5);
}

button {
    width: 100%;
    padding: 10px;
    margin-top: 15px;
    border: none;
    background: linear-gradient(120deg, #4facfe, #00f2fe);
    color: #fff;
    font-size: 16px;
    border-radius: 6px;
    cursor: pointer;
    transition: all 0.3s ease;
}

button:hover {
    background: linear-gradient(120deg, #00f2fe, #4facfe);
}

span{
padding-left:60px;
}

</style>
</head>
<body>
<form action="Register" method="post">
<fieldset>
	<legend>SignUp Page</legend>
	
	<label for="name">Name</label>
	<input type="text" id="name" name="name" required>
	<br><br>
	
	<label for="email">Email</label>
	<input type="email" id="email" name="email" required>
	<br><br>
	
	<label for="mobile">Phone No.</label>
	<input type="text" id="mobile" name="mobile" required>
	<br><br>
	
	<label for="password">Password</label>
	<input type="password" id="password" name="password" required>
	<br><br>
	
	<button type="submit">Sign In</button>
	<br><br>
	
	 <span>alredy you have a account? <a href="./login.jsp">Login</a></span>
</fieldset>
</form>
</body>
</html>
