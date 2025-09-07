package com.ecommerce.dto;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int userId;
    private int productId;
    private int quantity;
    private int addressId;
    private String paymentMethod;
    private double totalAmount;
    private String status;
    private Timestamp orderDate;
    private String imageUrl;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
    	return orderId; 
    }
    public void setOrderId(int orderId) {
    	this.orderId = orderId; 
    }
    public int getUserId() {
    	return userId; 
    }
    public void setUserId(int userId) {
    	this.userId = userId; 
    }
    public int getAddressId() { 
    	return addressId; 
    }
    public void setAddressId(int addressId) {
    	this.addressId = addressId; 
    }
    public String getPaymentMethod() {
    	return paymentMethod; 
    }
    public void setPaymentMethod(String paymentMethod) { 
    	this.paymentMethod = paymentMethod; 
    }
    public double getTotalAmount() { 
    	return totalAmount; 
    }
    public void setTotalAmount(double totalAmount) {
    	this.totalAmount = totalAmount; 
    }
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
