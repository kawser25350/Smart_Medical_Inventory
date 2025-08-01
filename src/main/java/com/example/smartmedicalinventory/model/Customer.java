package com.example.smartmedicalinventory.model;

public class Customer extends User {
    public Customer(String userId, String password, String userType) {
        super(userId, password, userType);
    }

    public void viewMedicine() {
        // Logic to view medicine
    }
    public void medicalShop() {
        // Logic to shop for medicine
    }

    public void orderMedicine() {
        // Logic to order medicine
    }

    public void searchMedicine() {
        // Logic to search for medicine
    }

}
