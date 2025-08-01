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

public class Manager_dashbordcontroller implements Initializable {

    @FXML
    private Label totalMedicineLabel;

    @FXML
    private Label expiredMedicineLabel;

    @FXML
    private Label lowStockLabel;

    @FXML
    private Label categoriesLabel;

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
    void addMedicine(ActionEvent event) {
        loadContent("Add Medicine", "Add new medicine to the inventory with details like name, category, price, expiry date...");
    }

    @FXML
    void removeMedicine(ActionEvent event) {
        loadContent("Remove Medicine", "Remove medicine from inventory. Select medicine to delete...");
    }

    @FXML
    void showExpiredMedicine(ActionEvent event) {
        loadContent("Expired Medicine", "View all expired medicines that need to be removed from inventory...");
    }

    @FXML
    void showAllMedicine(ActionEvent event) {
        loadContent("All Medicine", "Complete list of all medicines in the inventory with stock levels...");
    }

    @FXML
    void updateMedicine(ActionEvent event) {
        loadContent("Update Medicine", "Modify medicine information including price, stock quantity, and details...");
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
        System.out.println("Manager logout clicked");
        // Redirect to login page
    }

    private void updateDashboardStats() {
        totalMedicineLabel.setText("2,456");
        expiredMedicineLabel.setText("23");
        lowStockLabel.setText("47");
        categoriesLabel.setText("15");
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
