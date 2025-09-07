package com.ecommerce.dao;

import com.ecommerce.dto.Product;
import com.ecommerce.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	// Fetch products by category
    private static final String SELECT_PRODUCTS_BY_CATEGORY =
        "SELECT pid, pname, price, ptype, pquantity, image_url FROM products WHERE UPPER(ptype) = UPPER(?)";
    //Fetch a single product by its ID
    private static final String SELECT_BY_ID =
        "SELECT pid, pname, feature, price, ptype, pquantity, image_url FROM products WHERE pid = ?";

   //Search products by keyword (name, feature, or type)
    private static final String SELECT_PRODUCTS_BY_SEARCH =
        "SELECT pid, pname, price, ptype, pquantity, image_url FROM products WHERE UPPER(pname) LIKE UPPER(?)";

  //Fetch random products
    private static final String SELECT_RANDOM_PRODUCTS =
        "SELECT pid, pname, price, ptype, pquantity, image_url FROM products ORDER BY RAND() LIMIT ?";

    public static List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();

        try (Connection conn = DbConnection.getConnect();
             PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUCTS_BY_CATEGORY)) {
        	
        	//Set category parameter (case-insensitive because of UPPER())
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            // Build Product objects from result set
            while (rs.next()) {
                int pid = rs.getInt("pid");
                String pname = rs.getString("pname");
                double price = rs.getDouble("price");
                String ptype = rs.getString("ptype");
                int pquantity = rs.getInt("pquantity");
                String imageUrl = rs.getString("image_url");

                Product product = new Product(pid, pname, price, ptype, pquantity, imageUrl);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    
    //Retrieves detailed information about a single product by ID
    
    public static Product getProductDetails(int pid) {
    	
        try (Connection conn = DbConnection.getConnect();
        		
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, pid);
            ResultSet rs = stmt.executeQuery();
            
         // If a matching product is found, build and return it
            if (rs.next()) {
                int id = rs.getInt("pid");
                String pname = rs.getString("pname");
                String feature = rs.getString("feature");
                double price = rs.getDouble("price");
                String ptype = rs.getString("ptype");
                int quantity = rs.getInt("pquantity");
                String imageUrl = rs.getString("image_url");

                return new Product(id, pname, feature, price, ptype, quantity, imageUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //Retrieves a random list of products, limited by the given number
    public static List<Product> getRandomProducts(int limit) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DbConnection.getConnect();
             PreparedStatement stmt = conn.prepareStatement(SELECT_RANDOM_PRODUCTS)) {

            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int pid = rs.getInt("pid");
                String pname = rs.getString("pname");
                double price = rs.getDouble("price");
                String ptype = rs.getString("ptype");
                int pquantity = rs.getInt("pquantity");
                String imageUrl = rs.getString("image_url");

                Product product = new Product(pid, pname, price, ptype, pquantity, imageUrl);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    
    //Searches products by keyword across name, feature, and type
    public static List<Product> getProductsBySearch(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE pname LIKE ? OR feature LIKE ? OR ptype LIKE ?";

        try (Connection conn = DbConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int pid = rs.getInt("pid");
                String pname = rs.getString("pname");
                double price = rs.getDouble("price");
                String ptype = rs.getString("ptype");
                int pquantity = rs.getInt("pquantity");
                String imageUrl = rs.getString("image_url");

                Product product = new Product(pid, pname, price, ptype, pquantity, imageUrl);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

}
