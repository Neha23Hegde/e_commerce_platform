<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link href="./css/register.css" rel="stylesheet">  
</head>
<body>
    <form name="registerForm" action="Register" method="post" onsubmit="return validateForm()">
        <fieldset>
            <legend>SignUp To SwiftShop</legend>
            
            <label for="name">Name</label>
            <input type="text" name="name" required>
            <span id="nameError" class="error"></span>

            <label for="email">Email</label>
            <input type="email" name="email" required>
            <span id="emailError" class="error"></span>

            <label for="phone">Phone No.</label>
            <input type="text" name="mobile" required>
            <span id="phoneError" class="error"></span>

            <label for="password">Password</label>
            <input type="password" name="password" required>
            <span id="passwordError" class="error"></span>

            <button type="submit">Register</button>
            
            <span>Already have an account? <a href="login.jsp">Login</a></span>
        </fieldset>
    </form>

    <script>
    function validateForm() {
        let valid = true;

        // clear errors
        document.querySelectorAll(".error").forEach(e => e.innerText = "");

        let name = document.forms["registerForm"]["name"].value.trim();
        let email = document.forms["registerForm"]["email"].value.trim();
        let phone = document.forms["registerForm"]["mobile"].value.trim();
        let password = document.forms["registerForm"]["password"].value.trim();

        if (name.length < 3) {
            document.getElementById("nameError").innerText = "Name must be at least 3 characters";
            valid = false;
        }

        let emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
        if (!email.match(emailPattern)) {
            document.getElementById("emailError").innerText = "Invalid email format";
            valid = false;
        }

        let phonePattern = /^[0-9]{10}$/;
        if (!phone.match(phonePattern)) {
            document.getElementById("phoneError").innerText = "Phone must be 10 digits";
            valid = false;
        }

        let passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
        if (!password.match(passwordPattern)) {
            document.getElementById("passwordError").innerText = 
                "Password must be at least 6 characters with letters and numbers";
            valid = false;
        }

        return valid;
    }
    </script>
</body>
</html>
