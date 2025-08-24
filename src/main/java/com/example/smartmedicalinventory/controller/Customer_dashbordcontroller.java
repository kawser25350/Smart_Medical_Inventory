package com.example.smartmedicalinventory.controller;

import com.example.smartmedicalinventory.model.Customer;
import com.example.smartmedicalinventory.model.Medicine;
import com.example.smartmedicalinventory.model.MedicalShop;
import com.example.smartmedicalinventory.util.SystemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Customer_dashbordcontroller implements Initializable {

    @FXML
    private Label medicineCountLabel;

    @FXML
    private Label ordersCountLabel;

    @FXML
    private Label cartCountLabel;

    @FXML
    private Label profileNameLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label customerIdLabel;

    @FXML
    private VBox contentArea;

    @FXML
    private TextField quickSearchField;

    @FXML
    private TextField orderSearchField;

    private Customer customerObj;

    private List<Medicine> cart = new ArrayList<>();

    public void setCustomer(Customer customer) {
        this.customerObj = customer;


        if (userNameLabel != null) {
            userNameLabel.setText(customer.getName());
        }
        if (customerIdLabel != null) {
            customerIdLabel.setText("ID: C" + customer.getUserId());
        }
        if (profileNameLabel != null) {
            profileNameLabel.setText(customer.getName());
        }

        loadDashboardData();
    }

    private void loadDashboardData() {
        Connection conn = null;
        try {
            conn = SystemController.getConnection();


            String medicineQuery = "SELECT COUNT(*) AS total_medicines FROM Medicine";
            try (PreparedStatement stmt = conn.prepareStatement(medicineQuery);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    medicineCountLabel.setText(String.valueOf(rs.getInt("total_medicines")));
                }
            }


            String ordersQuery = "SELECT COUNT(*) AS total_orders FROM Customer_buy_history WHERE fk_customer_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(ordersQuery)) {
                stmt.setInt(1, customerObj.getUserId());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        ordersCountLabel.setText(String.valueOf(rs.getInt("total_orders")));
                    }
                }
            }


            cartCountLabel.setText(String.valueOf(cart.size()));

        } catch (Exception e) {
            e.printStackTrace();

            medicineCountLabel.setText("0");
            ordersCountLabel.setText("0");
            cartCountLabel.setText("0");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void profileMenu() {
        loadEditProfileForm();
    }

    private void loadEditProfileForm() {
        contentArea.getChildren().clear();

        Label titleLabel = new Label("Settings");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 30 0;");
        contentArea.getChildren().add(titleLabel);

        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                              "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        formContainer.setPrefWidth(700);

        VBox nameSection = new VBox(8);
        Label nameLabel = new Label("Full Name");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        TextField nameField = new TextField();
        nameField.setText(customerObj.getName());
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        nameField.setPrefHeight(40);
        nameSection.getChildren().addAll(nameLabel, nameField);

        VBox emailSection = new VBox(8);
        Label emailLabel = new Label("Email Address");
        emailLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        TextField emailField = new TextField();
        emailField.setText("customer@example.com");
        emailField.setEditable(false);
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #95a5a6; -fx-border-radius: 8; -fx-background-color: #ecf0f1;");
        emailField.setPrefHeight(40);

        Label emailNote = new Label("* Email cannot be changed for security reasons");
        emailNote.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c; -fx-font-style: italic;");

        emailSection.getChildren().addAll(emailLabel, emailField, emailNote);

        VBox contactSection = new VBox(8);
        Label contactLabel = new Label("Contact Number");
        contactLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        TextField contactField = new TextField();
        contactField.setPromptText("Enter your contact number");
        contactField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        contactField.setPrefHeight(40);
        contactSection.getChildren().addAll(contactLabel, contactField);

        VBox passwordSection = new VBox(8);
        Label passwordLabel = new Label("New Password (Optional)");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter new password or leave blank");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #3a506b; -fx-border-radius: 8;");
        passwordField.setPrefHeight(40);
        passwordSection.getChildren().addAll(passwordLabel, passwordField);

        HBox buttonSection = new HBox(15);
        buttonSection.setStyle("-fx-alignment: center;");

        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: linear-gradient(to right, #3a506b, #5bc0be); " +
                           "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; " +
                           "-fx-background-radius: 8; -fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 40; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");
        saveButton.setOnAction(e -> {
            String newName = nameField.getText().trim();
            String newContact = contactField.getText().trim();
            String newPassword = passwordField.getText().trim();

            if (newName.isEmpty() || newContact.isEmpty()) {
                updateContentArea("Validation Error", "Name and contact number are required.");
                return;
            }

            boolean updated = customerObj.updateProfile(newName, newContact, newPassword);
            if (updated) {
                profileNameLabel.setText(newName);
                userNameLabel.setText(newName);
                updateContentArea("Success", "Profile updated successfully.");
            } else {
                updateContentArea("Error", "Failed to update profile. Please try again.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3a506b; " +
                             "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                             "-fx-border-color: #3a506b; -fx-border-radius: 8; -fx-border-width: 2; " +
                             "-fx-cursor: hand; -fx-pref-width: 150; -fx-pref-height: 40;");
        cancelButton.setOnAction(e -> {
            updateContentArea("Welcome Back!", "Click on any option from the sidebar to get started.");
        });

        buttonSection.getChildren().addAll(saveButton, cancelButton);

        formContainer.getChildren().addAll(nameSection, emailSection, contactSection, passwordSection, buttonSection);
        contentArea.getChildren().add(formContainer);
    }


    @FXML
    private void viewMedicine() {
        try {
            List<Medicine> medicines = customerObj.browseMedicinesWithPrice();
            if (medicines.isEmpty()) {
                updateContentArea("Browse Medicines", "No medicines found in the inventory.");
            } else {
                loadMedicineBoxes(medicines);
            }
        } catch (Exception e) {
            updateContentArea("Error", "Failed to fetch medicines: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadMedicineBoxes(List<Medicine> medicines) {
        contentArea.getChildren().clear();


        Label titleLabel = new Label("Browse Medicines");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);


        VBox medicineContainer = new VBox(15);
        medicineContainer.setPadding(new Insets(20));

        for (Medicine medicine : medicines) {
            HBox medicineBox = createMedicineBox(medicine);
            medicineContainer.getChildren().add(medicineBox);
        }

        contentArea.getChildren().add(medicineContainer);
    }

    private HBox createMedicineBox(Medicine medicine) {
        HBox medicineBox = new HBox(20);
        medicineBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 15; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                           "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        medicineBox.setPrefWidth(800);


        VBox infoSection = new VBox(8);
        infoSection.setPrefWidth(400);

        Label nameLabel = new Label(medicine.getName());
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label typeLabel = new Label("Type: " + medicine.getType());
        typeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        Label companyLabel = new Label("Company: " + medicine.getCompany());
        companyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        Label priceLabel = new Label("Price: ৳" + String.format("%.2f", medicine.getPricePerUnit()) + " per unit");
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        infoSection.getChildren().addAll(nameLabel, typeLabel, companyLabel, priceLabel);


        VBox buttonSection = new VBox(10);
        buttonSection.setPrefWidth(200);

        Button orderButton = new Button("Order Now");
        orderButton.setStyle("-fx-background-color: linear-gradient(to right, #3a506b, #5bc0be); " +
                           "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; " +
                           "-fx-background-radius: 8; -fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 35; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");
        orderButton.setOnAction(e -> handleOrderMedicine(medicine));

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3a506b; " +
                                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                                "-fx-border-color: #3a506b; -fx-border-radius: 8; -fx-border-width: 2; " +
                                "-fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 35; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 3, 0, 0, 2);");
        addToCartButton.setOnAction(e -> handleAddToCart(medicine));

        buttonSection.getChildren().addAll(orderButton, addToCartButton);

        medicineBox.getChildren().addAll(infoSection, buttonSection);
        return medicineBox;
    }

    private void handleOrderMedicine(Medicine medicine) {
        try {

            Connection conn = SystemController.getConnection();


            int orgId = getOrCreateDefaultOrganization(conn);
            int deptId = getOrCreateDefaultDepartment(conn, orgId);

            String insertQuery = "INSERT INTO Customer_buy_history (fk_customer_id, fk_org_id, fk_dept_id, fk_med_id, quantity, datetime) VALUES (?, ?, ?, ?, ?, NOW())";

            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setInt(1, customerObj.getUserId());
                stmt.setInt(2, orgId);
                stmt.setInt(3, deptId);


                int medicineId = getMedicineIdByName(medicine.getName(), conn);
                if (medicineId == -1) {
                    conn.close();
                    return;
                }

                stmt.setInt(4, medicineId);
                stmt.setInt(5, 1);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {

                    updateOrdersCount();
                }
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getOrCreateDefaultOrganization(Connection conn) throws SQLException {

        String checkQuery = "SELECT org_id FROM Organization WHERE name = 'Default Medical Shop'";
        try (PreparedStatement stmt = conn.prepareStatement(checkQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("org_id");
            }
        }


        String insertQuery = "INSERT INTO Organization (name, address, gmail) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, "Default Medical Shop");
            stmt.setString(2, "Default Address");
            stmt.setString(3, "default@medicalshop.com");

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }

        throw new SQLException("Failed to create or retrieve default organization");
    }

    private int getOrCreateDefaultDepartment(Connection conn, int orgId) throws SQLException {

        String checkQuery = "SELECT dept_id FROM Department WHERE name = 'Default Department' AND fk_org_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
            stmt.setInt(1, orgId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("dept_id");
                }
            }
        }


        int adminId = getOrCreateDefaultAdmin(conn, orgId);


        String insertQuery = "INSERT INTO Department (name, dept_cost, budget, fk_org_id, fk_admin_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, "Default Department");
            stmt.setInt(2, 0);
            stmt.setInt(3, 10000);
            stmt.setInt(4, orgId);
            stmt.setInt(5, adminId);

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

    private int getOrCreateDefaultAdmin(Connection conn, int orgId) throws SQLException {

        String checkQuery = "SELECT admin_id FROM Admin WHERE name = 'Default Admin' AND org_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
            stmt.setInt(1, orgId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("admin_id");
                }
            }
        }


        String insertQuery = "INSERT INTO Admin (name, gmail, contact, pwd, org_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, "Default Admin");
            stmt.setString(2, "defaultadmin@medicalshop.com");
            stmt.setString(3, "0000000000");
            stmt.setString(4, SystemController.encryptPassword("defaultpassword"));
            stmt.setInt(5, orgId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }

        throw new SQLException("Failed to create or retrieve default admin");
    }

    private int getMedicineIdByName(String medicineName, Connection conn) {
        try {
            String query = "SELECT med_id FROM Medicine WHERE med_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, medicineName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("med_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void updateOrdersCount() {
        try {
            Connection conn = SystemController.getConnection();
            String ordersQuery = "SELECT COUNT(*) AS total_orders FROM Customer_buy_history WHERE fk_customer_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(ordersQuery)) {
                stmt.setInt(1, customerObj.getUserId());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int orderCount = rs.getInt("total_orders");
                        ordersCountLabel.setText(String.valueOf(orderCount));
                    }
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAddToCart(Medicine medicine) {
        cart.add(medicine);
        cartCountLabel.setText(String.valueOf(cart.size()));
    }


    @FXML
    private void completeCart() {
        if (cart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your cart is empty.");
            alert.showAndWait();
            return;
        }
        try {
            Connection conn = SystemController.getConnection();
            int orgId = getOrCreateDefaultOrganization(conn);
            int deptId = getOrCreateDefaultDepartment(conn, orgId);

            for (Medicine medicine : cart) {
                int medicineId = getMedicineIdByName(medicine.getName(), conn);
                if (medicineId != -1) {
                    String insertQuery = "INSERT INTO Customer_buy_history (fk_customer_id, fk_org_id, fk_dept_id, fk_med_id, quantity, datetime) VALUES (?, ?, ?, ?, ?, NOW())";
                    try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                        stmt.setInt(1, customerObj.getUserId());
                        stmt.setInt(2, orgId);
                        stmt.setInt(3, deptId);
                        stmt.setInt(4, medicineId);
                        stmt.setInt(5, 1);
                        stmt.executeUpdate();
                    }
                }
            }
            cart.clear();
            cartCountLabel.setText("0");
            updateOrdersCount();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All cart items have been ordered successfully!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void orderMedicine() {
        try {
            List<String> orders = customerObj.viewOrders();
            if (orders.isEmpty()) {
                updateContentArea("My Orders", "You have not placed any orders yet.\n\n" +
                        "Start browsing medicines to place your first order!");
            } else {
                loadOrdersDisplay(orders);
            }
        } catch (Exception e) {
            updateContentArea("Error", "Failed to fetch orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadOrdersDisplay(List<String> orders) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label("My Orders");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);

        Label countLabel = new Label("Total Orders: " + orders.size());
        countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 15 0;");
        contentArea.getChildren().add(countLabel);

        // --- Add search field for orders ---
        HBox searchBox = new HBox(10);
        Label searchLabel = new Label("Search by Medicine Name:");
        searchLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #3a506b;");
        orderSearchField = new TextField();
        orderSearchField.setPromptText("Enter medicine name...");
        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> searchOrderByMedicineName());
        searchBox.getChildren().addAll(searchLabel, orderSearchField, searchBtn);
        contentArea.getChildren().add(searchBox);

        VBox ordersContainer = new VBox(20);
        ordersContainer.setPadding(new Insets(20));
        ordersContainer.setId("ordersContainer");

        for (String order : orders) {
            VBox orderBox = createOrderBox(order);
            ordersContainer.getChildren().add(orderBox);
        }

        contentArea.getChildren().add(ordersContainer);
    }


    private void searchOrderByMedicineName() {
        String searchTerm = orderSearchField.getText();
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            orderMedicine();
            return;
        }
        List<String> filteredOrders = new ArrayList<>();
        List<String> allOrders = customerObj.viewOrders();
        for (String order : allOrders) {
            if (order.toLowerCase().contains(searchTerm.trim().toLowerCase())) {
                filteredOrders.add(order);
            }
        }

        contentArea.getChildren().clear();

        Label titleLabel = new Label("My Orders (Filtered)");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);

        Label countLabel = new Label("Found: " + filteredOrders.size());
        countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 15 0;");
        contentArea.getChildren().add(countLabel);


        HBox searchBox = new HBox(10);
        Label searchLabel = new Label("Search by Medicine Name:");
        searchLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #3a506b;");
        orderSearchField = new TextField(searchTerm);
        orderSearchField.setPromptText("Enter medicine name...");
        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> searchOrderByMedicineName());
        searchBox.getChildren().addAll(searchLabel, orderSearchField, searchBtn);
        contentArea.getChildren().add(searchBox);

        VBox ordersContainer = new VBox(20);
        ordersContainer.setPadding(new Insets(20));
        for (String order : filteredOrders) {
            VBox orderBox = createOrderBox(order);
            ordersContainer.getChildren().add(orderBox);
        }
        contentArea.getChildren().add(ordersContainer);
    }

    private VBox createOrderBox(String orderDetails) {
        VBox orderBox = new VBox(10);
        orderBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 15; " +
                         "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                         "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        orderBox.setPrefWidth(800);

        Label orderLabel = new Label(orderDetails);
        orderLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-family: 'Courier New', monospace;");
        orderLabel.setWrapText(true);

        orderBox.getChildren().add(orderLabel);
        return orderBox;
    }

    @FXML
    private void logout() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/hello-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void quickSearch() {
        String searchTerm = quickSearchField.getText();
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            try {
                List<Medicine> foundMedicines = customerObj.searchMedicineWithPrice(searchTerm.trim());
                if (!foundMedicines.isEmpty()) {
                    loadSearchResults(foundMedicines, searchTerm.trim());
                } else {
                    updateContentArea("Search Results",
                            "No medicines found matching: \"" + searchTerm.trim() + "\"\n\n" +
                            "• Try searching with different keywords\n" +
                            "• Check spelling of medicine name\n" +
                            "• Try searching by medicine type (e.g., Diabetes, Antibiotic)\n" +
                            "• Try searching by company name\n\n" +
                            "Use the 'Browse Medicines' option to see all available medicines.");
                }
            } catch (Exception e) {
                updateContentArea("Search Error", "Failed to search for medicines: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            updateContentArea("Quick Search",
                "Please enter a search term to find medicines.\n\n" +
                "You can search by:\n" +
                "• Medicine name (e.g., Metformin, Insulin)\n" +
                "• Medicine type (e.g., Diabetes, Antibiotic)\n" +
                "• Company name (e.g., PharmaLife, MediCare)\n\n" +
                "The search will show all matching medicines from different companies.");
        }
    }

    private void loadSearchResults(List<Medicine> medicines, String searchTerm) {
        contentArea.getChildren().clear();


        Label titleLabel = new Label("Search Results for: \"" + searchTerm + "\"");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 10 0;");
        contentArea.getChildren().add(titleLabel);


        Label countLabel = new Label("Found " + medicines.size() + " medicine(s) matching your search");
        countLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5bc0be; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(countLabel);


        VBox medicineContainer = new VBox(15);
        medicineContainer.setPadding(new Insets(20));

        for (Medicine medicine : medicines) {
            HBox medicineBox = createMedicineBox(medicine);
            medicineContainer.getChildren().add(medicineBox);
        }

        contentArea.getChildren().add(medicineContainer);


        quickSearchField.clear();
    }

    private void updateContentArea(String title, String message) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #3a506b;");

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #5bc0be;");
        messageLabel.setWrapText(true);

        contentArea.getChildren().addAll(titleLabel, messageLabel);
    }

    @FXML
    private void medicalShop() {
        try {
            List<MedicalShop> medicalShops = customerObj.viewMedicalShopsWithDetails();
            if (medicalShops.isEmpty()) {
                updateContentArea("Medical Shops", "No medical shops found.");
            } else {
                loadMedicalShopBoxes(medicalShops);
            }
        } catch (Exception e) {
            updateContentArea("Error", "Failed to fetch medical shops: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadMedicalShopBoxes(List<MedicalShop> medicalShops) {
        contentArea.getChildren().clear();


        Label titleLabel = new Label("Medical Shops");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #3a506b; -fx-padding: 0 0 20 0;");
        contentArea.getChildren().add(titleLabel);


        VBox shopsContainer = new VBox(15);
        shopsContainer.setPadding(new Insets(20));

        for (MedicalShop shop : medicalShops) {
            HBox shopBox = createMedicalShopBox(shop);
            shopsContainer.getChildren().add(shopBox);
        }

        contentArea.getChildren().add(shopsContainer);
    }

    private HBox createMedicalShopBox(MedicalShop shop) {
        HBox shopBox = new HBox(20);
        shopBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                        "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        shopBox.setPrefWidth(800);


        VBox infoSection = new VBox(8);
        infoSection.setPrefWidth(500);

        Label nameLabel = new Label(shop.getName());
        nameLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label ownerLabel = new Label("Owner: " + shop.getOwner());
        ownerLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e; -fx-font-weight: bold;");

        Label addressLabel = new Label("📍 Address: " + shop.getAddress());
        addressLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
        addressLabel.setWrapText(true);

        Label emailLabel = new Label("📧 Email: " + shop.getEmail());
        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #3498db; -fx-underline: true;");

        infoSection.getChildren().addAll(nameLabel, ownerLabel, addressLabel, emailLabel);


        VBox buttonSection = new VBox(10);
        buttonSection.setPrefWidth(200);

        Button contactButton = new Button("Contact Shop");
        contactButton.setStyle("-fx-background-color: linear-gradient(to right, #3a506b, #5bc0be); " +
                              "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; " +
                              "-fx-background-radius: 8; -fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 35; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 2);");
        contactButton.setOnAction(e -> handleContactShop(shop));

        Button viewInventoryButton = new Button("View Inventory");
        viewInventoryButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3a506b; " +
                                   "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                                   "-fx-border-color: #3a506b; -fx-border-radius: 8; -fx-border-width: 2; " +
                                   "-fx-cursor: hand; -fx-pref-width: 180; -fx-pref-height: 35; " +
                                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 3, 0, 0, 2);");
        viewInventoryButton.setOnAction(e -> handleViewInventory(shop));

        buttonSection.getChildren().addAll(contactButton, viewInventoryButton);

        shopBox.getChildren().addAll(infoSection, buttonSection);
        return shopBox;
    }

    private void handleContactShop(MedicalShop shop) {

    }

    private void handleViewInventory(MedicalShop shop) {

        viewMedicine();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
