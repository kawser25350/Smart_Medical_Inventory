// ...existing code...
        Label priceLabel = new Label("💰 Price: ৳" + String.format("%.2f", medicine.getPricePerUnit()) + " per unit");
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
// ...existing code...
                // Calculate price based on type
                double price = calculatePriceByType(type);

                // Use current date + 1 year as expiry
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.YEAR, 1);
                java.util.Date expiryDate = cal.getTime();

                if (adminObj.updateMedicineInInventory(medicine.getId(), name, type, company, price, quantity, expiryDate)) {
                    updateContentArea("Success", "Medicine '" + name + "' has been updated successfully!\n\n" +
                                    "Updated Details:\n" +
                                    "Name: " + name + "\n" +
                                    "Type: " + type + "\n" +
                                    "Company: " + company + "\n" +
                                    "Price: ৳" + String.format("%.2f", price) + " per unit (auto-calculated)\n" +
                                    "Quantity: " + String.format("%.0f", quantity) + " units\n" +
                                    "Expiry Date: 1 year from now (default)");
                    updateDashboardStats(); // Refresh stats
// ...existing code...
            salesLabel.setText("৳" + String.format("%.0f", totalSales));
// ...existing code...

