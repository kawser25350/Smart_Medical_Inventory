module com.example.smartmedicalinventory {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.smartmedicalinventory to javafx.fxml;
    exports com.example.smartmedicalinventory;
    exports com.example.smartmedicalinventory.controller;
    opens com.example.smartmedicalinventory.controller to javafx.fxml;
}