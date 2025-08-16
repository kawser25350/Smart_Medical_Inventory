package com.example.smartmedicalinventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class SystemController {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smartmedicalinventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connected successfully.");
            conn.close(); // Close test connection
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }

    public static String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decryptPassword(String encryptedPassword) {
        try {
            return new String(Base64.getDecoder().decode(encryptedPassword));
        } catch (Exception e) {
            return encryptedPassword; // Return original if decoding fails
        }
    }

    public static boolean registerCustomer(String name, String email, String contact, String password) {
        Connection conn = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO Customer (name, gmail, contact, pwd) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, contact);
                stmt.setString(4, encryptPassword(password));

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println("Customer registration failed: " + e.getMessage());
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

    public static boolean registerAdminWithOrganization(String orgName, String orgAddress, String orgEmail,
                                                       String adminName, String adminEmail, String adminContact, String adminPassword) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction

            // First, insert organization
            String orgSql = "INSERT INTO Organization (name, address, gmail) VALUES (?, ?, ?)";
            int orgId;
            try (PreparedStatement orgStmt = conn.prepareStatement(orgSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orgStmt.setString(1, orgName);
                orgStmt.setString(2, orgAddress);
                orgStmt.setString(3, orgEmail);

                int rowsAffected = orgStmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return false;
                }

                // Get generated organization ID
                try (ResultSet generatedKeys = orgStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orgId = generatedKeys.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // Then, insert admin with the organization ID
            String adminSql = "INSERT INTO Admin (name, gmail, contact, pwd, org_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement adminStmt = conn.prepareStatement(adminSql)) {
                adminStmt.setString(1, adminName);
                adminStmt.setString(2, adminEmail);
                adminStmt.setString(3, adminContact);
                adminStmt.setString(4, encryptPassword(adminPassword));
                adminStmt.setInt(5, orgId);

                int rowsAffected = adminStmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit(); // Commit transaction
            return true;

        } catch (SQLException e) {
            System.out.println("Admin/Organization registration failed: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
