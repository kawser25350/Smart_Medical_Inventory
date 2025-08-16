package com.example.smartmedicalinventory.controller;

import com.example.smartmedicalinventory.model.Customer;
import com.example.smartmedicalinventory.model.Admin;
import com.example.smartmedicalinventory.model.Manager;
import com.example.smartmedicalinventory.util.SystemController;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        try {
            String email = userid.getText();
            String password = userpswd.getText();
            if (email.isEmpty() || password.isEmpty()) {
                System.out.println("Please enter both Email and Password.");
                return;
            }

            String userType = ((RadioButton) userTypeGroup.getSelectedToggle()).getText();
            Connection conn = SystemController.getConnection();

            boolean loginSuccess = false;

            if ("Customer".equalsIgnoreCase(userType)) {
                String sql = "SELECT customer_id, name, pwd FROM Customer WHERE gmail = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String dbPwd = rs.getString("pwd");
                            if (SystemController.decryptPassword(dbPwd).equals(password)) {
                                loginSuccess = true;
                                int customerId = rs.getInt("customer_id");
                                String customerName = rs.getString("name");

                                Customer customerObj = new Customer(customerId, customerName);

                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/customer_dashbord.fxml"));
                                Parent root = fxmlLoader.load();
                                Customer_dashbordcontroller controller = fxmlLoader.getController();
                                controller.setCustomer(customerObj);
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) loginbtn.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } else {
                                System.out.println("Invalid Customer credentials.");
                            }
                        } else {
                            System.out.println("Customer not found.");
                        }
                    }
                }
            } else if ("Organization".equalsIgnoreCase(userType)) {
                // Try Admin login
                String sql = "SELECT * FROM Admin WHERE gmail = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String dbPwd = rs.getString("pwd");
                            if (SystemController.decryptPassword(dbPwd).equals(password)) {
                                loginSuccess = true;
                                Admin adminObj = new Admin(
                                        rs.getInt("admin_id"),
                                        rs.getString("name"),
                                        rs.getInt("org_id") // Adjusted constructor arguments
                                );
                                // create admin obj and pass it to admin dashbord controller
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/admin_dashbord.fxml"));
                                Parent root = fxmlLoader.load();
                                Admin_dashbordcontroller controller = fxmlLoader.getController();
                                controller.setAdmin(adminObj);
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) loginbtn.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            }
                        }
                    }
                }
                // If not admin, try Manager login
                if (!loginSuccess) {
                    sql = "SELECT manager_id, name, pwd, fk_dept_id, fk_org_id FROM Manager WHERE gmail = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, email);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                String dbPwd = rs.getString("pwd");
                                if (SystemController.decryptPassword(dbPwd).equals(password)) {
                                    loginSuccess = true;

                                    // Fetch manager details
                                    int managerId = rs.getInt("manager_id");
                                    String managerName = rs.getString("name");
                                    int deptId = rs.getInt("fk_dept_id");
                                    int orgId = rs.getInt("fk_org_id");

                                    // Create Manager object
                                    Manager managerObj = new Manager(managerId, managerName, deptId, orgId);

                                    // Load Manager Dashboard
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/smartmedicalinventory/fxml/manager_dashbord.fxml"));
                                    Parent root = fxmlLoader.load();
                                    Manager_dashbordcontroller controller = fxmlLoader.getController();
                                    controller.setManager(managerObj);
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) loginbtn.getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!loginSuccess) {
                    System.out.println("Invalid Admin/Manager credentials.");
                }
            }
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