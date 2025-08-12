package com.example.smartmedicalinventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {
    private static Connection connection;

    @Override
    public void start(Stage stage) throws IOException {

        // database
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Smart Medical Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       
        connectDatabase(); // shohanur database connection ta ekhane call kora hoyeche
        launch();
    }


    public static void connectDatabase() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/smartmedicalinventory";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL JDBC Driver not found. " +
                "vai database connection ta dek akbar" );
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }


    public static Connection getConnection() {
        return connection;
    }
}