package com.example.smartmedicalinventory.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.example.smartmedicalinventory.model.Admin;
import java.util.List;

public class Admin_dashbordcontroller {

    @FXML
    private TextField quickSearchField;

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Label salesLabel;

    @FXML
    private Label totalMedicinesLabel;

    @FXML
    private Label logsLabel;

    @FXML
    private ScrollPane mainContentPane;

    @FXML
    private VBox contentArea;

    @FXML
    private Label adminNameLabel;

    @FXML
    private Label adminIdLabel;

    private Admin adminObj;
    private String adminName = "Administrator";

    public void setAdmin(Admin admin) {
        this.adminObj = admin;
        this.adminName = admin.getAdminName();
        updateDashboardStats();
        updateAdminProfile();
    }

    private void updateAdminProfile() {
        if (adminObj != null) {
            adminNameLabel.setText(adminObj.getAdminName());
            adminIdLabel.setText("ID: A" + adminObj.getAdminId());
        }
    }

    // Enhanced updateContentArea method with better formatting
    private void updateContentArea(String title, String message) {
        contentArea.getChildren().clear();

        // Title with better styling
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");

        // Content with improved readability
        if (message.contains("\n")) {
            // Format list-like content
            String[] lines = message.split("\n");
            VBox contentBox = new VBox(12);
            contentBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 25; -fx-background-radius: 15; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3);");

            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    Label lineLabel = new Label("• " + line.trim());
                    lineLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-wrap-text: true; " +
                                     "-fx-font-family: 'Segoe UI', Arial, sans-serif;");
                    lineLabel.setMaxWidth(900);
                    contentBox.getChildren().add(lineLabel);
                }
            }
            contentArea.getChildren().addAll(titleLabel, contentBox);
        } else {
            // Single message
            VBox messageBox = new VBox();
            messageBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 25; -fx-background-radius: 15; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3);");

            Label descLabel = new Label(message);
            descLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #5bc0be; -fx-wrap-text: true; " +
                             "-fx-font-family: 'Segoe UI', Arial, sans-serif;");
            descLabel.setMaxWidth(900);

            messageBox.getChildren().add(descLabel);
            contentArea.getChildren().addAll(titleLabel, messageBox);
        }
    }

    private void loadMedicineInventoryBoxes(List<com.example.smartmedicalinventory.model.Medicine> medicines) {
        contentArea.getChildren().clear();

        // Title with organization name
        String orgName = adminObj != null ? adminObj.getOrganizationName() : "Organization";
        Label titleLabel = new Label(orgName + " - Medicine Inventory");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);

        // Count subtitle
        Label countLabel = new Label("Total Medicines in " + orgName + ": " + medicines.size());
        countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(countLabel);

        if (medicines.isEmpty()) {
            // Show message when no medicines found
            VBox emptyMessageBox = new VBox(20);
            emptyMessageBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 40; -fx-background-radius: 15; " +
                                   "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                                   "-fx-alignment: center;");

            Label emptyLabel = new Label("No medicines found in your organization's inventory");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");

            Label instructionLabel = new Label("Medicines will appear here when managers add them to the inventory for " + orgName);
            instructionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6; -fx-wrap-text: true; -fx-text-alignment: center;");
            instructionLabel.setMaxWidth(600);

            emptyMessageBox.getChildren().addAll(emptyLabel, instructionLabel);
            contentArea.getChildren().add(emptyMessageBox);
            return;
        }

        // Medicines container
        VBox medicinesContainer = new VBox(15);
        medicinesContainer.setPadding(new javafx.geometry.Insets(20));

        for (com.example.smartmedicalinventory.model.Medicine medicine : medicines) {
            javafx.scene.layout.HBox medicineBox = createMedicineInventoryBox(medicine);
            medicinesContainer.getChildren().add(medicineBox);
        }

        contentArea.getChildren().add(medicinesContainer);
    }

    private void loadManagerBoxes(List<String> managers) {
        contentArea.getChildren().clear();

        // Title with organization name
        String orgName = adminObj != null ? adminObj.getOrganizationName() : "Organization";
        Label titleLabel = new Label(orgName + " - All Managers");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);

        // Count subtitle
        Label countLabel = new Label("Total Managers in " + orgName + ": " + managers.size());
        countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(countLabel);

        if (managers.isEmpty()) {
            // Show message when no managers found
            VBox emptyMessageBox = new VBox(20);
            emptyMessageBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 40; -fx-background-radius: 15; " +
                                   "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                                   "-fx-alignment: center;");

            Label emptyLabel = new Label("No managers found in your organization");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");

            Label instructionLabel = new Label("Click 'Add Manager' to add your first manager to " + orgName);
            instructionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6;");

            emptyMessageBox.getChildren().addAll(emptyLabel, instructionLabel);
            contentArea.getChildren().add(emptyMessageBox);
            return;
        }

        // Managers container
        VBox managersContainer = new VBox(15);
        managersContainer.setPadding(new javafx.geometry.Insets(20));

        for (String managerInfo : managers) {
            javafx.scene.layout.HBox managerBox = createManagerBox(managerInfo);
            managersContainer.getChildren().add(managerBox);
        }

        contentArea.getChildren().add(managersContainer);
    }

    @FXML
    private void manageUsers() {
        if (adminObj != null) {
            try {
                List<String> managers = adminObj.viewAllManagers();
                if (managers.isEmpty()) {
                    String orgName = adminObj.getOrganizationName();
                    updateContentArea("Manage Managers", "No managers found in " + orgName + ".\n\n" +
                                    "Click 'Add Manager' to add your first manager to your organization.");
                } else {
                    loadManagerBoxes(managers);
                }
            } catch (Exception e) {
                updateContentArea("Error", "Failed to fetch managers: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML


    private void loadAddManagerForm() {
        contentArea.getChildren().clear();

        String orgName = adminObj != null ? adminObj.getOrganizationName() : "Organization";
        Label titleLabel = new Label("Add New Manager to " + orgName);
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 30 0;");
        contentArea.getChildren().add(titleLabel);

        VBox formContainer = new VBox(20);
        formContainer.setPadding(new javafx.geometry.Insets(30));
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                              "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        formContainer.setPrefWidth(800);

        // Organization info display
        VBox orgInfoSection = new VBox(8);
        Label orgInfoLabel = new Label("Organization Information");
        orgInfoLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label orgNameLabel = new Label("Organization: " + orgName);
        orgNameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #3498db; -fx-padding: 5 0 5 0;");

        Label orgIdLabel = new Label("Organization ID: " + adminObj.getOrgId());
        orgIdLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        orgInfoSection.getChildren().addAll(orgInfoLabel, orgNameLabel, orgIdLabel);

        // Name Field
        VBox nameSection = new VBox(8);
        Label nameLabel = new Label("Manager Name");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setPromptText("Enter manager's full name");
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        nameField.setPrefHeight(40);
        nameSection.getChildren().addAll(nameLabel, nameField);

        // Email Field
        VBox emailSection = new VBox(8);
        Label emailLabel = new Label("Email Address");
        emailLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField emailField = new javafx.scene.control.TextField();
        emailField.setPromptText("manager@example.com");
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        emailField.setPrefHeight(40);
        emailSection.getChildren().addAll(emailLabel, emailField);

        // Contact Field
        VBox contactSection = new VBox(8);
        Label contactLabel = new Label("Contact Number");
        contactLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField contactField = new javafx.scene.control.TextField();
        contactField.setPromptText("Enter 10-digit contact number");
        contactField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        contactField.setPrefHeight(40);
        contactSection.getChildren().addAll(contactLabel, contactField);

        // Password Field
        VBox passwordSection = new VBox(8);
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.PasswordField passwordField = new javafx.scene.control.PasswordField();
        passwordField.setPromptText("Create a secure password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        passwordField.setPrefHeight(40);
        passwordSection.getChildren().addAll(passwordLabel, passwordField);

        // Buttons
        javafx.scene.layout.HBox buttonSection = new javafx.scene.layout.HBox(15);
        buttonSection.setStyle("-fx-alignment: center;");

        javafx.scene.control.Button saveButton = new javafx.scene.control.Button("Add Manager");
        saveButton.setStyle("-fx-background-color: linear-gradient(to right, #3a506b, #5bc0be); " +
                           "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; " +
                           "-fx-background-radius: 8; -fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 40; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");
        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String contact = contactField.getText().trim();
            String password = passwordField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
                updateContentArea("Validation Error", "All fields are required. Please fill in all the information.");
                return;
            }

            if (adminObj.addManagerWithDefaults(name, email, password, contact)) {
                updateContentArea("Success", "Manager added successfully to " + orgName + "!\n\n" +
                                "Name: " + name + "\n" +
                                "Email: " + email + "\n" +
                                "Contact: " + contact + "\n" +
                                "Organization: " + orgName + "\n" +
                                "Organization ID: " + adminObj.getOrgId() + "\n\n" +
                                "The manager can now login using their email and password.");
                updateDashboardStats(); // Refresh stats
            } else {
                updateContentArea("Error", "Failed to add manager to " + orgName + ". Email might already exist or there was a database error.");
            }
        });

        javafx.scene.control.Button cancelButton = new javafx.scene.control.Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3a506b; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-border-color: #3a506b; -fx-border-radius: 8; -fx-border-width: 2; " +
                             "-fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 40;");
        cancelButton.setOnAction(e -> {
            updateContentArea("Welcome Back!", "Click on any option from the sidebar to get started.");
        });

        buttonSection.getChildren().addAll(saveButton, cancelButton);
        formContainer.getChildren().addAll(orgInfoSection, nameSection, emailSection, contactSection, passwordSection, buttonSection);
        contentArea.getChildren().add(formContainer);
    }

    @FXML
    private void removeUser() {
        loadRemoveManagerForm();
    }

    private void loadRemoveManagerForm() {
        if (adminObj != null) {
            try {
                List<String> managers = adminObj.viewAllManagers();
                if (managers.isEmpty()) {
                    updateContentArea("Remove Manager", "No managers found to remove.");
                    return;
                }

                contentArea.getChildren().clear();

                Label titleLabel = new Label("Remove Manager");
                titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 30 0;");
                contentArea.getChildren().add(titleLabel);

                VBox managersContainer = new VBox(15);
                managersContainer.setPadding(new javafx.geometry.Insets(20));

                for (String managerInfo : managers) {
                    VBox managerBox = createRemovableManagerBox(managerInfo);
                    managersContainer.getChildren().add(managerBox);
                }

                contentArea.getChildren().add(managersContainer);

            } catch (Exception e) {
                updateContentArea("Error", "Failed to load managers: " + e.getMessage());
            }
        }
    }

    private VBox createRemovableManagerBox(String managerInfo) {
        VBox managerBox = new VBox(10);
        managerBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 15; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                          "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");

        Label infoLabel = new Label(managerInfo);
        infoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        javafx.scene.control.Button removeButton = new javafx.scene.control.Button("Remove Manager");
        removeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                             "-fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 6; " +
                             "-fx-cursor: hand; -fx-pref-width: 140; -fx-pref-height: 30;");

        // Extract manager ID from the info string
        String[] parts = managerInfo.split(",");
        if (parts.length > 0) {
            String idPart = parts[0].trim(); // "ID: 123"
            if (idPart.startsWith("ID: ")) {
                try {
                    int managerId = Integer.parseInt(idPart.substring(4));
                    removeButton.setOnAction(e -> {
                        if (adminObj.removeManagerById(managerId)) {
                            updateContentArea("Success", "Manager removed successfully!");
                            updateDashboardStats(); // Refresh stats
                        } else {
                            updateContentArea("Error", "Failed to remove manager.");
                        }
                    });
                } catch (NumberFormatException ex) {
                    removeButton.setDisable(true);
                }
            }
        }

        managerBox.getChildren().addAll(infoLabel, removeButton);
        return managerBox;
    }

    @FXML
    private void updateUser() {
        updateContentArea("Update Manager",
            "Manager Update Functionality\n\n" +
            "This feature allows you to:\n" +
            "Update manager contact information\n" +
            "Modify manager permissions and access levels\n" +
            "Change assigned departments\n" +
            "Reset manager passwords\n\n" +
            "Feature coming soon in next update!");
    }

    @FXML
    private void viewAllUsers() {
        if (adminObj != null) {
            try {
                java.sql.Connection conn = com.example.smartmedicalinventory.util.SystemController.getConnection();
                java.util.List<String> users = adminObj.viewAllUsers(conn);
                updateContentArea("All Users", String.join("\n", users));
            } catch (Exception e) {
                updateContentArea("Error", "Failed to fetch users: " + e.getMessage());
            }
        }
    }

    @FXML
    private void viewBuyHistory() {
        if (adminObj != null) {
            try {
                java.sql.Connection conn = com.example.smartmedicalinventory.util.SystemController.getConnection();
                java.util.List<String> history = adminObj.viewBuyHistory(conn);
                conn.close();

                String orgName = adminObj.getOrganizationName();
                if (history.isEmpty()) {
                    updateContentArea("Purchase History - " + orgName,
                                    "No purchase history found for " + orgName + ".\n\n" +
                                    "Purchase history will appear here when " + orgName + " buys medicines from suppliers.\n\n" +
                                    "This section shows only purchases made by your organization.");
                } else {
                    StringBuilder content = new StringBuilder();
                    content.append("Organization: ").append(orgName).append("\n");
                    content.append("Organization ID: ").append(adminObj.getOrgId()).append("\n\n");
                    content.append("Total Purchase Records: ").append(history.size()).append("\n\n");
                    for (String record : history) {
                        content.append("• ").append(record).append("\n");
                    }
                    content.append("\n--- End of ").append(orgName).append(" Purchase History ---");
                    updateContentArea("Purchase History - " + orgName, content.toString());
                }
            } catch (Exception e) {
                updateContentArea("Error", "Failed to fetch purchase history: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML
    private void viewSalesReport() {
        if (adminObj != null) {
            try {
                java.sql.Connection conn = com.example.smartmedicalinventory.util.SystemController.getConnection();
                java.util.List<String> sales = adminObj.viewSalesReport(conn);
                conn.close();

                String orgName = adminObj.getOrganizationName();
                if (sales.isEmpty()) {
                    updateContentArea("Sales Report - " + orgName,
                                    "No sales data found for " + orgName + ".\n\n" +
                                    "Sales data will appear here when customers purchase medicines from " + orgName + ".\n\n" +
                                    "This report shows only sales made by your organization to customers.");
                } else {
                    StringBuilder content = new StringBuilder();
                    content.append("Organization: ").append(orgName).append("\n");
                    content.append("Organization ID: ").append(adminObj.getOrgId()).append("\n\n");
                    content.append("Total Sales Records: ").append(sales.size()).append("\n");
                    content.append("Total Revenue: ₹").append(String.format("%.2f", adminObj.getOrganizationRevenue())).append("\n");
                    content.append("Unique Customers Served: ").append(adminObj.getOrganizationCustomersCount()).append("\n\n");

                    // Top selling medicines
                    List<String> topMedicines = adminObj.getTopSellingMedicines();
                    if (!topMedicines.isEmpty()) {
                        content.append("Top Selling Medicines:\n");
                        for (String medicine : topMedicines) {
                            content.append("• ").append(medicine).append("\n");
                        }
                        content.append("\n");
                    }

                    content.append("Detailed Sales Records:\n");
                    for (String sale : sales) {
                        content.append("• ").append(sale).append("\n");
                    }
                    content.append("\n--- End of ").append(orgName).append(" Sales Report ---");
                    updateContentArea("Sales Report - " + orgName, content.toString());
                }
            } catch (Exception e) {
                updateContentArea("Error", "Failed to fetch sales report: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML
    private void medicineOversight() {
        if (adminObj != null) {
            try {
                // Get both detailed medicine objects and overview strings
                List<com.example.smartmedicalinventory.model.Medicine> medicines = adminObj.getMedicineInventoryWithDetails();
                loadMedicineInventoryBoxes(medicines);
            } catch (Exception e) {
                updateContentArea("Error", "Failed to fetch medicine inventory: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML
    private void systemLogs() {
        if (adminObj != null) {
            java.util.List<String> logs = adminObj.systemLogs();
            String orgName = adminObj.getOrganizationName();

            StringBuilder content = new StringBuilder();
            content.append("System Status for: ").append(orgName).append("\n");
            content.append("Organization ID: ").append(adminObj.getOrgId()).append("\n\n");
            content.append("System Health Status: OPERATIONAL\n\n");

            content.append("Organization-Specific System Activity:\n");
            for (String log : logs) {
                content.append("• ").append(log).append("\n");
            }

            content.append("\nReal-time Statistics:\n");
            content.append("• Active Managers: ").append(adminObj.getTotalManagersCount()).append("\n");
            content.append("• Medicines in Inventory: ").append(adminObj.getTotalMedicinesCount()).append("\n");
            content.append("• Total Revenue: ₹").append(String.format("%.2f", adminObj.getOrganizationRevenue())).append("\n");
            content.append("• Customers Served: ").append(adminObj.getOrganizationCustomersCount()).append("\n");

            content.append("\nDatabase Connection: Active for Organization ").append(adminObj.getOrgId()).append("\n");
            content.append("Last System Check: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            content.append("\n\n--- System Report for ").append(orgName).append(" ---");

            updateContentArea("System Predictions & Logs - " + orgName, content.toString());
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML
    private void settings() {
        if (adminObj != null) {
            adminObj.updateSettings("last_access", java.time.LocalDateTime.now().toString());
            String orgName = adminObj.getOrganizationName();

            StringBuilder content = new StringBuilder();
            content.append("System Configuration for: ").append(orgName).append("\n\n");
            content.append("Organization Details:\n");
            content.append("Organization Name: ").append(orgName).append("\n");
            content.append("Organization ID: ").append(adminObj.getOrgId()).append("\n");
            content.append("Administrator: ").append(adminObj.getAdminName()).append("\n");
            content.append("Admin ID: A").append(adminObj.getAdminId()).append("\n");
            content.append("Last Access: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");

            content.append("Organization Statistics:\n");
            content.append("Total Managers: ").append(adminObj.getTotalManagersCount()).append("\n");
            content.append("Medicines in Inventory: ").append(adminObj.getTotalMedicinesCount()).append("\n");
            content.append("Monthly Revenue: ₹").append(String.format("%.2f", adminObj.getOrganizationRevenue())).append("\n");
            content.append("Customers Served: ").append(adminObj.getOrganizationCustomersCount()).append("\n\n");

            content.append("System Preferences for ").append(orgName).append(":\n");
            content.append("Auto-backup: Enabled\n");
            content.append("Security Level: High\n");
            content.append("Session Timeout: 30 minutes\n");
            content.append("Audit Logging: Active for Organization ").append(adminObj.getOrgId()).append("\n");
            content.append("Data Isolation: Enabled (Organization-specific data only)");

            updateContentArea("System Settings - " + orgName, content.toString());
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML
    private void quickSearch() {
        String searchTerm = quickSearchField.getText();
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            if (adminObj != null) {
                String orgName = adminObj.getOrganizationName();
                StringBuilder content = new StringBuilder();
                content.append("Search Results for: \"").append(searchTerm.trim()).append("\" in ").append(orgName).append("\n\n");
                content.append("Organization ID: ").append(adminObj.getOrgId()).append("\n\n");
                content.append("Searching across ").append(orgName).append(" data only:\n");
                content.append("• Manager records and contact information\n");
                content.append("• Medicine inventory and specifications\n");
                content.append("• Sales transactions to customers\n");
                content.append("• Purchase records from suppliers\n");
                content.append("• Organization-specific system logs\n\n");
                content.append("Advanced search functionality coming soon!\n");
                content.append("This will help you quickly find information specific to ").append(orgName).append(".");

                updateContentArea("Advanced Search - " + orgName, content.toString());
            } else {
                updateContentArea("Advanced Search", "Admin session not initialized. Please login again.");
            }
            quickSearchField.clear();
        } else {
            if (adminObj != null) {
                String orgName = adminObj.getOrganizationName();
                updateContentArea("Quick Search - " + orgName,
                    "Please enter a search term to find information within " + orgName + ".\n\n" +
                    "You can search for:\n" +
                    "• Manager names and contact details within your organization\n" +
                    "• Medicine names, types, and companies in your inventory\n" +
                    "• Transaction records and dates for your organization\n" +
                    "• System events and logs specific to " + orgName + "\n\n" +
                    "Enter your search term in the field above and click Search.\n\n" +
                    "Note: Search results are filtered to show only " + orgName + " data.");
            }
        }
    }

    @FXML
    private void editProfile() {
        if (adminObj != null) {
            String orgName = adminObj.getOrganizationName();
            StringBuilder content = new StringBuilder();
            content.append("Administrator Profile Management - ").append(orgName).append("\n\n");
            content.append("Current Administrator Information:\n");
            content.append("Name: ").append(adminName != null ? adminName : "Administrator").append("\n");
            content.append("Admin ID: A").append(adminObj.getAdminId()).append("\n");
            content.append("Organization: ").append(orgName).append("\n");
            content.append("Organization ID: ").append(adminObj.getOrgId()).append("\n");
            content.append("Role: System Administrator for ").append(orgName).append("\n");
            content.append("Access Level: Full Administrative Rights within Organization\n\n");
            content.append("Available Profile Actions:\n");
            content.append("• Update administrator name and contact details\n");
            content.append("• Change profile picture and avatar settings\n");
            content.append("• Modify system preferences for ").append(orgName).append("\n");
            content.append("• Update security settings and password\n");
            content.append("• Configure organization details and information\n");
            content.append("• Manage organization-specific settings and policies");

            updateContentArea("Edit Admin Profile - " + orgName, content.toString());
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML
    private void profileSettings() {
        if (adminObj != null) {
            String orgName = adminObj.getOrganizationName();
            StringBuilder content = new StringBuilder();
            content.append("System Administrator Configuration - ").append(orgName).append("\n\n");
            content.append("Current Settings Overview:\n");
            content.append("Admin ID: A").append(adminObj.getAdminId()).append("\n");
            content.append("Organization: ").append(orgName).append("\n");
            content.append("Organization ID: ").append(adminObj.getOrgId()).append("\n");
            content.append("Last Login: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            content.append("Access Status: Active Administrator for ").append(orgName).append("\n\n");
            content.append("Organization-Specific Administrative Controls:\n");
            content.append("• System security configuration and monitoring\n");
            content.append("• Manager access management within ").append(orgName).append("\n");
            content.append("• Organization data backup settings and scheduling\n");
            content.append("• Audit trail configuration for organization activities\n");
            content.append("• System monitoring preferences specific to ").append(orgName).append("\n");
            content.append("• Emergency access protocols and recovery procedures\n");
            content.append("• Data isolation settings (ensuring only ").append(orgName).append(" data access)");

            updateContentArea("Administrator Settings - " + orgName, content.toString());
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    // Enhanced updateDashboardStats method with organization-specific data
    private void updateDashboardStats() {
        if (adminObj != null) {
            // Update with real data from database - organization specific
            int totalManagers = adminObj.getTotalManagersCount();
            totalUsersLabel.setText(String.valueOf(totalManagers));

            double totalSales = adminObj.getOrganizationRevenue();
            salesLabel.setText("₹" + String.format("%.0f", totalSales));

            int totalMedicines = adminObj.getTotalMedicinesCount();
            totalMedicinesLabel.setText(String.valueOf(totalMedicines));

            logsLabel.setText("Healthy");
        } else {
            // Default values if admin object is not available
            totalUsersLabel.setText("0");
            salesLabel.setText("₹0");
            totalMedicinesLabel.setText("0");
            logsLabel.setText("Offline");
        }
    }

    @FXML
    private void addUser() {
        loadAddManagerForm();
    }

    private javafx.scene.layout.HBox createManagerBox(String managerInfo) {
        javafx.scene.layout.HBox managerBox = new javafx.scene.layout.HBox(20);
        managerBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 15; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                          "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        managerBox.setPrefWidth(800);

        // Manager Info Section
        VBox infoSection = new VBox(8);
        infoSection.setPrefWidth(600);

        // Parse manager info: "ID: 1, Name: John Doe, Email: john@example.com, Contact: 1234567890"
        String[] parts = managerInfo.split(",");
        String managerId = "";
        String managerName = "";
        String managerEmail = "";
        String managerContact = "";

        for (String part : parts) {
            String trimmedPart = part.trim();
            if (trimmedPart.startsWith("ID: ")) {
                managerId = trimmedPart.substring(4);
            } else if (trimmedPart.startsWith("Name: ")) {
                managerName = trimmedPart.substring(6);
            } else if (trimmedPart.startsWith("Email: ")) {
                managerEmail = trimmedPart.substring(7);
            } else if (trimmedPart.startsWith("Contact: ")) {
                managerContact = trimmedPart.substring(9);
            }
        }

        // Make variables effectively final by creating final copies
        final String finalManagerId = managerId;
        final String finalManagerName = managerName;

        Label nameLabel = new Label(managerName);
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label idLabel = new Label("Manager ID: " + managerId);
        idLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d; -fx-font-weight: bold;");

        Label emailLabel = new Label("📧 Email: " + managerEmail);
        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #3498db;");

        Label contactLabel = new Label("📱 Contact: " + managerContact);
        contactLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #27ae60;");

        Label departmentLabel = new Label("🏢 Department: Default Department");
        departmentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #9b59b6;");

        infoSection.getChildren().addAll(nameLabel, idLabel, emailLabel, contactLabel, departmentLabel);

        // Action Button Section
        VBox buttonSection = new VBox(10);
        buttonSection.setPrefWidth(150);
        buttonSection.setAlignment(javafx.geometry.Pos.CENTER);

        javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Delete Manager");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-cursor: hand; -fx-pref-width: 140; -fx-pref-height: 35; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");

        // Extract manager ID for deletion using final variables
        try {
            int managerIdInt = Integer.parseInt(finalManagerId);
            deleteButton.setOnAction(e -> {
                if (adminObj.removeManagerById(managerIdInt)) {
                    updateContentArea("Success", "Manager '" + finalManagerName + "' has been deleted successfully!");
                    updateDashboardStats(); // Refresh stats
                } else {
                    updateContentArea("Error", "Failed to delete manager. Please try again.");
                }
            });
        } catch (NumberFormatException ex) {
            deleteButton.setDisable(true);
            deleteButton.setText("Invalid ID");
        }

        buttonSection.getChildren().add(deleteButton);

        managerBox.getChildren().addAll(infoSection, buttonSection);
        return managerBox;
    }

    private javafx.scene.layout.HBox createMedicineInventoryBox(com.example.smartmedicalinventory.model.Medicine medicine) {
        javafx.scene.layout.HBox medicineBox = new javafx.scene.layout.HBox(20);
        medicineBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 15; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                           "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        medicineBox.setPrefWidth(900);

        // Medicine Info Section
        VBox infoSection = new VBox(8);
        infoSection.setPrefWidth(600);

        Label nameLabel = new Label(medicine.getName());
        nameLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label idLabel = new Label("Medicine ID: " + medicine.getId());
        idLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d; -fx-font-weight: bold;");

        Label typeLabel = new Label("💊 Type: " + medicine.getType());
        typeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #8e44ad;");

        Label companyLabel = new Label("🏢 Company: " + medicine.getCompany());
        companyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #3498db;");

        Label priceLabel = new Label("💰 Price: ₹" + String.format("%.2f", medicine.getPricePerUnit()) + " per unit");
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        Label quantityLabel = new Label("📦 Quantity: " + String.format("%.0f", medicine.getQuantity()) + " units");
        quantityLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #f39c12;");

        // Format expiry date with better handling
        String expiryText = "📅 Expiry: ";
        String expiryStyle = "-fx-font-size: 14px; ";
        if (medicine.getExpiryDate() != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy");
            expiryText += sdf.format(medicine.getExpiryDate());

            // Check if medicine is expired or expiring soon
            java.util.Date now = new java.util.Date();
            long daysDiff = (medicine.getExpiryDate().getTime() - now.getTime()) / (1000 * 60 * 60 * 24);

            if (daysDiff < 0) {
                expiryStyle += "-fx-text-fill: #e74c3c; -fx-font-weight: bold;"; // Red for expired
            } else if (daysDiff < 30) {
                expiryStyle += "-fx-text-fill: #f39c12; -fx-font-weight: bold;"; // Orange for expiring soon
            } else {
                expiryStyle += "-fx-text-fill: #27ae60;"; // Green for good
            }
        } else {
            expiryText += "1 year from now (default)";
            expiryStyle += "-fx-text-fill: #27ae60;";
        }

        Label expiryLabel = new Label(expiryText);
        expiryLabel.setStyle(expiryStyle);

        infoSection.getChildren().addAll(nameLabel, idLabel, typeLabel, companyLabel, priceLabel, quantityLabel, expiryLabel);

        // Action Button Section
        VBox buttonSection = new VBox(10);
        buttonSection.setPrefWidth(200);
        buttonSection.setAlignment(javafx.geometry.Pos.CENTER);

        // Update Button
        javafx.scene.control.Button updateButton = new javafx.scene.control.Button("Update Medicine");
        updateButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 40; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");

        // Store medicine for update
        final com.example.smartmedicalinventory.model.Medicine finalMedicine = medicine;

        updateButton.setOnAction(e -> {
            loadUpdateMedicineForm(finalMedicine);
        });

        // Delete Button
        javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Delete Medicine");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 40; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");

        // Store medicine ID and name for deletion
        final int medicineId = medicine.getId();
        final String medicineName = medicine.getName();

        deleteButton.setOnAction(e -> {
            if (adminObj.deleteMedicineFromInventory(medicineId)) {
                updateContentArea("Success", "Medicine '" + medicineName + "' has been deleted successfully from inventory!");
                updateDashboardStats(); // Refresh stats
            } else {
                updateContentArea("Error", "Failed to delete medicine '" + medicineName + "'. Please try again.");
            }
        });

        buttonSection.getChildren().addAll(updateButton, deleteButton);

        medicineBox.getChildren().addAll(infoSection, buttonSection);
        return medicineBox;
    }

    private void loadUpdateMedicineForm(com.example.smartmedicalinventory.model.Medicine medicine) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label("Update Medicine - " + medicine.getName());
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 30 0;");
        contentArea.getChildren().add(titleLabel);

        VBox formContainer = new VBox(20);
        formContainer.setPadding(new javafx.geometry.Insets(30));
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                              "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        formContainer.setPrefWidth(800);

        // Medicine Name Field
        VBox nameSection = new VBox(8);
        Label nameLabel = new Label("Medicine Name");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setText(medicine.getName());
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        nameField.setPrefHeight(40);
        nameSection.getChildren().addAll(nameLabel, nameField);

        // Medicine Type Field
        VBox typeSection = new VBox(8);
        Label typeLabel = new Label("Medicine Type");
        typeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.ComboBox<String> typeComboBox = new javafx.scene.control.ComboBox<>();
        typeComboBox.getItems().addAll("Diabetes", "Antibiotic", "Gastric", "Allergy", "Injection", "Pain Relief", "Vitamin", "Other");
        typeComboBox.setValue(medicine.getType());
        typeComboBox.setStyle("-fx-font-size: 14px; -fx-pref-height: 40;");
        typeComboBox.setPrefWidth(400);
        typeSection.getChildren().addAll(typeLabel, typeComboBox);

        // Company Field
        VBox companySection = new VBox(8);
        Label companyLabel = new Label("Company");
        companyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField companyField = new javafx.scene.control.TextField();
        companyField.setText(medicine.getCompany());
        companyField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        companyField.setPrefHeight(40);
        companySection.getChildren().addAll(companyLabel, companyField);

        // Quantity Field
        VBox quantitySection = new VBox(8);
        Label quantityLabel = new Label("Quantity");
        quantityLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField quantityField = new javafx.scene.control.TextField();
        quantityField.setText(String.format("%.0f", medicine.getQuantity()));
        quantityField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        quantityField.setPrefHeight(40);
        quantitySection.getChildren().addAll(quantityLabel, quantityField);

        // Note about limitations
        Label noteLabel = new Label("Note: Price and expiry date are calculated automatically based on medicine type. Database limitations prevent manual editing.");
        noteLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d; -fx-wrap-text: true; -fx-font-style: italic;");
        noteLabel.setMaxWidth(750);

        // Buttons
        javafx.scene.layout.HBox buttonSection = new javafx.scene.layout.HBox(15);
        buttonSection.setStyle("-fx-alignment: center;");

        javafx.scene.control.Button saveButton = new javafx.scene.control.Button("Update Medicine");
        saveButton.setStyle("-fx-background-color: linear-gradient(to right, #3a506b, #5bc0be); " +
                           "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; " +
                           "-fx-background-radius: 8; -fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 40; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");
        saveButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String type = typeComboBox.getValue();
                String company = companyField.getText().trim();
                double quantity = Double.parseDouble(quantityField.getText().trim());

                if (name.isEmpty() || type == null || company.isEmpty()) {
                    updateContentArea("Validation Error", "Please fill in all required fields.");
                    return;
                }

                // Calculate price based on type
                double price = calculatePriceByType(type);

                // Use current date + 1 year as expiry
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.YEAR, 1);
                java.util.Date expiryDate = cal.getTime();

                if (adminObj.updateMedicineInInventory(medicine.getId(), name, type, company, price, quantity, expiryDate)) {
                    updateContentArea("Success", "Medicine '" + name + "' has been updated successfully!\n\n" +
                                    "Updated Details:\n" +
                                    "Name: " + name + "\n" +
                                    "Type: " + type + "\n" +
                                    "Company: " + company + "\n" +
                                    "Price: ₹" + String.format("%.2f", price) + " per unit (auto-calculated)\n" +
                                    "Quantity: " + String.format("%.0f", quantity) + " units\n" +
                                    "Expiry Date: 1 year from now (default)");
                    updateDashboardStats(); // Refresh stats
                } else {
                    updateContentArea("Error", "Failed to update medicine. Please try again.");
                }
            } catch (NumberFormatException ex) {
                updateContentArea("Validation Error", "Please enter a valid number for quantity.");
            } catch (Exception ex) {
                updateContentArea("Error", "An error occurred: " + ex.getMessage());
            }
        });

        javafx.scene.control.Button cancelButton = new javafx.scene.control.Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3a506b; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-border-color: #3a506b; -fx-border-radius: 8; -fx-border-width: 2; " +
                             "-fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 40;");
        cancelButton.setOnAction(e -> {
            medicineOversight(); // Go back to medicine inventory
        });

        buttonSection.getChildren().addAll(saveButton, cancelButton);
        formContainer.getChildren().addAll(nameSection, typeSection, companySection, quantitySection, noteLabel, buttonSection);
        contentArea.getChildren().add(formContainer);
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

    @FXML
    private void logout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/hello-view.fxml"));
            javafx.scene.Parent root = fxmlLoader.load();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            javafx.stage.Stage stage = (javafx.stage.Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
