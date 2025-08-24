package com.example.smartmedicalinventory.model;

import com.example.smartmedicalinventory.util.SystemController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    private int deptId;
    private int orgId;

    public Manager(int userId, String name, int deptId, int orgId) {
        super(userId, name);
        this.deptId = deptId;
        this.orgId = orgId;
    }


    public boolean addMedicine(String medName, String medType, String company, double quantity) {
        try (Connection conn = SystemController.getConnection()) {

            int medId = -1;
            String checkMed = "SELECT med_id FROM Medicine WHERE med_name = ? AND med_type = ? AND company = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkMed)) {
                stmt.setString(1, medName);
                stmt.setString(2, medType);
                stmt.setString(3, company);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    medId = rs.getInt("med_id");
                }
            }

            if (medId == -1) {
                String insertMed = "INSERT INTO Medicine (med_name, med_type, company) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertMed, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, medName);
                    stmt.setString(2, medType);
                    stmt.setString(3, company);
                    stmt.executeUpdate();
                    ResultSet keys = stmt.getGeneratedKeys();
                    if (keys.next()) {
                        medId = keys.getInt(1);
                    }
                }
            }

            String checkInv = "SELECT quantity FROM Inventory WHERE fk_org_id = ? AND fk_dept_id = ? AND fk_med_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkInv)) {
                stmt.setInt(1, orgId);
                stmt.setInt(2, deptId);
                stmt.setInt(3, medId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {

                    String updateInv = "UPDATE Inventory SET quantity = quantity + ? WHERE fk_org_id = ? AND fk_dept_id = ? AND fk_med_id = ?";
                    try (PreparedStatement upStmt = conn.prepareStatement(updateInv)) {
                        upStmt.setDouble(1, quantity);
                        upStmt.setInt(2, orgId);
                        upStmt.setInt(3, deptId);
                        upStmt.setInt(4, medId);
                        upStmt.executeUpdate();
                    }
                } else {

                    String insertInv = "INSERT INTO Inventory (fk_org_id, fk_dept_id, fk_med_id, med_type, quantity, location) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement inStmt = conn.prepareStatement(insertInv)) {
                        inStmt.setInt(1, orgId);
                        inStmt.setInt(2, deptId);
                        inStmt.setInt(3, medId);
                        inStmt.setString(4, medType);
                        inStmt.setDouble(5, quantity);
                        inStmt.setString(6, "Main Warehouse");
                        inStmt.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteMedicine(String medName) {
        try (Connection conn = SystemController.getConnection()) {

            int medId = -1;
            String findMed = "SELECT med_id FROM Medicine WHERE med_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(findMed)) {
                stmt.setString(1, medName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    medId = rs.getInt("med_id");
                }
            }
            if (medId == -1) return false;

            String delInv = "DELETE FROM Inventory WHERE fk_org_id = ? AND fk_dept_id = ? AND fk_med_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(delInv)) {
                stmt.setInt(1, orgId);
                stmt.setInt(2, deptId);
                stmt.setInt(3, medId);
                stmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<String> showAllmedicines() {
        List<String> medicines = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT DISTINCT m.med_name " +
                           "FROM Inventory i " +
                           "JOIN Medicine m ON i.fk_med_id = m.med_id " +
                           "WHERE i.fk_dept_id = ? AND i.fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.deptId);
            stmt.setInt(2, this.orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                medicines.add(rs.getString("med_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }


    public List<String> showInventoryDetails() {
        List<String> inventory = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT m.med_name, m.med_type, m.company, i.quantity " +
                           "FROM Inventory i " +
                           "JOIN Medicine m ON i.fk_med_id = m.med_id " +
                           "WHERE i.fk_dept_id = ? AND i.fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.deptId);
            stmt.setInt(2, this.orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inventory.add("Medicine: " + rs.getString("med_name") +
                              ", Type: " + rs.getString("med_type") +
                              ", Company: " + rs.getString("company") +
                              ", Stock: " + rs.getDouble("quantity") + " units");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }


    public List<String> viewSellHistory() {
        List<String> sellHistory = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT cbh.quantity, cbh.datetime, m.med_name, m.med_type, m.company, cbh.fk_customer_id, c.name as customer_name, " +
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
                           "JOIN Customer c ON cbh.fk_customer_id = c.customer_id " +
                           "WHERE cbh.fk_org_id = ? AND cbh.fk_dept_id = ? " +
                           "ORDER BY cbh.datetime DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            stmt.setInt(2, deptId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String medicineName = rs.getString("med_name");
                String medicineType = rs.getString("med_type");
                String company = rs.getString("company");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String datetime = rs.getTimestamp("datetime").toString();
                String customerName = rs.getString("customer_name");

                String sellDetails = String.format(
                    "🏥 MEDICINE: %s\n" +
                    "📋 Type: %s | Company: %s\n" +
                    "📦 Quantity: %d units\n" +
                    "💰 Unit Price: ৳%.2f | Total: ৳%.2f\n" +
                    "🧑‍💼 Customer: %s\n" +
                    "📅 Sell Date: %s\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                    medicineName,
                    medicineType, company,
                    quantity,
                    price, (price * quantity),
                    customerName,
                    datetime
                );
                sellHistory.add(sellDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellHistory;
    }


    public List<String> viewPurchaseHistory() {
        List<String> purchaseHistory = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT obh.fk_med_id, m.med_name, m.med_type, m.company, obh.quantity, obh.buy_date " +
                           "FROM Organization_buy_history obh " +
                           "JOIN Medicine m ON obh.fk_med_id = m.med_id " +
                           "WHERE obh.fk_org_id = ? AND obh.fk_dept_id = ? " +
                           "ORDER BY obh.buy_date DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, orgId);
            stmt.setInt(2, deptId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String medicineName = rs.getString("med_name");
                String medicineType = rs.getString("med_type");
                String company = rs.getString("company");
                double quantity = rs.getDouble("quantity");
                String buyDate = rs.getTimestamp("buy_date").toString();

                String record = String.format(
                    "🏥 MEDICINE: %s\n" +
                    "📋 Type: %s | Company: %s\n" +
                    "📦 Quantity: %.0f units\n" +
                    "📅 Purchase Date: %s\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                    medicineName,
                    medicineType, company,
                    quantity,
                    buyDate
                );
                purchaseHistory.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchaseHistory;
    }

    public List<String> expiredMedicines() {
        List<String> expiredMedicines = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT m.med_name " +
                           "FROM Inventory i " +
                           "JOIN Medicine m ON i.fk_med_id = m.med_id " +
                           "WHERE i.fk_dept_id = ? AND i.fk_org_id = ? AND i.expiry_date < CURDATE()";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.deptId);
            stmt.setInt(2, this.orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expiredMedicines.add(rs.getString("med_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expiredMedicines;
    }

    public int getLowStockCount() {
        int lowStockCount = 0;
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT COUNT(*) AS low_stock_count FROM Inventory WHERE fk_dept_id = ? AND fk_org_id = ? AND quantity < 10";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.deptId);
            stmt.setInt(2, this.orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lowStockCount = rs.getInt("low_stock_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lowStockCount;
    }

    public int getUniqueCategoriesCount() {
        int categoriesCount = 0;
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT COUNT(DISTINCT med_type) AS categories_count FROM Inventory WHERE fk_dept_id = ? AND fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.deptId);
            stmt.setInt(2, this.orgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                categoriesCount = rs.getInt("categories_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoriesCount;
    }

    public List<String> SearchMedicine(String medicineName) {
        List<String> medicines = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT m.med_name FROM Inventory i JOIN Medicine m ON i.fk_med_id = m.med_id WHERE m.med_name LIKE ? AND i.fk_dept_id = ? AND i.fk_org_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + medicineName + "%");
            stmt.setInt(2, this.deptId);
            stmt.setInt(3, this.orgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                medicines.add(rs.getString("med_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }
}
