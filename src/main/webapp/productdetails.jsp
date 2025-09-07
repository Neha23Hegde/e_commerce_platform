<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SwiftShop - Product Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/productdetails.css" rel="stylesheet">
</head>
<body>
    <div class="header text-center my-3">
        <h1>SwiftShop</h1>
        <div class="tagline">Fast and Convenient Shopping</div>
    </div>

    <c:if test="${not empty product}">
        <div class="container d-flex justify-content-center">
            <div class="product-card card p-3 shadow-lg" style="max-width: 1000px;">
                <div class="row g-3">
                    <!-- Product Image -->
                    <div class="col-md-5 d-flex align-items-center">
                        <img src="${product.imageUrl}" alt="${product.pname}" class="img-fluid rounded">
                    </div>

                    <!-- Product Info -->
                    <div class="col-md-7">
                        <h2 class="mb-3">${product.pname}</h2>
                        <p>Feature: ${product.feature}</p>
                        <p>Type: ${product.ptype}</p>
                        <p>Stock: <span id="stock_${product.pid}">${product.quantity}</span> left</p>
                        <p>₹<span id="price_${product.pid}">${product.price}</span></p>

                        <!-- Add to Cart Form -->
                        <form action="CartServlet" method="post" class="mt-3">
                            <input type="hidden" name="pid" value="${product.pid}">
                            <input type="hidden" name="price" value="${product.price}">
                            <input type="hidden" name="pname" value="${product.pname}">
                            <input type="hidden" name="imageUrl" value="${product.imageUrl}">

                            <div class="d-flex align-items-center mb-3">
                                <button type="button" class="btn btn-sm btn-secondary me-2"
                                        onclick="decreaseQty('${product.pid}', ${product.quantity}, ${product.price})">-</button>

                                <input type="text" id="qty_${product.pid}"
                                       name="quantity" value="1" class="form-control text-center me-2" style="width:60px;" readonly>

                                <button type="button" class="btn btn-sm btn-secondary"
                                        onclick="increaseQty('${product.pid}', ${product.quantity}, ${product.price})">+</button>
                            </div>

                            <div class="d-flex gap-2">
                                <a href="home.jsp" class="btn btn-primary">Back to Products</a>
                                <button type="submit" class="btn btn-warning">Add to Cart</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${empty product}">
        <div class="alert alert-danger text-center mt-5" role="alert">
            Product not found!
        </div>
    </c:if>

    <script>
        function increaseQty(pid, maxStock, unitPrice) {
            let qty = document.getElementById("qty_" + pid);
            let stockEl = document.getElementById("stock_" + pid);
            let priceEl = document.getElementById("price_" + pid);

            let currentQty = parseInt(qty.value);
            let stock = parseInt(stockEl.innerText);

            if (currentQty < maxStock) {
                qty.value = currentQty + 1;
                stockEl.innerText = stock - 1;
                priceEl.innerText = unitPrice * (currentQty + 1);
            }
        }

        function decreaseQty(pid, maxStock, unitPrice) {
            let qty = document.getElementById("qty_" + pid);
            let stockEl = document.getElementById("stock_" + pid);
            let priceEl = document.getElementById("price_" + pid);

            let currentQty = parseInt(qty.value);

            if (currentQty > 1) {
                qty.value = currentQty - 1;
                stockEl.innerText = parseInt(stockEl.innerText) + 1;
                priceEl.innerText = unitPrice * (currentQty - 1);
            }
        }
    </script>
</body>
</html>
