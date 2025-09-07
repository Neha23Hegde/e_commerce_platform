package com.ecommerce.dto;

public class Customer {
	private int userId; 
	private String name;
	private String email;
	private Long mobile;
	private String password;
	private String role;
	
	
	public Customer(String name, String email, Long mobile, String password) {
		
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
	}
	
	public Customer() {
		super();	
	}
	
	//getters and setters
	public int getUserId() {
	    return userId;
	}

	public void setUserId(int userId) {
	    this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}
