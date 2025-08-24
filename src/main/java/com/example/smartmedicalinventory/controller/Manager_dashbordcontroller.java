package com.example.smartmedicalinventory.controller;

import com.example.smartmedicalinventory.model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
        updateProfileInfo();
        updateDashboardStats();
    }

    private void updateProfileInfo() {
        if (managerObj != null) {
            managerNameLabel.setText(managerObj.getName());
            managerIdLabel.setText("ID: " + managerObj.getUserId());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalMedicinesLabel.setText("0");
        expiredMedicinesLabel.setText("0");

    }

    private void updateDashboardStats() {
        try {
            List<String> allMedicines = managerObj.showAllmedicines();
            totalMedicinesLabel.setText(String.valueOf(allMedicines.size()));

            List<String> expiredMedicines = managerObj.expiredMedicines();
            expiredMedicinesLabel.setText(String.valueOf(expiredMedicines.size()));

            if (expiredMedicines.size() > 0) {
                systemStatusLabel.setText("Attention Needed");
                systemStatusLabel.setStyle("-fx-text-fill: #e74c3c;");
            } else {
                systemStatusLabel.setText("Healthy");
                systemStatusLabel.setStyle("-fx-text-fill: #2ecc71;");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicine(ActionEvent event) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label("Add Medicine");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        contentArea.getChildren().add(titleLabel);

        VBox form = new VBox(15);
        form.setPadding(new javafx.geometry.Insets(20));

        Label nameLabel = new Label("Medicine Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter medicine name");

        Label typeLabel = new Label("Medicine Type:");
        javafx.scene.control.ComboBox<String> typeCombo = new javafx.scene.control.ComboBox<>();
        typeCombo.getItems().addAll("Diabetes", "Antibiotic", "Gastric", "Allergy", "Injection", "Pain Relief", "Vitamin", "Other");
        typeCombo.setPromptText("Select type");

        Label companyLabel = new Label("Company:");
        TextField companyField = new TextField();
        companyField.setPromptText("Enter company name");

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");

        Button addBtn = new Button("Add Medicine");
        addBtn.setStyle("-fx-background-color: #3a506b; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-pref-width: 160; -fx-pref-height: 40;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c;");

        addBtn.setOnAction(e -> {
            String medName = nameField.getText().trim();
            String medType = typeCombo.getValue();
            String company = companyField.getText().trim();
            String qtyStr = quantityField.getText().trim();

            if (medName.isEmpty() || medType == null || company.isEmpty() || qtyStr.isEmpty()) {
                messageLabel.setText("All fields are required.");
                return;
            }
            double quantity;
            try {
                quantity = Double.parseDouble(qtyStr);
                if (quantity <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Quantity must be a positive number.");
                return;
            }

            boolean success = managerObj.addMedicine(medName, medType, company, quantity);
            if (success) {
                messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 14px;");
                messageLabel.setText("Medicine added successfully!");
                nameField.clear();
                typeCombo.setValue(null);
                companyField.clear();
                quantityField.clear();
                updateDashboardStats();
                updateProfileInfo();
            } else {
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px;");
                messageLabel.setText("Failed to add medicine. Please try again.");
            }
        });

        form.getChildren().addAll(
            nameLabel, nameField,
            typeLabel, typeCombo,
            companyLabel, companyField,
            quantityLabel, quantityField,
            addBtn,
            messageLabel
        );

        contentArea.getChildren().add(form);
    }

    @FXML
    void removeMedicine(ActionEvent event) {

        List<String> allMedicines = managerObj.showAllmedicines();
        contentArea.getChildren().clear();

        Label titleLabel = new Label("Delete Medicine");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        contentArea.getChildren().add(titleLabel);

        if (allMedicines.isEmpty()) {
            Label emptyLabel = new Label("No medicines available to delete.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888;");
            contentArea.getChildren().add(emptyLabel);
            return;
        }

        VBox boxContainer = new VBox(15);
        boxContainer.setPadding(new javafx.geometry.Insets(20));
        for (String medName : allMedicines) {
            HBox row = new HBox(10);
            Label medLabel = new Label(medName);
            medLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #2c3e50;");
            Button deleteBtn = new Button("Delete");
            deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
            deleteBtn.setOnAction(e -> {
                if (managerObj.deleteMedicine(medName)) {
                    removeMedicine(null);
                }
            });
            row.getChildren().addAll(medLabel, deleteBtn);
            boxContainer.getChildren().add(row);
        }
        contentArea.getChildren().add(boxContainer);
    }

    @FXML
    void showAllMedicine(ActionEvent event) {
        List<String> allMedicines = managerObj.showAllmedicines();
        contentArea.getChildren().clear();

        Label titleLabel = new Label("All Medicines");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        contentArea.getChildren().add(titleLabel);

        if (allMedicines.isEmpty()) {
            Label emptyLabel = new Label("No medicines found in your inventory.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888;");
            contentArea.getChildren().add(emptyLabel);
            return;
        }

        VBox medicinesContainer = new VBox(15);
        medicinesContainer.setPadding(new javafx.geometry.Insets(20));
        for (String medName : allMedicines) {
            HBox medicineBox = createMedicineBox(medName);
            medicinesContainer.getChildren().add(medicineBox);
        }
        contentArea.getChildren().add(medicinesContainer);
    }

    private HBox createMedicineBox(String medName) {
        HBox medicineBox = new HBox(20);
        medicineBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 3); " +
                "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
        medicineBox.setPrefWidth(800);

        VBox infoSection = new VBox(8);
        infoSection.setPrefWidth(600);

        Label nameLabel = new Label(medName);
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");



        infoSection.getChildren().addAll(nameLabel);

        VBox buttonSection = new VBox(10);
        buttonSection.setPrefWidth(150);
        buttonSection.setAlignment(javafx.geometry.Pos.CENTER);

        Button removeBtn = new Button("Remove from Inventory");
        removeBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; " +
                "-fx-cursor: hand; -fx-pref-width: 140; -fx-pref-height: 35;");
        removeBtn.setOnAction(e -> {
            if (managerObj.deleteMedicine(medName)) {
                showAllMedicine(null);
                updateDashboardStats();
            }
        });

        buttonSection.getChildren().add(removeBtn);

        medicineBox.getChildren().addAll(infoSection, buttonSection);
        return medicineBox;
    }

    @FXML
    void showExpiredMedicine(ActionEvent event) {
        List<String> expiredMedicines = managerObj.expiredMedicines();
        contentArea.getChildren().clear();

        Label titleLabel = new Label("Expired Medicines");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        contentArea.getChildren().add(titleLabel);

        if (expiredMedicines.isEmpty()) {
            Label emptyLabel = new Label("No expired medicines found.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888;");
            contentArea.getChildren().add(emptyLabel);
            return;
        }

        VBox expiredContainer = new VBox(15);
        expiredContainer.setPadding(new javafx.geometry.Insets(20));
        for (String medName : expiredMedicines) {
            HBox medicineBox = new HBox(20);
            medicineBox.setStyle("-fx-background-color: #fff4f4; -fx-padding: 18; -fx-background-radius: 15; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(231,76,60,0.08), 8, 0, 0, 2); " +
                    "-fx-border-color: #e74c3c; -fx-border-radius: 15; -fx-border-width: 1;");
            medicineBox.setPrefWidth(800);

            Label nameLabel = new Label(medName);
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

            medicineBox.getChildren().add(nameLabel);
            expiredContainer.getChildren().add(medicineBox);
        }
        contentArea.getChildren().add(expiredContainer);
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

    @FXML
    void showInventory(ActionEvent event) {
        List<String> inventory = managerObj.showInventoryDetails();
        loadBoxContent("Inventory", inventory);
    }

    @FXML
    void showSellHistory(ActionEvent event) {
        List<String> sellHistory = managerObj.viewSellHistory();
        loadBoxContent("Sell History", sellHistory);
    }

    @FXML
    void showPurchaseHistory(ActionEvent event) {
        List<String> purchaseHistory = managerObj.viewPurchaseHistory();
        loadBoxContent("Purchase History", purchaseHistory);
    }


    private void loadBoxContent(String title, List<String> records) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        contentArea.getChildren().add(titleLabel);

        if (records == null || records.isEmpty()) {
            Label emptyLabel = new Label("No records found.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888;");
            contentArea.getChildren().add(emptyLabel);
            return;
        }

        VBox boxContainer = new VBox(15);
        boxContainer.setPadding(new javafx.geometry.Insets(20));
        for (String record : records) {
            VBox recordBox = new VBox(10);
            recordBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 18; -fx-background-radius: 15; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 8, 0, 0, 2); " +
                    "-fx-border-color: #e0e0e0; -fx-border-radius: 15; -fx-border-width: 1;");
            recordBox.setPrefWidth(800);

            Label recordLabel = new Label(record);
            recordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-family: 'Courier New', monospace;");
            recordLabel.setWrapText(true);

            recordBox.getChildren().add(recordLabel);
            boxContainer.getChildren().add(recordBox);
        }
        contentArea.getChildren().add(boxContainer);
    }

    private void loadContent(String title, String description) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        contentArea.getChildren().addAll(titleLabel, descLabel);
    }

    @FXML
    private void editProfile(ActionEvent event) {
        contentArea.getChildren().clear();

        Label titleLabel = new Label("Edit Profile");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        contentArea.getChildren().add(titleLabel);

        VBox form = new VBox(15);
        form.setPadding(new javafx.geometry.Insets(20));

        Label nameLabel = new Label("Manager Name:");
        TextField nameField = new TextField(managerObj.getName());
        nameField.setPromptText("Enter new name");

        Button saveBtn = new Button("Save");
        saveBtn.setStyle("-fx-background-color: #3a506b; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-pref-width: 120; -fx-pref-height: 35;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c;");

        saveBtn.setOnAction(e -> {
            String newName = nameField.getText().trim();
            if (newName.isEmpty()) {
                messageLabel.setText("Name cannot be empty.");
                return;
            }

            managerObj.setName(newName);
            updateProfileInfo();
            messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 14px;");
            messageLabel.setText("Profile updated!");
        });

        form.getChildren().addAll(nameLabel, nameField, saveBtn, messageLabel);
        contentArea.getChildren().add(form);
    }

    @FXML
    private void profileMenu(ActionEvent event) {

        loadContent("Profile Settings", "Profile settings feature coming soon.");
    }
}
