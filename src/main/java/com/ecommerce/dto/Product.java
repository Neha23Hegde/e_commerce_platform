package com.ecommerce.dto;

public class Product {

	
	private int pid;
	private String pname;
    private String feature;
    private double price;
    private String ptype;
    private int pquantity;
    private String imageUrl;

    public Product( int pid,String pname, double price, String ptype, int quantity, String imageUrl) {
        this.pid=pid;
    	this.pname = pname;
        this.price = price;
        this.ptype = ptype;
        this.pquantity = quantity;
        this.imageUrl = imageUrl;
    }
    public Product( int pid,String pname,  String feature, double price, String ptype, int quantity, String imageUrl) {
    	this.pid=pid;
    	this.pname = pname;
        this.feature=feature;
        this.price = price;
        this.ptype = ptype;
        this.pquantity = quantity;
        this.imageUrl = imageUrl;
    }
    
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
    
    public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}

    public double getPrice() { 
    	return price;
    }
    public void setPrice(double price) { 
    	this.price = price; 
    }

    public String getPtype() {
    	return ptype; 
    }
    public void setPtype(String ptype) { 
    	this.ptype = ptype; 
    }

    public int getQuantity() { 
    	return pquantity; 
    }
    public void setQuantity(int quantity) { 
    	this.pquantity = quantity;
    }

    public String getImageUrl() { 
    	return imageUrl; 
    }
    public void setImageUrl(String imageUrl) {
    	this.imageUrl = imageUrl;
    	
    }
}

