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

public class Admin_dashbordcontroller implements Initializable {

    @FXML
    private Label managersCountLabel;

    @FXML
    private Label totalSalesLabel;

    @FXML
    private Label systemUsersLabel;

    @FXML
    private Label reportsLabel;

    @FXML
    private TextField quickSearchField;

    @FXML
    private ScrollPane mainContentPane;

    @FXML
    private VBox contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateDashboardStats();
    }

    @FXML
    void addManager(ActionEvent event) {
        loadContent("Add Manager", "Create new manager account with appropriate permissions...");
    }

    @FXML
    void removeManager(ActionEvent event) {
        loadContent("Remove Manager", "Select and remove manager accounts from the system...");
    }

    @FXML
    void showManagers(ActionEvent event) {
        loadContent("All Managers", "View all registered managers and their details...");
    }

    @FXML
    void updateManager(ActionEvent event) {
        loadContent("Update Manager", "Modify manager information and permissions...");
    }

    @FXML
    void showBuyHistory(ActionEvent event) {
        loadContent("Purchase History", "View complete purchase history and transactions...");
    }

    @FXML
    void showSellHistory(ActionEvent event) {
        loadContent("Sales History", "View sales records and customer transactions...");
    }

    @FXML
    void showSellRecord(ActionEvent event) {
        loadContent("Sales Records", "Detailed sales analytics and performance metrics...");
    }

    @FXML
    void updateSellRecord(ActionEvent event) {
        loadContent("Update Sales Records", "Modify and update sales transaction records...");
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
        System.out.println("Admin logout clicked");
        // Redirect to login page
    }

    private void updateDashboardStats() {
        managersCountLabel.setText("25");
        totalSalesLabel.setText("$45,678");
        systemUsersLabel.setText("1,567");
        reportsLabel.setText("134");
    }

    private void loadContent(String title, String description) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        contentArea.getChildren().addAll(titleLabel, descLabel);
    }
}
