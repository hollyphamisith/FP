import java.sql.*;
import java.util.Scanner;

public class FacilityManager {
    private Scanner scanner = new Scanner(System.in);
    public void bookRoom() {
        // Query to retrieve available room IDs
        String availableRoomsSQL = "SELECT RoomID FROM Room WHERE availability = 'Available';";


        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Retrieve available room IDs
            ResultSet rs = stmt.executeQuery(availableRoomsSQL);

            // Display available room IDs
            System.out.println("Available Room IDs:");
            while (rs.next()) {
                int roomId = rs.getInt("RoomID");
                System.out.println(roomId);
            }

            // Prompt the user to choose a room
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the Room ID to book: ");
            int roomId = Integer.parseInt(scanner.nextLine());

            // Check if the selected room exists and is available
            String checkRoomSQL = "SELECT COUNT(*) AS count FROM Room WHERE RoomID = ? AND availability = 'Available';";
            try (PreparedStatement pstmtCheckRoom = conn.prepareStatement(checkRoomSQL)) {
                pstmtCheckRoom.setInt(1, roomId);
                ResultSet rsCheckRoom = pstmtCheckRoom.executeQuery();
                if (rsCheckRoom.next() && rsCheckRoom.getInt("count") > 0) {
                    // Room is available, proceed to book
                    bookSelectedRoom(roomId);
                } else {
                    System.out.println("Invalid Room ID or Room is not available.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void bookSelectedRoom(int roomId) {
        // Logic to book the selected room (e.g., insert into RoomBookings table)
        try (Connection conn = DatabaseUtil.getConnection()) {
            // Update the availability status to "Booked"
            String updateSQL = "UPDATE Room SET availability = 'Booked' WHERE RoomID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setInt(1, roomId);
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Room " + roomId + " booked successfully.");
                } else {
                    System.out.println("Failed to update availability for Room " + roomId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void scheduleMaintenance(int equipmentId, String maintenanceDate) {
        // Assuming a table "Equipment" exists
        String SQL = "UPDATE Equipment SET MaintenanceDate = ? WHERE EquipmentID = ?;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, maintenanceDate);
            pstmt.setInt(2, equipmentId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Maintenance scheduled successfully.");
            } else {
                System.out.println("Equipment with ID " + equipmentId + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}