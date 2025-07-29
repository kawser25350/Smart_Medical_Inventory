package com.example.smartmedicalinventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class register_page_controller {
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
            // Use any node from the scene to get the stage, e.g., customerRadio
            Stage stage = (Stage) customerRadio.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
