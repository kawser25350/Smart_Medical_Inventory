// ...existing code...
    private HBox createMedicineBox(Medicine medicine) {
        HBox medicineBox = new HBox(20);
        // ...existing code...

        Label priceLabel = new Label("Price: " + String.format("%.2f", medicine.getPricePerUnit()) + " ৳ per unit");
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        // ...existing code...
    }
// ...existing code...

    public List<String> viewOrders() {
        List<String> orders = new ArrayList<>();
        Connection conn = null;
        try {
            conn = SystemController.getConnection();
            String query = "SELECT cbh.quantity, cbh.datetime, m.med_name, m.med_type, m.company, " +
                          "CASE " +
                          "WHEN m.med_type = 'Diabetes' THEN 45.50 " +
                          "WHEN m.med_type = 'Antibiotic' THEN 32.75 " +
                          "WHEN m.med_type = 'Gastric' THEN 28.25 " +
                          "WHEN m.med_type = 'Allergy' THEN 18.90 " +
                          "WHEN m.med_type = 'Injection' THEN 125.00 " +
                          "ELSE 25.00 " +
                          "END as price " +
                          "FROM Customer_buy_history cbh " +
                          "JOIN Medicine m ON cbh.fk_med_id = m.med_id " +
                          "WHERE cbh.fk_customer_id = ? " +
                          "ORDER BY cbh.datetime DESC";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, getUserId());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String medicineName = rs.getString("med_name");
                        String medicineType = rs.getString("med_type");
                        String company = rs.getString("company");
                        int quantity = rs.getInt("quantity");
                        double price = rs.getDouble("price");
                        String datetime = rs.getTimestamp("datetime").toString();

                        String orderDetails = String.format(
                            "🏥 MEDICINE: %s\n" +
                            "📋 Type: %s | Company: %s\n" +
                            "📦 Quantity: %d units\n" +
                            "💰 Unit Price: %.2f ৳ | Total: %.2f ৳\n" +
                            "📅 Order Date: %s\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                            medicineName,
                            medicineType, company,
                            quantity,
                            price, (price * quantity),
                            datetime
                        );
                        orders.add(orderDetails);
                    }
                }
            }
        // ...existing code...
    }
// ...existing code...

