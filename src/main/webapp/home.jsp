<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>E-comerce</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: Arial, sans-serif;
    }

    #navbar {
      display: flex;
      align-items: center;
      justify-content: space-between;
      background-color: skyblue;
      padding: 10px 20px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    .logo {
      display: flex;
      flex-direction: column;
      font-weight: bold;
      font-size: 14px;
    }


    .logo span {
      font-size: 12px;
      color: #878787;
    }

    .searchbar input {
      width: 400px;
      padding: 10px;
      border: none;
      background-color: #f0f5ff;
      border-radius: 4px;
    }

    .menu-items {
      display: flex;
      align-items: center;
      gap: 25px;
    }

    .menu-items a {
      text-decoration: none;
      color: #212121;
      font-weight: bold;
      font-size: 14px;
    }

    .categories {
      display: flex;
      justify-content: center;
      background-color: #fff;
      padding: 10px 0;
      border-top: 1px solid #ddd;
      border-bottom: 1px solid #ddd;
      justify-content:space-evenly;
    }
    .img-cate{
      display:flex;
      align-items: center;
      justify-content: space-between;
    }
    .categories a {
      margin: 0 15px;
      text-decoration: none;
      color: #333;
      font-weight: 500;
    }

    .categories a:hover {
      color: #2874f0;
    }
  </style>
</head>
<body>

 
  <div id="navbar">
   
    <div class="logo">
      E-COMMERCE 
    </div>

    <div class="searchbar">
      <input type="text" placeholder="🔍 Search for Products, Brands and More">
    </div>

    <div class="menu-items">
    	<%
            String username = (String) session.getAttribute("username");
        %>
        <% if (username != null) { %>
            <span> 👤 Welcome, <%= username %></span>
            <a href="Logout">Logout ➺</a>
        <% } else { %>
            <a href="register.jsp"> 👤Login</a>
        <% } %>
      
      <a href="#">🛒 Cart</a>
      <a href="#">🏪 Become a Seller</a>
    </div>
  </div>

  <div class="categories">
    <!--   <div class="img-cate">
      <a href="#"><img src="https://static.vecteezy.com/system/resources/previews/005/425/617/non_2x/shopping-special-offer-label-with-percentage-sign-and-emojis-3d-illustration-vector.jpg" height="40px" width="40px"><p>Top Offers</p></a>
    </div>-->
    <div class="img-cate">
      <a href="#"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiEr-9NVSsHdsYVz03lCeLiRxfq5vDXfbSdg&s" height="40px" width="40px"><p>Mobiles & Tablets</p></a>
    </div>
    <div class="img-cate">
      <a href="#"><img src="https://www.shutterstock.com/image-photo/home-appliances-tv-refrigerator-washing-600w-2248825351.jpg" height="40px" width="40px"><p>TVs & Appliances </p></a>
    </div>
    <div class="img-cate">
      <a href="#"><img src="https://www.arkema.com/files/live/sites/shared_arkema/files/images/markets/Electronics%20electrical/electronics.jpg" height="40px" width="40px"><p>Electronics</p></a>
    </div>
   <!--   <div class="img-cate">
      <a href="#"><img src="https://cdn2.stylecraze.com/wp-content/uploads/2018/01/Fashion-Tips-Every-Girl.jpg.webp" height="40px" width="40px"><p>Fashion</p></a>
    </div>
    <div class="img-cate">
      <a href="#"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSy_yv9C1PcZgnDymWAy6XlcOW_bKt4EjyNQw&s" height="40px" width="40px"><p>Beauty, Food...</p></a>
    </div>-->
    <div class="img-cate">
      <a href="#"><img src="https://assets.aboutamazon.com/dims4/default/e773cec/2147483647/strip/true/crop/1599x900+1+0/resize/1240x698!/quality/90/?url=https%3A%2F%2Famazon-blogs-brightspot.s3.amazonaws.com%2F9e%2F34%2Fb59df0f244e0a6c39929a16ccf90%2Fkitchen-appliances-lead.jpg" height="40px" width="40px"><p>Home & Kitchen</p></a>
    </div>
    <div class="img-cate">
      <a href="#"><img src="https://www.urbanwood.in/assets/image/popular-items/sofa-set.jpg" height="40px" width="40px"><p>Furniture</p></a>
    </div>
    <!--  <div class="img-cate">
      <a href="#"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBM0MYufCsNhMGYYFhwnmrzGt_RANNKUMOMQ&s" height="40px" width="40px"><p>Flight Bookings</p></a>
    </div>
    <div class="img-cate">
      <a href="#"><img src="https://www.store2k.com/cdn/shop/articles/store2k_blog_2_d342a3bc-141f-4ce3-a06b-bf988b9a78f4_600x.png?v=1628237346" height="40px" width="40px"><p>Grocery</p></a>
    </div>-->
  </div>

</body>
</html>
