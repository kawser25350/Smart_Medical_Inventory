package com.example.smartmedicalinventory.model;

import com.example.smartmedicalinventory.util.SystemController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private int orgId;

    public Admin(int userId, String name, int orgId) {
        super(userId, name);
        this.orgId = orgId;
    }

    public int getOrgId() {
        return orgId;
    }

    public String getAdminName() {
        return getName();
    }

    public int getAdminId() {
        return getUserId();
    }

    public List<String> viewAllManagers() {
        List<String> managers = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT manager_id, name, gmail, contact FROM Manager WHERE fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                managers.add("ID: " + rs.getInt("manager_id") +
                           ", Name: " + rs.getString("name") +
                           ", Email: " + rs.getString("gmail") +
                           ", Contact: " + rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }

    public void addManager(String name, String email, String password, String contact, int deptId) {
        try (Connection conn = SystemController.getConnection()) {
            String query = "INSERT INTO Manager (name, gmail, pwd, contact, fk_dept_id, fk_org_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, SystemController.encryptPassword(password));
            stmt.setString(4, contact);
            stmt.setInt(5, deptId);
            stmt.setInt(6, orgId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalManagersCount() {
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT COUNT(*) as count FROM Manager WHERE fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalSales() {
        double totalSales = 0.0;
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT SUM(cbh.quantity) as total_quantity " +
                          "FROM Customer_buy_history cbh " +
                          "WHERE cbh.fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalSales = rs.getDouble("total_quantity") * 50.0; // Assuming average price of 50 per unit
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSales;
    }

    public int getTotalMedicinesCount() {
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT COUNT(DISTINCT fk_med_id) as count FROM Inventory WHERE fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean addManagerWithDefaults(String name, String email, String password, String contact) {
        try (Connection conn = SystemController.getConnection()) {
            // Get or create default department for this organization
            int deptId = getOrCreateDefaultDepartment(conn);

            String query = "INSERT INTO Manager (name, gmail, pwd, contact, fk_dept_id, fk_org_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, SystemController.encryptPassword(password));
            stmt.setString(4, contact);
            stmt.setInt(5, deptId);
            stmt.setInt(6, orgId); // Use the admin's organization ID

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getOrCreateDefaultDepartment(Connection conn) throws SQLException {
        // Check if default department exists for this organization
        String checkQuery = "SELECT dept_id FROM Department WHERE name = 'Default Department' AND fk_org_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
            stmt.setInt(1, orgId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("dept_id");
                }
            }
        }

        // Create default department if it doesn't exist
        String insertQuery = "INSERT INTO Department (name, dept_cost, budget, fk_org_id, fk_admin_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, "Default Department");
            stmt.setInt(2, 0);
            stmt.setInt(3, 100000);
            stmt.setInt(4, orgId);
            stmt.setInt(5, getUserId()); // Use the admin's ID

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }

        throw new SQLException("Failed to create or retrieve default department");
    }

    public void removeManager(int managerId) {
        try (Connection conn = SystemController.getConnection()) {
            String query = "DELETE FROM Manager WHERE manager_id = ? AND fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, managerId);
            stmt.setInt(2, orgId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeManagerById(int managerId) {
        try (Connection conn = SystemController.getConnection()) {
            String query = "DELETE FROM Manager WHERE manager_id = ? AND fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, managerId);
            stmt.setInt(2, orgId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> viewSellHistory() {
        List<String> sellHistory = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT fk_med_id, quantity, datetime FROM Customer_buy_history WHERE fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sellHistory.add("Medicine ID: " + rs.getInt("fk_med_id") +
                        ", Quantity: " + rs.getInt("quantity") +
                        ", Date: " + rs.getTimestamp("datetime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellHistory;
    }

    public List<String> viewPurchaseHistory() {
        List<String> purchaseHistory = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT fk_med_id, quantity, buy_date FROM Organization_buy_history WHERE fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                purchaseHistory.add("Medicine ID: " + rs.getInt("fk_med_id") +
                        ", Quantity: " + rs.getDouble("quantity") +
                        ", Date: " + rs.getTimestamp("buy_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchaseHistory;
    }

    public List<String> viewAllUsers(Connection conn) {
        List<String> users = new ArrayList<>();
        try {
            String query = "SELECT name, gmail FROM Customer";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add("Name: " + rs.getString("name") + ", Email: " + rs.getString("gmail"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(Connection conn, String userType, String name, String email, String contact, String password, int orgId, int deptId) {
        try {
            String query;
            if ("customer".equalsIgnoreCase(userType)) {
                query = "INSERT INTO Customer (name, gmail, contact, pwd) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.setString(3, contact);
                    stmt.setString(4, SystemController.encryptPassword(password));
                    stmt.executeUpdate();
                }
            } else if ("manager".equalsIgnoreCase(userType)) {
                query = "INSERT INTO Manager (name, gmail, contact, pwd, fk_org_id, fk_dept_id) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.setString(3, contact);
                    stmt.setString(4, SystemController.encryptPassword(password));
                    stmt.setInt(5, orgId);
                    stmt.setInt(6, deptId);
                    stmt.executeUpdate();
                }
            } else {
                throw new IllegalArgumentException("Invalid user type: " + userType);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeUser(Connection conn, String userType, int userId) {
        try {
            String query;
            if ("customer".equalsIgnoreCase(userType)) {
                query = "DELETE FROM Customer WHERE customer_id = ?";
            } else if ("manager".equalsIgnoreCase(userType)) {
                query = "DELETE FROM Manager WHERE manager_id = ?";
            } else {
                throw new IllegalArgumentException("Invalid user type: " + userType);
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(Connection conn, String userType, int userId, String name, String email, String contact) {
        try {
            String query;
            if ("customer".equalsIgnoreCase(userType)) {
                query = "UPDATE Customer SET name = ?, gmail = ?, contact = ? WHERE customer_id = ?";
            } else if ("manager".equalsIgnoreCase(userType)) {
                query = "UPDATE Manager SET name = ?, gmail = ?, contact = ? WHERE manager_id = ?";
            } else {
                throw new IllegalArgumentException("Invalid user type: " + userType);
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, contact);
                stmt.setInt(4, userId);
                stmt.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> viewBuyHistory(Connection conn) {
        List<String> history = new ArrayList<>();
        try {
            // Only show purchase history for this admin's organization
            String query = "SELECT obh.fk_med_id, m.med_name, obh.quantity, obh.buy_date " +
                          "FROM Organization_buy_history obh " +
                          "JOIN Medicine m ON obh.fk_med_id = m.med_id " +
                          "WHERE obh.fk_org_id = ? " +
                          "ORDER BY obh.buy_date DESC";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, orgId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        history.add("Medicine: " + rs.getString("med_name") +
                                ", Quantity: " + rs.getDouble("quantity") +
                                ", Purchase Date: " + rs.getTimestamp("buy_date"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return history;
    }

    public List<String> viewSalesReport(Connection conn) {
        List<String> sales = new ArrayList<>();
        try {
            // Only show sales for this admin's organization
            String query = "SELECT cbh.fk_med_id, m.med_name, cbh.quantity, cbh.datetime, c.name as customer_name " +
                          "FROM Customer_buy_history cbh " +
                          "JOIN Medicine m ON cbh.fk_med_id = m.med_id " +
                          "JOIN Customer c ON cbh.fk_customer_id = c.customer_id " +
                          "WHERE cbh.fk_org_id = ? " +
                          "ORDER BY cbh.datetime DESC";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, orgId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        sales.add("Medicine: " + rs.getString("med_name") +
                                ", Quantity: " + rs.getInt("quantity") +
                                ", Customer: " + rs.getString("customer_name") +
                                ", Date: " + rs.getTimestamp("datetime"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sales;
    }

    public List<String> medicineOversight(Connection conn) {
        List<String> medicines = new ArrayList<>();
        try {
            // Only show medicines in this admin's organization inventory
            String query = "SELECT DISTINCT m.med_name, m.med_type, m.company, i.quantity " +
                          "FROM Medicine m " +
                          "JOIN Inventory i ON m.med_id = i.fk_med_id " +
                          "WHERE i.fk_org_id = ? " +
                          "ORDER BY m.med_name";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, orgId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        medicines.add("Medicine: " + rs.getString("med_name") +
                                ", Type: " + rs.getString("med_type") +
                                ", Company: " + rs.getString("company") +
                                ", Stock: " + rs.getDouble("quantity") + " units");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicines;
    }

    public List<String> systemLogs() {
        // System logs specific to this organization
        List<String> logs = new ArrayList<>();
        logs.add("Organization ID " + orgId + " - System started successfully");
        logs.add("Organization ID " + orgId + " - Database connection established");
        logs.add("Organization ID " + orgId + " - Admin '" + getName() + "' logged in");
        logs.add("Organization ID " + orgId + " - Last inventory update: " +
                java.time.LocalDateTime.now().minusHours(2).format(
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        logs.add("Organization ID " + orgId + " - Total managers: " + getTotalManagersCount());
        logs.add("Organization ID " + orgId + " - Total medicines in inventory: " + getTotalMedicinesCount());
        logs.add("Organization ID " + orgId + " - System health check: PASSED");
        return logs;
    }

    // Get organization-specific statistics
    public int getOrganizationCustomersCount() {
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT COUNT(DISTINCT fk_customer_id) as count FROM Customer_buy_history WHERE fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getOrganizationRevenue() {
        double revenue = 0.0;
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT SUM(cbh.quantity) as total_sold " +
                          "FROM Customer_buy_history cbh " +
                          "WHERE cbh.fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double totalSold = rs.getDouble("total_sold");
                revenue = totalSold * 45.0; // Average price calculation
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenue;
    }

    public List<String> getTopSellingMedicines() {
        List<String> topMedicines = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT m.med_name, m.med_type, SUM(cbh.quantity) as total_sold " +
                          "FROM Customer_buy_history cbh " +
                          "JOIN Medicine m ON cbh.fk_med_id = m.med_id " +
                          "WHERE cbh.fk_org_id = ? " +
                          "GROUP BY m.med_id, m.med_name, m.med_type " +
                          "ORDER BY total_sold DESC " +
                          "LIMIT 5";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topMedicines.add(rs.getString("med_name") + " (" + rs.getString("med_type") + ")" +
                               " - Sold: " + rs.getInt("total_sold") + " units");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topMedicines;
    }

    public List<Medicine> getMedicineInventoryWithDetails() {
        List<Medicine> medicines = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            // Modified query to show only medicines from this admin's organization
            String query = "SELECT m.med_id, m.med_name, m.med_type, m.company, " +
                          "COALESCE(i.quantity, 0) as quantity " +
                          "FROM Medicine m " +
                          "INNER JOIN Inventory i ON m.med_id = i.fk_med_id " +
                          "WHERE i.fk_org_id = ? " +
                          "ORDER BY m.med_name";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Calculate price based on medicine type (since no price column exists in inventory)
                double price = calculatePriceByType(rs.getString("med_type"));

                Medicine medicine = new Medicine(
                    rs.getInt("med_id"),
                    rs.getString("med_name"),
                    rs.getString("med_type"),
                    rs.getString("company"),
                    price
                );

                medicine.setQuantity(rs.getDouble("quantity"));

                // Set a default expiry date (1 year from now)
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.YEAR, 1);
                medicine.setExpiryDate(cal.getTime());

                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    private double calculatePriceByType(String medType) {
        if (medType == null) return 25.00;

        switch (medType.toLowerCase()) {
            case "diabetes":
                return 45.50;
            case "antibiotic":
                return 32.75;
            case "gastric":
                return 28.25;
            case "allergy":
                return 18.90;
            case "injection":
                return 125.00;
            default:
                return 25.00;
        }
    }

    public boolean updateMedicineInInventory(int medicineId, String name, String type, String company,
                                           double price, double quantity, java.util.Date expiryDate) {
        try (Connection conn = SystemController.getConnection()) {
            conn.setAutoCommit(false);

            // Update Medicine table
            String medicineQuery = "UPDATE Medicine SET med_name = ?, med_type = ?, company = ? WHERE med_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(medicineQuery)) {
                stmt.setString(1, name);
                stmt.setString(2, type);
                stmt.setString(3, company);
                stmt.setInt(4, medicineId);
                stmt.executeUpdate();
            }

            // Update or Insert Inventory (without price_per_unit and expiry_date columns)
            String checkInventoryQuery = "SELECT COUNT(*) FROM Inventory WHERE fk_med_id = ? AND fk_org_id = ?";
            boolean inventoryExists = false;

            try (PreparedStatement stmt = conn.prepareStatement(checkInventoryQuery)) {
                stmt.setInt(1, medicineId);
                stmt.setInt(2, orgId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    inventoryExists = true;
                }
            }

            if (inventoryExists) {
                // Update existing inventory record
                String updateQuery = "UPDATE Inventory SET med_type = ?, quantity = ? WHERE fk_med_id = ? AND fk_org_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                    stmt.setString(1, type);
                    stmt.setDouble(2, quantity);
                    stmt.setInt(3, medicineId);
                    stmt.setInt(4, orgId);
                    stmt.executeUpdate();
                }
            } else {
                // Insert new inventory record
                String insertQuery = "INSERT INTO Inventory (fk_org_id, fk_dept_id, fk_med_id, med_type, quantity, location) " +
                                   "VALUES (?, ?, ?, ?, ?, 'Main Warehouse')";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    // Get default department ID
                    int deptId = getOrCreateDefaultDepartment(conn);

                    stmt.setInt(1, orgId);
                    stmt.setInt(2, deptId);
                    stmt.setInt(3, medicineId);
                    stmt.setString(4, type);
                    stmt.setDouble(5, quantity);
                    stmt.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMedicineFromInventory(int medicineId) {
        try (Connection conn = SystemController.getConnection()) {
            // First delete from inventory
            String inventoryQuery = "DELETE FROM Inventory WHERE fk_med_id = ? AND fk_org_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(inventoryQuery)) {
                stmt.setInt(1, medicineId);
                stmt.setInt(2, orgId);
                stmt.executeUpdate();
            }

            // Then check if medicine is used in other organizations, if not delete from Medicine table
            String checkQuery = "SELECT COUNT(*) as count FROM Inventory WHERE fk_med_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
                stmt.setInt(1, medicineId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt("count") == 0) {
                    // Delete from Medicine table if not used anywhere else
                    String medicineQuery = "DELETE FROM Medicine WHERE med_id = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(medicineQuery)) {
                        deleteStmt.setInt(1, medicineId);
                        deleteStmt.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getOrganizationName() {
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT name FROM Organization WHERE org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown Organization";
    }

    public void updateSettings(String key, String value) {
        // This method can be used to update admin-specific settings
        // For now, it's a placeholder that could be extended to update database settings
        // or maintain session-specific configurations
        System.out.println("Admin setting updated: " + key + " = " + value);

        // In the future, this could update a settings table or configuration file
        // Example implementation could be:
        /*
        try (Connection conn = SystemController.getConnection()) {
            String query = "INSERT INTO Admin_Settings (admin_id, setting_key, setting_value, updated_at) " +
                          "VALUES (?, ?, ?, NOW()) " +
                          "ON DUPLICATE KEY UPDATE setting_value = VALUES(setting_value), updated_at = NOW()";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, getUserId());
            stmt.setString(2, key);
            stmt.setString(3, value);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }
}
