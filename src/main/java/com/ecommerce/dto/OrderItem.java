package com.ecommerce.dto;

public class OrderItem {
	    private int id;
	    private int orderId;
	    private int productId;
	    private String productName;
	    private int quantity;
	    private double price;
	    
	 // Empty constructor (needed for frameworks/libraries)
	    public OrderItem() {}

	    // Constructor without id (useful when inserting new orders)
	    public OrderItem(int orderId, int productId, int quantity, double price, String productName) {
	        this.orderId = orderId;
	        this.productId = productId;
	        this.quantity = quantity;
	        this.price = price;
	        this.productName = productName;
	    }

	    // Full constructor (when fetching from DB)
	    public OrderItem(int id, int orderId, int productId, int quantity, double price, String productName) {
	        this.id = id;
	        this.orderId = orderId;
	        this.productId = productId;
	        this.quantity = quantity;
	        this.price = price;
	        this.productName = productName;
	    }
	    // adding the details for pdf
	    public OrderItem(String productName, int quantity, double price) {
	    	this.quantity = quantity;
	        this.price = price;
	        this.productName = productName;
		}

		public int getId() {
	    	return id;
	    }
	    public void setId(int id) {
	    	this.id = id;
	    }

	    public int getOrderId() {
	    	return orderId;
	    }
	    public void setOrderId(int orderId) {
	    	this.orderId = orderId;
	    }

	    public int getProductId() {
	    	return productId; 
	    }
	    public void setProductId(int productId) {
	    	this.productId = productId;
	    }
	    public String getProductName() {
	        return productName;
	    }

	    public void setProductName(String productName) {
	        this.productName = productName;
	    }


	    public int getQuantity() {
	    	return quantity; 
	    }
	    public void setQuantity(int quantity) {
	    	this.quantity = quantity;
	    }

	    public double getPrice() {
	    	return price; 
	    }
	    public void setPrice(double price) { 
	    	this.price = price;
	    }
	    //helper method
	    public double getTotalPrice() {
	        return this.price * this.quantity;
	    }
	}



