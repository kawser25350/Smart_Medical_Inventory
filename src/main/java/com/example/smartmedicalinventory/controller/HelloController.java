package com.example.smartmedicalinventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private Button loginbtn;

    @FXML
    private Button registerbtn;

    @FXML
    private Button backIcon;

    @FXML
    private TextField userid;

    @FXML
    private PasswordField userpswd;

    @FXML
    private RadioButton customer;

    @FXML
    private RadioButton organization;

    @FXML
    private ToggleGroup userTypeGroup;

    @FXML
    void loginmethod(KeyEvent event) {
        // Example: get selected user type
        RadioButton selected = (RadioButton) userTypeGroup.getSelectedToggle();
        if (selected != null) {
            String userType = selected.getText();
            // Handle login logic
        }
    }

    @FXML
    void registermethod(ActionEvent event) {
        // Redirect to register page
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/register_page.fxml"));
            javafx.scene.Parent root = fxmlLoader.load();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            javafx.stage.Stage stage = (javafx.stage.Stage) registerbtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backmethod(ActionEvent event) {
        // Example: close the window or implement navigation as needed
        ((Stage) backIcon.getScene().getWindow()).close();
    }
}