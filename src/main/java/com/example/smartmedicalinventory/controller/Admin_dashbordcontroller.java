package com.example.smartmedicalinventory.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.example.smartmedicalinventory.model.Admin;

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

    // Example: Use Admin instance to access both admin and manager features
    private final Admin admin = new Admin("admin", "adminpass", "admin");

    @FXML
    private void manageUsers() {
        // Admin-specific functionality
        admin.addManger();
        admin.removeManger();
        admin.showManger();
        admin.updateManger();
    }

    @FXML
    private void addUser() {
        // Logic to add a user
        admin.addManger();
    }

    @FXML
    private void removeUser() {
        // Logic to remove a user
        admin.removeManger();
    }

    @FXML
    private void updateUser() {
        // Logic to update a user
        admin.updateManger();
    }

    @FXML
    private void viewAllUsers() {
        // Logic to show all users
        admin.showManger();
    }

    @FXML
    private void viewBuyHistory() {
        // Logic to show buy history
        admin.showBuyhistory();
    }

    @FXML
    private void settings() {
        // Logic for admin settings
        // TODO: Implement settings logic
    }

    @FXML
    private void viewSalesReport() {
        // Admin-specific or inherited functionality
        admin.showSellhistory();
        admin.showSellrecornd();
        admin.updateSellrecord();
    }

    @FXML
    private void medicineOversight() {
        // Inherited from Manager, accessible via Admin
        // Example: admin.showMedicines();
        // TODO: Implement medicine oversight logic
    }

    @FXML
    private void systemLogs() {
        // Admin-specific logic for system logs
        // TODO: Implement system logs logic
    }

    @FXML
    private void quickSearch() {
        // Implement quick search logic
        // TODO: Implement quick search logic
    }

    @FXML
    private void logout() {
        // Implement logout logic
        // TODO: Implement logout logic
    }
}
