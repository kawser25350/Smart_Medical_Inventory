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

    private void updateContentArea(String title, String message) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");

        if (message.contains("\n")) {

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

        String orgName = adminObj != null ? adminObj.getOrganizationName() : "Organization";
        Label titleLabel = new Label(orgName + " - Medicine Inventory");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);

        Label countLabel = new Label("Total Medicines in " + orgName + ": " + medicines.size());
        countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(countLabel);

        if (medicines.isEmpty()) {

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

        String orgName = adminObj != null ? adminObj.getOrganizationName() : "Organization";
        Label titleLabel = new Label(orgName + " - All Managers");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);

        Label countLabel = new Label("Total Managers in " + orgName + ": " + managers.size());
        countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(countLabel);

        if (managers.isEmpty()) {

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

        VBox orgInfoSection = new VBox(8);
        Label orgInfoLabel = new Label("Organization Information");
        orgInfoLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label orgNameLabel = new Label("Organization: " + orgName);
        orgNameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #3498db; -fx-padding: 5 0 5 0;");

        Label orgIdLabel = new Label("Organization ID: " + adminObj.getOrgId());
        orgIdLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        orgInfoSection.getChildren().addAll(orgInfoLabel, orgNameLabel, orgIdLabel);

        VBox nameSection = new VBox(8);
        Label nameLabel = new Label("Manager Name");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setPromptText("Enter manager's full name");
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        nameField.setPrefHeight(40);
        nameSection.getChildren().addAll(nameLabel, nameField);

        VBox emailSection = new VBox(8);
        Label emailLabel = new Label("Email Address");
        emailLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField emailField = new javafx.scene.control.TextField();
        emailField.setPromptText("manager@example.com");
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        emailField.setPrefHeight(40);
        emailSection.getChildren().addAll(emailLabel, emailField);

        VBox contactSection = new VBox(8);
        Label contactLabel = new Label("Contact Number");
        contactLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField contactField = new javafx.scene.control.TextField();
        contactField.setPromptText("Enter 10-digit contact number");
        contactField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        contactField.setPrefHeight(40);
        contactSection.getChildren().addAll(contactLabel, contactField);

        VBox passwordSection = new VBox(8);
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.PasswordField passwordField = new javafx.scene.control.PasswordField();
        passwordField.setPromptText("Create a secure password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        passwordField.setPrefHeight(40);
        passwordSection.getChildren().addAll(passwordLabel, passwordField);

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
                updateDashboardStats();
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

        String[] parts = managerInfo.split(",");
        if (parts.length > 0) {
            String idPart = parts[0].trim();
            if (idPart.startsWith("ID: ")) {
                try {
                    int managerId = Integer.parseInt(idPart.substring(4));
                    removeButton.setOnAction(e -> {
                        if (adminObj.removeManagerById(managerId)) {
                            updateContentArea("Success", "Manager removed successfully!");
                            updateDashboardStats();
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
                    contentArea.getChildren().clear();

                    Label titleLabel = new Label("Purchase History - " + orgName);
                    titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
                    contentArea.getChildren().add(titleLabel);

                    Label countLabel = new Label("Total Purchase Records: " + history.size());
                    countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 15 0;");
                    contentArea.getChildren().add(countLabel);

                    VBox historyContainer = new VBox(15);
                    historyContainer.setPadding(new javafx.geometry.Insets(20));
                    for (String record : history) {
                        VBox recordBox = new VBox(10);
                        recordBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 18; -fx-background-radius: 15; " +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 8, 0, 0, 2); " +
                                "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
                        recordBox.setPrefWidth(800);

                        Label recordLabel = new Label(record);
                        recordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-family: 'Courier New', monospace;");
                        recordLabel.setWrapText(true);

                        recordBox.getChildren().add(recordLabel);
                        historyContainer.getChildren().add(recordBox);
                    }
                    contentArea.getChildren().add(historyContainer);
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
    private void viewSellHistory() {
        if (adminObj != null) {
            try {
                List<String> sellHistory = adminObj.viewSellHistory();
                String orgName = adminObj.getOrganizationName();
                if (sellHistory.isEmpty()) {
                    updateContentArea("Sell History - " + orgName,
                        "No sell history found for " + orgName + ".\n\n" +
                        "Sell history will appear here when customers buy medicines from your pharmacy.");
                } else {
                    contentArea.getChildren().clear();

                    Label titleLabel = new Label("Sell History - " + orgName);
                    titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
                    contentArea.getChildren().add(titleLabel);

                    Label countLabel = new Label("Total Sell Records: " + sellHistory.size());
                    countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 15 0;");
                    contentArea.getChildren().add(countLabel);

                    VBox historyContainer = new VBox(15);
                    historyContainer.setPadding(new javafx.geometry.Insets(20));
                    for (String record : sellHistory) {
                        VBox recordBox = new VBox(10);
                        recordBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 18; -fx-background-radius: 15; " +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 8, 0, 0, 2); " +
                                "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
                        recordBox.setPrefWidth(800);

                        Label recordLabel = new Label(record);
                        recordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-family: 'Courier New', monospace;");
                        recordLabel.setWrapText(true);

                        recordBox.getChildren().add(recordLabel);
                        historyContainer.getChildren().add(recordBox);
                    }
                    contentArea.getChildren().add(historyContainer);
                }
            } catch (Exception e) {
                updateContentArea("Error", "Failed to fetch sell history: " + e.getMessage());
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

            contentArea.getChildren().clear();

            Label titleLabel = new Label("System Predictions & Logs - " + orgName);
            titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
            contentArea.getChildren().add(titleLabel);

            VBox logsBox = new VBox(10);
            logsBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 18; -fx-background-radius: 15; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 8, 0, 0, 2); " +
                    "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
            logsBox.setPrefWidth(900);

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
            content.append("• Total Revenue: ৳").append(String.format("%.2f", adminObj.getOrganizationRevenue())).append("\n");
            content.append("• Customers Served: ").append(adminObj.getOrganizationCustomersCount()).append("\n");

            content.append("\nDatabase Connection: Active for Organization ").append(adminObj.getOrgId()).append("\n");
            content.append("Last System Check: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            content.append("\n\n--- System Report for ").append(orgName).append(" ---");

            Label logsLabel = new Label(content.toString());
            logsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
            logsLabel.setWrapText(true);
            logsBox.getChildren().add(logsLabel);

            contentArea.getChildren().add(logsBox);

            Label predTitle = new Label("Medicine Purchase Prediction (Next Month)");
            predTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 25 0 10 0;");
            contentArea.getChildren().add(predTitle);

            java.util.List<String> predictions = adminObj.getMedicinePurchasePredictions();
            if (predictions.isEmpty()) {
                Label noPred = new Label("No prediction data available for your organization.");
                noPred.setStyle("-fx-font-size: 16px; -fx-text-fill: #e74c3c;");
                contentArea.getChildren().add(noPred);
            } else {
                VBox predContainer = new VBox(15);
                predContainer.setPadding(new javafx.geometry.Insets(10));
                for (String pred : predictions) {
                    VBox predBox = new VBox(8);
                    predBox.setStyle("-fx-background-color: #f8fafd; -fx-padding: 16; -fx-background-radius: 12; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 1); " +
                            "-fx-border-color: #5bc0be; -fx-border-radius: 12; -fx-border-width: 1;");
                    predBox.setPrefWidth(800);

                    Label predLabel = new Label(pred);
                    predLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #222; -fx-font-family: 'Courier New', monospace;");
                    predLabel.setWrapText(true);

                    predBox.getChildren().add(predLabel);
                    predContainer.getChildren().add(predBox);
                }
                contentArea.getChildren().add(predContainer);
            }
        } else {
            updateContentArea("Error", "Admin session not initialized. Please login again.");
        }
    }

    @FXML
    private void settings() {
        if (adminObj != null) {
            String orgName = adminObj.getOrganizationName();

            contentArea.getChildren().clear();

            Label titleLabel = new Label("System Settings - " + orgName);
            titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 30 0;");
            contentArea.getChildren().add(titleLabel);

            VBox formContainer = new VBox(20);
            formContainer.setPadding(new javafx.geometry.Insets(30));
            formContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                    "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
            formContainer.setPrefWidth(700);

            VBox nameSection = new VBox(8);
            Label nameLabel = new Label("Administrator Name");
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
            nameField.setText(adminObj.getAdminName());
            nameField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
            nameField.setPrefHeight(40);
            nameSection.getChildren().addAll(nameLabel, nameField);

            VBox orgSection = new VBox(8);
            Label orgLabel = new Label("Organization Name");
            orgLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            javafx.scene.control.TextField orgField = new javafx.scene.control.TextField();
            orgField.setText(orgName);
            orgField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
            orgField.setPrefHeight(40);
            orgSection.getChildren().addAll(orgLabel, orgField);

            VBox emailSection = new VBox(8);
            Label emailLabel = new Label("Email Address");
            emailLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            javafx.scene.control.TextField emailField = new javafx.scene.control.TextField();
            emailField.setText("Email cannot be changed");
            emailField.setEditable(false);
            emailField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #95a5a6; -fx-border-radius: 8; -fx-background-color: #ecf0f1;");
            emailField.setPrefHeight(40);
            emailSection.getChildren().addAll(emailLabel, emailField);

            javafx.scene.layout.HBox buttonSection = new javafx.scene.layout.HBox(15);
            buttonSection.setStyle("-fx-alignment: center;");

            javafx.scene.control.Button saveButton = new javafx.scene.control.Button("Save Changes");
            saveButton.setStyle("-fx-background-color: linear-gradient(to right, #3a506b, #5bc0be); " +
                    "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; " +
                    "-fx-background-radius: 8; -fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 40; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");
            saveButton.setOnAction(e -> {
                String newName = nameField.getText().trim();
                String newOrgName = orgField.getText().trim();
                if (newName.isEmpty() || newOrgName.isEmpty()) {
                    updateContentArea("Validation Error", "Both fields are required.");
                    return;
                }
                boolean nameChanged = adminObj.updateAdminName(newName);
                boolean orgChanged = adminObj.updateOrganizationName(newOrgName);
                if (nameChanged && orgChanged) {
                    updateContentArea("Success", "Administrator and Organization names updated successfully.");
                    adminNameLabel.setText(newName);
                } else if (nameChanged) {
                    updateContentArea("Partial Success", "Administrator name updated. Organization name update failed.");
                    adminNameLabel.setText(newName);
                } else if (orgChanged) {
                    updateContentArea("Partial Success", "Organization name updated. Administrator name update failed.");
                } else {
                    updateContentArea("Error", "Failed to update names. Please try again.");
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

            formContainer.getChildren().addAll(nameSection, orgSection, emailSection, buttonSection);
            contentArea.getChildren().add(formContainer);
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

    @FXML
    private void profileSettings() {
        settings();
    }

    private void updateDashboardStats() {
        if (adminObj != null) {
            int totalManagers = adminObj.getTotalManagersCount();
            totalUsersLabel.setText(String.valueOf(totalManagers));

            double totalSales = adminObj.getOrganizationRevenue();
            salesLabel.setText("₹" + String.format("%.0f", totalSales));

            int totalMedicines = adminObj.getTotalMedicinesCount();
            totalMedicinesLabel.setText(String.valueOf(totalMedicines));

            logsLabel.setText("Healthy");
        } else {
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

        VBox infoSection = new VBox(8);
        infoSection.setPrefWidth(600);

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

        VBox buttonSection = new VBox(10);
        buttonSection.setPrefWidth(150);
        buttonSection.setAlignment(javafx.geometry.Pos.CENTER);

        javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Delete Manager");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-cursor: hand; -fx-pref-width: 140; -fx-pref-height: 35; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");

        try {
            int managerIdInt = Integer.parseInt(finalManagerId);
            deleteButton.setOnAction(e -> {
                if (adminObj.removeManagerById(managerIdInt)) {
                    updateContentArea("Success", "Manager '" + finalManagerName + "' has been deleted successfully!");
                    updateDashboardStats();
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

        String expiryText = "📅 Expiry: ";
        String expiryStyle = "-fx-font-size: 14px; ";
        if (medicine.getExpiryDate() != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy");
            expiryText += sdf.format(medicine.getExpiryDate());

            java.util.Date now = new java.util.Date();
            long daysDiff = (medicine.getExpiryDate().getTime() - now.getTime()) / (1000 * 60 * 60 * 24);

            if (daysDiff < 0) {
                expiryStyle += "-fx-text-fill: #e74c3c; -fx-font-weight: bold;";
            } else if (daysDiff < 30) {
                expiryStyle += "-fx-text-fill: #f39c12; -fx-font-weight: bold;";
            } else {
                expiryStyle += "-fx-text-fill: #27ae60;";
            }
        } else {
            expiryText += "1 year from now (default)";
            expiryStyle += "-fx-text-fill: #27ae60;";
        }

        Label expiryLabel = new Label(expiryText);
        expiryLabel.setStyle(expiryStyle);

        infoSection.getChildren().addAll(nameLabel, idLabel, typeLabel, companyLabel, priceLabel, quantityLabel, expiryLabel);

        VBox buttonSection = new VBox(10);
        buttonSection.setPrefWidth(200);
        buttonSection.setAlignment(javafx.geometry.Pos.CENTER);

        javafx.scene.control.Button updateButton = new javafx.scene.control.Button("Update Medicine");
        updateButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 40; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");

        final com.example.smartmedicalinventory.model.Medicine finalMedicine = medicine;

        updateButton.setOnAction(e -> {
            loadUpdateMedicineForm(finalMedicine);
        });

        javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Delete Medicine");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 40; " +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");

        final int medicineId = medicine.getId();
        final String medicineName = medicine.getName();

        deleteButton.setOnAction(e -> {
            if (adminObj.deleteMedicineFromInventory(medicineId)) {
                updateContentArea("Success", "Medicine '" + medicineName + "' has been deleted successfully from inventory!");
                updateDashboardStats();
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

        VBox nameSection = new VBox(8);
        Label nameLabel = new Label("Medicine Name");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setText(medicine.getName());
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        nameField.setPrefHeight(40);
        nameSection.getChildren().addAll(nameLabel, nameField);

        VBox typeSection = new VBox(8);
        Label typeLabel = new Label("Medicine Type");
        typeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.ComboBox<String> typeComboBox = new javafx.scene.control.ComboBox<>();
        typeComboBox.getItems().addAll("Diabetes", "Antibiotic", "Gastric", "Allergy", "Injection", "Pain Relief", "Vitamin", "Other");
        typeComboBox.setValue(medicine.getType());
        typeComboBox.setStyle("-fx-font-size: 14px; -fx-pref-height: 40;");
        typeComboBox.setPrefWidth(400);
        typeSection.getChildren().addAll(typeLabel, typeComboBox);

        VBox companySection = new VBox(8);
        Label companyLabel = new Label("Company");
        companyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField companyField = new javafx.scene.control.TextField();
        companyField.setText(medicine.getCompany());
        companyField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        companyField.setPrefHeight(40);
        companySection.getChildren().addAll(companyLabel, companyField);

        VBox quantitySection = new VBox(8);
        Label quantityLabel = new Label("Quantity");
        quantityLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        javafx.scene.control.TextField quantityField = new javafx.scene.control.TextField();
        quantityField.setText(String.format("%.0f", medicine.getQuantity()));
        quantityField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        quantityField.setPrefHeight(40);
        quantitySection.getChildren().addAll(quantityLabel, quantityField);

        Label noteLabel = new Label("Note: Price and expiry date are calculated automatically based on medicine type. Database limitations prevent manual editing.");
        noteLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d; -fx-wrap-text: true; -fx-font-style: italic;");
        noteLabel.setMaxWidth(750);

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

                double price = calculatePriceByType(type);

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
                    updateDashboardStats();
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
            medicineOversight();
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
}
