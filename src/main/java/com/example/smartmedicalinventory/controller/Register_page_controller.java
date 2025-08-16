package com.example.smartmedicalinventory.controller;

import com.example.smartmedicalinventory.util.SystemController;
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
        String name = nameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        String password = passwordField.getText();
        String orgName = orgNameField.getText();
        String address = addressField.getText();

        boolean success = false;

        if ("Customer".equalsIgnoreCase(userType)) {
            success = SystemController.registerCustomer(name, email, contact, password);
            if (success) {
                showSuccess("Customer registered successfully!");
            } else {
                showError("Customer registration failed.");
            }
        } else if ("Organization".equalsIgnoreCase(userType)) {

            success = SystemController.registerAdminWithOrganization(
                    orgName, address, email, name, email, contact, password
            );
            if (success) {
                showSuccess("Organization and Admin registered successfully!");
            } else {
                showError("Organization/Admin registration failed.");
            }
        } else {
            showError("Please select a user type.");
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
