import java.sql.*;
import java.util.Scanner;
public class ScheduleManager {

    public void scheduleSession(int memberId) {
        String classScheduleSQL = "SELECT ClassID, RoomID, TrainerID, Time, ClassName FROM ClassSchedule;";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Retrieve class schedule data
            ResultSet rs = stmt.executeQuery(classScheduleSQL);

            // Display class schedule
            System.out.println("Class Schedule:");
            while (rs.next()) {
                int classId = rs.getInt("ClassID");
                int roomID = rs.getInt("RoomID");
                int trainerID = rs.getInt("TrainerID");
                String time = rs.getString("Time");
                String className = rs.getString("ClassName");

                System.out.println("ClassID: " + classId + ", RoomID: " + roomID + ", TrainerID: " + trainerID +
                        ", Time: " + time + ", ClassName: " + className);
            }

            // Prompt user to input the class ID they want to join
            System.out.print("Enter the ClassID of the class you want to join: ");
            Scanner scanner = new Scanner(System.in);
            int classIdToJoin = scanner.nextInt();

            // Check if the class exists
            String checkClassSQL = "SELECT ClassName FROM ClassSchedule WHERE ClassID = ?";
            try (PreparedStatement pstmtCheckClass = conn.prepareStatement(checkClassSQL)) {
                pstmtCheckClass.setInt(1, classIdToJoin);
                ResultSet rsCheckClass = pstmtCheckClass.executeQuery();
                if (rsCheckClass.next()) {
                    String className = rsCheckClass.getString("ClassName");

                    // Insert the attendee into ClassAttendees
                    String insertAttendeeSQL = "INSERT INTO ClassAttendees (MemberID, ClassName) VALUES (?, ?)";
                    try (PreparedStatement pstmtInsertAttendee = conn.prepareStatement(insertAttendeeSQL)) {
                        pstmtInsertAttendee.setInt(1, memberId);
                        pstmtInsertAttendee.setString(2, className);
                        pstmtInsertAttendee.executeUpdate();
                    }
                    System.out.println("You have successfully joined the class.");
                } else {
                    System.out.println("Invalid ClassID.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void updateClassSchedule() {
        // Display all Class IDs
        displayAllClassIDs();

        // Continue with the rest of the method to update the class schedule
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Class ID to update: ");
        int classID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Display class details for the chosen class ID
        displayClassDetails(classID);

        // Prompt the user to update the time
        System.out.print("Enter the new time for the class (format: HH:MM): ");
        String newTime = scanner.nextLine();

        // Prepare SQL statement for updating the time
        String SQL = "UPDATE ClassSchedule SET Time = ? WHERE ClassID = ?;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, newTime);
            pstmt.setInt(2, classID);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Class schedule updated successfully.");
            } else {
                System.out.println("Class schedule with ID " + classID + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayAllClassIDs() {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Query to retrieve all Class IDs
            String classIdsQuery = "SELECT ClassID FROM ClassSchedule;";

            // Execute the query to get the Class IDs
            ResultSet rs = stmt.executeQuery(classIdsQuery);

            // Display all Class IDs
            System.out.println("Class IDs Available to Update:");
            while (rs.next()) {
                int classId = rs.getInt("ClassID");
                System.out.println(classId);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Class IDs: " + e.getMessage());
        }
    }

    private void displayClassDetails(int classID) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassSchedule WHERE ClassID = ?")) {
            pstmt.setInt(1, classID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int roomID = rs.getInt("RoomID");
                int trainerID = rs.getInt("TrainerID");
                String time = rs.getString("Time");
                String className = rs.getString("ClassName"); // Retrieve class name from the result set

                System.out.println("Class Details:");
                System.out.println("Class ID: " + classID);
                System.out.println("Room ID: " + roomID);
                System.out.println("Trainer ID: " + trainerID);
                System.out.println("Time: " + time);
                System.out.println("Class Name: " + className); // Print class name
            } else {
                System.out.println("Class with ID " + classID + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving class details: " + e.getMessage());
        }
    }


    public static void createNewClass(int roomId, int trainerId, String classTime, String className) {
        // Assuming a table "ClassSchedule" exists for managing class schedules
        String SQL = "INSERT INTO ClassSchedule(RoomID, TrainerID, Time, ClassName) VALUES (?, ?, ?, ?);";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, trainerId);
            pstmt.setString(3, classTime);
            pstmt.setString(4, className); // Set the fourth parameter for ClassName

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Class scheduled successfully.");

                // Update the availability of the room to RoomAvailability.BOOKED
                updateRoomAvailability(roomId, RoomAvailability.Booked);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    private static void updateRoomAvailability(int roomId, RoomAvailability availability) {
        // Update the availability of the room in the Room table
        String updateSQL = "UPDATE Room SET availability = ? WHERE RoomID = ?;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            // Set the availability parameter with an explicit SQL type
            pstmt.setObject(1, availability.toString()); // Convert availability to String
            pstmt.setInt(2, roomId); // Use setInt for integer values

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Room availability updated to " + availability + ".");
            } else {
                System.out.println("Failed to update room availability.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public enum RoomAvailability {
        Available,
        Booked
    }

}
