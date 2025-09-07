<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.ecommerce.dto.Product, com.ecommerce.dao.ProductDAO" %>
<%
    String username = (String) session.getAttribute("username");// get logged-in user
    String selectedCategory = request.getParameter("category");//category filter
    String searchQuery = request.getParameter("search");//search filter
    List<Product> products = null;

    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        products = ProductDAO.getProductsBySearch(searchQuery.trim());// search by name
    } 
    else if (selectedCategory != null && !selectedCategory.trim().isEmpty()) {
        products = ProductDAO.getProductsByCategory(selectedCategory);//filter by category
    } 
    else {
        products = ProductDAO.getRandomProducts(32);
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>E-commerce</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="./css/global.css" rel="stylesheet">
</head>
<body>
<div class="header">
  <div class="navbar">
    <div class="logo"><img  src="./images/Logo.png" height="50px" width="130px" style=mix-blend-mode:multiply/></div>
      <!-- Search bar -->
 	 <div class="searchbar" style="width: 100%; max-width: 500px; position: relative;">
  	 <form action="home.jsp" method="get" style="width: 100%;">
     <input 
      type="text" 
      name="search" 
      placeholder="🔍 Search for Products, Brands and More"
      value="<%= (request.getParameter("search") != null) ? request.getParameter("search") : "" %>"
      id="searchInput"
      style="width: 100%; padding: 10px 40px 10px 12px; font-size: 16px; border-radius: 20px; border: 1px solid #ccc;"
      oninput="toggleClearButton()"
    >
    <!--  Clear button -->
    <span 
      id="clearBtn"
      onclick="clearSearch()" 
      style="position: absolute; right: 12px; top: 50%; transform: translateY(-50%); cursor: pointer; font-size: 18px; color: gray; display: none;">
      ×
    </span>
  </form>
</div>
  <!-- Menu items depends on login status -->
    <div class="menu-items">
      <% if (username != null) { %>
        <span class="user"> <a href="profile.jsp">👤 <%= username %></a></span>
        <a href="Logout">➟ Logout</a>
        <a href="MyOrdersServlet">🛍️ My Orders</a>
      <% } else { %>
        <a href="login.jsp">👤 Login</a>
      <% } %>
      <a href="cart.jsp">🛒 Cart</a> 
    </div>
  </div>

  <div class="categories">
    <div class="img-cate"><a href="home.jsp?category=mobiles"><img src="images/mobiles.jpeg" height="40" width="40"><p>Mobiles</p></a></div>
    <div class="img-cate"><a href="home.jsp?category=tv"><img src="images/Tv_ic.jpg" height="40" width="40"><p>TVs</p></a></div>
    <div class="img-cate"><a href="home.jsp?category=electronics"><img src="images/electronics.jpg" height="40" width="40"><p>Electronics</p></a></div>
    <div class="img-cate"><a href="home.jsp?category=kitchen"><img src="images/kitchen.jpg" height="40" width="40"><p>Kitchen</p></a></div>
    <div class="img-cate"><a href="home.jsp?category=furniture"><img src="images/Furniture_ic.webp" height="40" width="40"><p>Furniture</p></a></div>
  </div>
</div>
<!--Bootstrap carousel-->
<div class="content">
  <div id="carouselExampleCaptions" class="carousel slide mb-5" data-bs-ride="carousel">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="images/Furniture.jpg" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
          <h5>Furniture Offers</h5>
          <p>Big Discounts on Sofas and Beds</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="images/Kitchen_Appliances.webp" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
          <h5>Kitchen Essentials</h5>
          <p>Cook in style with top brands</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="images/Home_Appliances.jpg" class="d-block w-100" alt="...">
        <div class="carousel-caption d-none d-md-block">
          <h5>Home Appliances</h5>
          <p>Best Deals on ACs, Washing Machines</p>
        </div>
      </div>
    </div>
  </div>
  <!-- Product display based on category selection -->
  <div class="container">
    <h3 class="text-center mb-4">
      <%= selectedCategory != null ? (selectedCategory.toUpperCase()) : " " %>
    </h3>
    <div class="d-flex flex-wrap justify-content-center gap-4">
      <% if (products != null && !products.isEmpty()) {
           for (Product p : products) { %>
             <div class="card" style="width: 18rem" >
               <img src="<%= p.getImageUrl() %>"  class="card-img-top"  alt="<%= p.getPname() %>">
               <div class="card-body">
                 <h5 class="card-title"><%= p.getPname() %></h5>
                 <p class="card-text"><strong>₹<%= p.getPrice() %></strong></p>
                 <a href="productdetails?pid=<%= p.getPid() %>" class="btn btn-primary">View Details</a>
               </div>
             </div>
      <%   } 
         } 
      	else if (searchQuery != null && !searchQuery.trim().isEmpty()) { %>
       <p style="color:red; font-size:30px; text-align:center; width:100%;">Product not found!</p>
<% } %>
      
    </div>
  </div>
</div>
<!-- toggle for clear button -->
<script>
  function toggleClearButton() {
    let input = document.getElementById("searchInput");
    let clearBtn = document.getElementById("clearBtn");
    clearBtn.style.display = input.value.length > 0 ? "block" : "none";
  }

  function clearSearch() {
    document.getElementById("searchInput").value = "";  
    document.getElementById("clearBtn").style.display = "none";
    window.location.href = "home.jsp"; 
  }

  
  window.onload = toggleClearButton;
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>


</html>
