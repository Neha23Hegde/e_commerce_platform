package com.ecommerce.dto;


public class CartItem {
    private int pid;
    private String pname;
    private String imageUrl;
    private double price;
    private int quantity;

    // Constructor
    public CartItem(int pid, String pname, String imageUrl, double price, int quantity) {
        this.pid = pid;
        this.pname = pname;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getPid() {
    	return pid; 
    }
    public void setPid(int pid) {
    	this.pid = pid;
    }

    public String getPname() {
    	return pname;
    }
    public void setPname(String pname) {
    	this.pname = pname; 
    }

    public String getImageUrl() {
    	return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
    	this.imageUrl = imageUrl;
    }

    public double getPrice() {
    	return price;
    }
    public void setPrice(double price) { 
    	this.price = price;
    }

    public int getQuantity() { 
    	return quantity;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity; 
    }
}
