package com.example.smartmedicalinventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
    void loginmethod(ActionEvent event) {
        // for test only redirect to dashboard
        // DEPEND on user type if cutomer redirect to customer dashboard if organizzation redirect to admin dashbord
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/admin_dashbord.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginbtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void registermethod(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/register_page.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registerbtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backmethod(ActionEvent event) {
        ((Stage) backIcon.getScene().getWindow()).close();
    }
}