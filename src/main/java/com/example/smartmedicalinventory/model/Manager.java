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

    public void addMedicine(String medicineName, String description, double price) {
        try (Connection conn = SystemController.getConnection()) {
            String query = "INSERT INTO Inventory (medicine_name, description, price, dept_id, org_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, medicineName);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, this.deptId);
            stmt.setInt(5, this.orgId);
            stmt.executeUpdate();
            // check if the medcine as in the medcine table if not then add it
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> showAllmedicines() {
        List<String> medicines = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT DISTINCT med_name FROM Inventory WHERE fk_dept_id = ? AND fk_org_id = ?";
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

    public List<String> expiredMedicines() {
        List<String> expiredMedicines = new ArrayList<>();
        try (Connection conn = SystemController.getConnection()) {
            String query = "SELECT med_name FROM Inventory WHERE fk_dept_id = ? AND fk_org_id = ? AND expiry_date < CURDATE()";
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
            String query = "SELECT med_name FROM Inventory WHERE med_name LIKE ? AND fk_dept_id = ? AND fk_org_id = ?";
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
