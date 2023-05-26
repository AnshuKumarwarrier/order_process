package com.planet.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.planet.Entity.Order;

public class OrderProcessor {
	
	
//	private static String CSV_FILE_PATH ="E://INTERVIEW FOLDER/file.csv";
	private static String CSV_FILE_PATH ="C:/Users/Compu/Downloads/ordertestfile.csv";
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USERNAME = "HR";
    private static final String DB_PASSWORD = "ANSHU";
	
	// Define regular expressions for each country
    private static final String REGEX_CAMEROON = "\\(237\\)\\ ?[2368]\\d{7,8}$";
    private static final String REGEX_ETHIOPIA = "\\(251\\)\\ ?[1-59]\\d{8}$";
    private static final String REGEX_MOROCCO = "\\(212\\)\\ ?[5-9]\\d{8}$";
    private static final String REGEX_MOZAMBIQUE = "\\(258\\)\\ ?[28]\\d{7,8}$";
    private static final String REGEX_UGANDA = "\\(256\\)\\ ?\\d{9}$";

    // Map country codes to regular expressions
    private static final Map<String, String> COUNTRY_REGEX_MAP = new HashMap<>();

    static {
        COUNTRY_REGEX_MAP.put("Cameroon", REGEX_CAMEROON);
        COUNTRY_REGEX_MAP.put("Ethiopia", REGEX_ETHIOPIA);
        COUNTRY_REGEX_MAP.put("Morocco", REGEX_MOROCCO);
        COUNTRY_REGEX_MAP.put("Mozambique", REGEX_MOZAMBIQUE);
        COUNTRY_REGEX_MAP.put("Uganda", REGEX_UGANDA);
    }
   
    public static void main(String[] args) throws SQLException {
    	
    	
    	OrderProcessor processor = new OrderProcessor();
        try {
            List<Order> orders = processor.readOrdersFromCSV(CSV_FILE_PATH);
            processor.processOrders(orders);
        } catch (IOException e) {
            System.out.println("Failed to read orders from CSV: " + e.getMessage());
        }
      
    }
    
    
    public List<Order> readOrdersFromCSV(String filePath) throws IOException {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    Order order = new Order(values[0], values[1], values[2],values[3],values[4]);
                    orders.add(order);
                } else {
                    System.out.println("Invalid order: " + line);
                }
            }
        }
        return orders;
    }

    public void processOrders(List<Order> orders) throws SQLException {
    	try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            for (Order order : orders) {
                String countryCode = determineCountryCode(order.getPhoneNumber());
                String sql = "INSERT INTO orderprocessor (id, email, phone_number,parcel_weight, countryCode) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, order.getId());
                    stmt.setString(2, order.getEmail());
                    stmt.setString(3, order.getPhoneNumber());
                    stmt.setString(4, order.getParcel_weight());
                    stmt.setString(5, countryCode);
                    stmt.executeUpdate();
                    
                   System.out.println("completed"); 
                }
            }
        }
    }

    public String determineCountryCode(String phoneNumber) {
        for (Map.Entry<String, String> entry : COUNTRY_REGEX_MAP.entrySet()) {
            String country = entry.getKey();
            String regex = entry.getValue();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phoneNumber);
            if (matcher.matches()) {
                return country;
            }
        }
        return "Unknown";
    }
    
   
}