package com.example.smartmedicalinventory.model;

import com.example.smartmedicalinventory.util.SystemController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    public Customer(int userId, String name) {
        super(userId, name);
    }

    public List<Medicine> browseMedicinesWithPrice() {
        List<Medicine> medicines = new ArrayList<>();
        Connection conn = null;
        try {
            conn = SystemController.getConnection();
            String query = "SELECT m.med_id, m.med_name, m.med_type, m.company, " +
                          "CASE " +
                          "WHEN m.med_type = 'Diabetes' THEN 45.50 " +
                          "WHEN m.med_type = 'Antibiotic' THEN 32.75 " +
                          "WHEN m.med_type = 'Gastric' THEN 28.25 " +
                          "WHEN m.med_type = 'Allergy' THEN 18.90 " +
                          "WHEN m.med_type = 'Injection' THEN 125.00 " +
                          "ELSE 25.00 " +
                          "END as price " +
                          "FROM Medicine m";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medicine medicine = new Medicine(
                        rs.getInt("med_id"),
                        rs.getString("med_name"),
                        rs.getString("med_type"),
                        rs.getString("company"),
                        rs.getDouble("price")
                    );
                    medicines.add(medicine);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return medicines;
    }

    public Medicine searchMedicine(String medicineName) {
        Connection conn = null;
        try {
            conn = SystemController.getConnection();
            String query = "SELECT med_id, med_name, med_type, company FROM Medicine WHERE med_name LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + medicineName + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Medicine(
                            rs.getInt("med_id"),
                            rs.getString("med_name"),
                            rs.getString("med_type"),
                            rs.getString("company"),
                            25.00 // Default price
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<String> viewOrders() {
        List<String> orders = new ArrayList<>();
        Connection conn = null;
        try {
            conn = SystemController.getConnection();
            String query = "SELECT cbh.quantity, cbh.datetime, m.med_name, m.med_type, m.company, " +
                          "CASE " +
                          "WHEN m.med_type = 'Diabetes' THEN 45.50 " +
                          "WHEN m.med_type = 'Antibiotic' THEN 32.75 " +
                          "WHEN m.med_type = 'Gastric' THEN 28.25 " +
                          "WHEN m.med_type = 'Allergy' THEN 18.90 " +
                          "WHEN m.med_type = 'Injection' THEN 125.00 " +
                          "ELSE 25.00 " +
                          "END as price " +
                          "FROM Customer_buy_history cbh " +
                          "JOIN Medicine m ON cbh.fk_med_id = m.med_id " +
                          "WHERE cbh.fk_customer_id = ? " +
                          "ORDER BY cbh.datetime DESC";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, getUserId());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String medicineName = rs.getString("med_name");
                        String medicineType = rs.getString("med_type");
                        String company = rs.getString("company");
                        int quantity = rs.getInt("quantity");
                        double price = rs.getDouble("price");
                        String datetime = rs.getTimestamp("datetime").toString();

                        String orderDetails = String.format(
                            "🏥 MEDICINE: %s\n" +
                            "📋 Type: %s | Company: %s\n" +
                            "📦 Quantity: %d units\n" +
                            "💰 Unit Price: $%.2f | Total: $%.2f\n" +
                            "📅 Order Date: %s\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                            medicineName,
                            medicineType, company,
                            quantity,
                            price, (price * quantity),
                            datetime
                        );
                        orders.add(orderDetails);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return orders;
    }

    public List<MedicalShop> viewMedicalShopsWithDetails() {
        List<MedicalShop> medicalShops = new ArrayList<>();
        Connection conn = null;
        try {
            conn = SystemController.getConnection();
            String query = "SELECT o.name, o.address, o.gmail, " +
                          "(SELECT a.name FROM Admin a WHERE a.org_id = o.org_id LIMIT 1) as owner_name " +
                          "FROM Organization o";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MedicalShop shop = new MedicalShop(
                        rs.getString("name"),
                        rs.getString("owner_name") != null ? rs.getString("owner_name") : "Unknown Owner",
                        rs.getString("address"),
                        rs.getString("gmail")
                    );
                    medicalShops.add(shop);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return medicalShops;
    }

    public boolean placeOrder(int medicineId, int quantity, int orgId, int deptId) {
        Connection conn = null;
        try {
            conn = SystemController.getConnection();
            String query = "INSERT INTO Customer_buy_history (fk_customer_id, fk_org_id, fk_dept_id, fk_med_id, quantity, datetime) VALUES (?, ?, ?, ?, ?, NOW())";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, getUserId());
                stmt.setInt(2, orgId);
                stmt.setInt(3, deptId);
                stmt.setInt(4, medicineId);
                stmt.setInt(5, quantity);

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Medicine> searchMedicineWithPrice(String medicineName) {
        List<Medicine> medicines = new ArrayList<>();
        Connection conn = null;
        try {
            conn = SystemController.getConnection();
            String query = "SELECT m.med_id, m.med_name, m.med_type, m.company, " +
                          "CASE " +
                          "WHEN m.med_type = 'Diabetes' THEN 45.50 " +
                          "WHEN m.med_type = 'Antibiotic' THEN 32.75 " +
                          "WHEN m.med_type = 'Gastric' THEN 28.25 " +
                          "WHEN m.med_type = 'Allergy' THEN 18.90 " +
                          "WHEN m.med_type = 'Injection' THEN 125.00 " +
                          "ELSE 25.00 " +
                          "END as price " +
                          "FROM Medicine m " +
                          "WHERE m.med_name LIKE ? OR m.med_type LIKE ? OR m.company LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                String searchPattern = "%" + medicineName + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Medicine medicine = new Medicine(
                            rs.getInt("med_id"),
                            rs.getString("med_name"),
                            rs.getString("med_type"),
                            rs.getString("company"),
                            rs.getDouble("price")
                        );
                        medicines.add(medicine);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return medicines;
    }
}
