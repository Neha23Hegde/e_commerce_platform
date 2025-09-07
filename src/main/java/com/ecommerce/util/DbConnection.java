package com.ecommerce.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Utility class for managing database connections
public class DbConnection {
	public static Connection getConnect() {
		Connection con=null;
		try {
			 // Load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			 // Establish connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce","root","Vaish@0923");
		} 
		catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
		
	}

}
