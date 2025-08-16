package com.example.smartmedicalinventory.controller;

import com.example.smartmedicalinventory.model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Manager_dashbordcontroller implements Initializable {

    @FXML
    private Label totalMedicinesLabel;

    @FXML
    private Label expiredMedicinesLabel;

    @FXML
    private Label lowStockLabel;

    @FXML
    private Label categoriesLabel;

    @FXML
    private Label managerNameLabel;

    @FXML
    private Label managerIdLabel;

    @FXML
    private Label systemStatusLabel;

    @FXML
    private TextField quickSearchField;

    @FXML
    private ScrollPane mainContentPane;

    @FXML
    private VBox contentArea;

    private Manager managerObj;

    public void setManager(Manager manager) {
        this.managerObj = manager;

        // Set manager name and ID
        managerNameLabel.setText(manager.getName());
        managerIdLabel.setText("ID: " + manager.getUserId());

        // Update dashboard statistics
        updateDashboardStats();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize with default values
        totalMedicinesLabel.setText("0");
        expiredMedicinesLabel.setText("0");
        lowStockLabel.setText("0");
        categoriesLabel.setText("0");
    }

    private void updateDashboardStats() {
        try {
            // Fetch total medicines
            List<String> allMedicines = managerObj.showAllmedicines();
            totalMedicinesLabel.setText(String.valueOf(allMedicines.size()));

            // Fetch expired medicines
            List<String> expiredMedicines = managerObj.expiredMedicines();
            expiredMedicinesLabel.setText(String.valueOf(expiredMedicines.size()));

            // Fetch low stock medicines
            int lowStockCount = managerObj.getLowStockCount();
            lowStockLabel.setText(String.valueOf(lowStockCount));

            // Fetch unique categories
            int categoriesCount = managerObj.getUniqueCategoriesCount();
            categoriesLabel.setText(String.valueOf(categoriesCount));

            // Update system status
            if (expiredMedicines.size() > 0 || lowStockCount > 0) {
                systemStatusLabel.setText("Attention Needed");
                systemStatusLabel.setStyle("-fx-text-fill: #e74c3c;"); // Red color
            } else {
                systemStatusLabel.setText("Healthy");
                systemStatusLabel.setStyle("-fx-text-fill: #2ecc71;"); // Green color
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        List<String> expiredMedicines = managerObj.expiredMedicines();
        loadContent("Expired Medicines", "Expired Medicines: " + String.join(", ", expiredMedicines));
    }

    @FXML
    void showAllMedicine(ActionEvent event) {
        List<String> allMedicines = managerObj.showAllmedicines();
        loadContent("All Medicines", "All Medicines: " + String.join(", ", allMedicines));
    }

    @FXML
    void quickSearch(ActionEvent event) {
        String searchTerm = quickSearchField.getText();
        if (!searchTerm.isEmpty()) {
            List<String> searchResults = managerObj.SearchMedicine(searchTerm);
            loadContent("Search Results", "Search Results: " + String.join(", ", searchResults));
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) mainContentPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
