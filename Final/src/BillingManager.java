import java.sql.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class BillingManager {

    public void processPayment() {
        // Display all bill IDs and their statuses
        displayAllBills();

        // Prompt the user to select a bill ID for processing payment
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the BillID you want to process payment for: ");
        int billId = scanner.nextInt();

        // Process payment for the selected bill ID
        processPaymentForBill(billId);
    }

    private void displayAllBills() {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Query to retrieve all bill IDs and their statuses
            String billsQuery = "SELECT BillID, Status FROM Billing;";

            // Execute the query to get the bill details
            ResultSet rs = stmt.executeQuery(billsQuery);

            // Display all bill IDs and their statuses
            System.out.println("All Bills:");
            while (rs.next()) {
                int billID = rs.getInt("BillID");
                String status = rs.getString("Status");
                System.out.println("BillID: " + billID + ", Status: " + status);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving bill details: " + e.getMessage());
        }
    }

    private void processPaymentForBill(int billId) {
        // SQL to update the status of the bill to 'Paid'
        String SQL = "UPDATE Billing SET Status = 'Paid' WHERE BillID = ?;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, billId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Payment processed successfully for BillID: " + billId);
            } else {
                System.out.println("Failed to process payment for BillID: " + billId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
    }


