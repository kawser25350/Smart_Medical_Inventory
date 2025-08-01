package com.example.smartmedicalinventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Customer_dashbordcontroller implements Initializable {

    @FXML
    private Label medicineCountLabel;

    @FXML
    private Label ordersCountLabel;

    @FXML
    private Label cartCountLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField quickSearchField;

    @FXML
    private ScrollPane mainContentPane;

    @FXML
    private VBox contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize dashboard data
        updateDashboardStats();
    }

    @FXML
    void viewMedicine(ActionEvent event) {
        // Update content area to show medicine list
        loadContent("Medicine Catalog", "Here you can view all available medicines...");
    }

    @FXML
    void searchMedicine(ActionEvent event) {
        // Update content area to show search interface
        loadContent("Search Medicine", "Enter medicine name or category to search...");
    }

    @FXML
    void medicalShop(ActionEvent event) {
        // Update content area to show shop interface
        loadContent("Medical Shop", "Browse and shop for medicines...");
    }

    @FXML
    void orderMedicine(ActionEvent event) {
        // Update content area to show order interface
        loadContent("Order Medicine", "Place your medicine orders here...");
    }

    @FXML
    void quickSearch(ActionEvent event) {
        String searchTerm = quickSearchField.getText();
        if (!searchTerm.isEmpty()) {
            loadContent("Search Results", "Searching for: " + searchTerm);
        }
    }

    @FXML
    void logout(ActionEvent event) {
        // Handle logout - redirect to login page
        System.out.println("Logout clicked");
    }

    private void updateDashboardStats() {
        // Update the dashboard statistics
        medicineCountLabel.setText("1,234");
        ordersCountLabel.setText("56");
        cartCountLabel.setText("8");
        userNameLabel.setText("John Doe");
    }

    private void loadContent(String title, String description) {
        // Clear existing content
        contentArea.getChildren().clear();

        // Add new content
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        contentArea.getChildren().addAll(titleLabel, descLabel);
    }
}
