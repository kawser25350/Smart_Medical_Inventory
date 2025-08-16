package com.example.smartmedicalinventory.model;

public class MedicalShop {
    private String name;
    private String owner;
    private String address;
    private String email;

    public MedicalShop(String name, String owner, String address, String email) {
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MedicalShop{name='" + name + "', owner='" + owner + "', address='" + address + "', email='" + email + "'}";
    }
}
