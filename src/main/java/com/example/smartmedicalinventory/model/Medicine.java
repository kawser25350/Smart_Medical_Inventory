package com.example.smartmedicalinventory.model;

import java.util.Date;

public class Medicine {
    private int id;
    private String name;
    private String type;
    private String company;
    private double pricePerUnit;
    private double quantity;
    private Date expiryDate;

    public Medicine(int id, String name, String type, String company, double pricePerUnit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.company = company;
        this.pricePerUnit = pricePerUnit;
        this.quantity = 0.0;
        this.expiryDate = new Date();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getCompany() { return company; }
    public double getPricePerUnit() { return pricePerUnit; }
    public double getQuantity() { return quantity; }
    public Date getExpiryDate() { return expiryDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setCompany(String company) { this.company = company; }
    public void setPricePerUnit(double pricePerUnit) { this.pricePerUnit = pricePerUnit; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", company='" + company + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", quantity=" + quantity +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
