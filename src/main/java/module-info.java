module com.example.smartmedicalinventory {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.smartmedicalinventory to javafx.fxml;
    opens com.example.smartmedicalinventory.controller to javafx.fxml;
    exports com.example.smartmedicalinventory;
    exports com.example.smartmedicalinventory.controller;
}