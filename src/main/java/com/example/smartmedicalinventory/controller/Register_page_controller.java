package com.example.smartmedicalinventory.controller;

import com.example.smartmedicalinventory.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register_page_controller {
    @FXML
    private RadioButton customerRadio;
    @FXML
    private RadioButton organizationRadio;
    @FXML
    private ToggleGroup userTypeGroup;
    @FXML
    private RadioButton pharmacyRadio;
    @FXML
    private RadioButton hospitalRadio;
    @FXML
    private RadioButton othersRadio;
    @FXML
    private ToggleGroup orgTypeGroup;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField useridField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField orgNameField;
    @FXML
    private TextField addressField;

    @FXML
    private void backmethod(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) customerRadio.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registermethod(ActionEvent event) {
        String userType = userTypeGroup.getSelectedToggle() != null ? ((RadioButton) userTypeGroup.getSelectedToggle()).getText() : "";
        String orgType = orgTypeGroup.getSelectedToggle() != null ? ((RadioButton) orgTypeGroup.getSelectedToggle()).getText() : "";
        String name = nameField.getText();
        String age = ageField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        String password = passwordField.getText();
        String orgName = orgNameField.getText();
        String address = addressField.getText();

        try {
            Connection conn = HelloApplication.getConnection();

            if ("Customer".equalsIgnoreCase(userType)) {
                String sql = "INSERT INTO Customer (pwd, name, gmail, contact) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, password);
                    ps.setString(2, name);
                    ps.setString(3, email);
                    ps.setString(4, contact);
                    ps.executeUpdate();
                }
                showSuccess("Customer registered successfully!");
            } else if ("Organization".equalsIgnoreCase(userType)) {
                String orgSql = "INSERT INTO Organization (name, address, gmail) VALUES (?, ?, ?)";
                int orgId = -1;
                try (PreparedStatement ps = conn.prepareStatement(orgSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, orgName);
                    ps.setString(2, address);
                    ps.setString(3, email);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            orgId = rs.getInt(1);
                        }
                    }
                }
                if (orgId != -1) {
                    String adminSql = "INSERT INTO Admin (pwd, name, gmail, contact, org_id) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement ps = conn.prepareStatement(adminSql)) {
                        ps.setString(1, password);
                        ps.setString(2, name);
                        ps.setString(3, email);
                        ps.setString(4, contact);
                        ps.setInt(5, orgId);
                        ps.executeUpdate();
                    }
                    showSuccess("Organization and Admin registered successfully!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Registration failed: " + e.getMessage());
        }
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
