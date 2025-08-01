package com.example.smartmedicalinventory.model;

public class Manager extends User{
    public Manager(String userId, String password, String userType) {
        super(userId, password, userType);
    }

    public void addMedicine() {
        // Logic to add medicine
    }

    public void removeMedicine() {
        // Logic to remove medicine
    }
    public void show_expired_medicine() {
        // Logic to get expiry date of medicine
    }
    public void show_Allmedicine() {
        // Logic to show medicine
    }

    public void updateMedicine() {
        // Logic to update medicine
    }


}
