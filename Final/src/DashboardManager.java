import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardManager {

    public static void displayMemberDashboard(int memberId) {
        String fitnessGoalsSQL = "SELECT FitnessGoals, HealthMetrics FROM MemberProfile WHERE MemberID = ?;";
        String exerciseLogSQL = "SELECT Exercise FROM ExerciseLog WHERE MemberID = ?;";
        String fitnessAchievementsSQL = "SELECT AchievementDescription FROM FitnessAchievements WHERE MemberID = ?;";
        String signedUpClassSQL = "SELECT ClassName FROM ClassAttendees WHERE MemberID = ?;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement fitnessGoalsStmt = conn.prepareStatement(fitnessGoalsSQL);
             PreparedStatement exerciseLogStmt = conn.prepareStatement(exerciseLogSQL);
             PreparedStatement fitnessAchievementsStmt = conn.prepareStatement(fitnessAchievementsSQL);
             PreparedStatement signedUpClassStmt = conn.prepareStatement(signedUpClassSQL)) {

            // Set parameters for the fitness goals query
            fitnessGoalsStmt.setInt(1, memberId);
            ResultSet fitnessGoalsRs = fitnessGoalsStmt.executeQuery();

            System.out.println("\nHealth Stats:");
            // Display fitness goals and health metrics
            if (fitnessGoalsRs.next()) {
                String fitnessGoals = fitnessGoalsRs.getString("FitnessGoals");
                String healthMetrics = fitnessGoalsRs.getString("HealthMetrics");

                System.out.println("Fitness Goals: " + fitnessGoals);
                System.out.println("Health Metrics: " + healthMetrics);
            } else {
                System.out.println("No profile data found for member ID: " + memberId);
            }

            // Set parameters for the exercise log query
            exerciseLogStmt.setInt(1, memberId);
            ResultSet exerciseLogRs = exerciseLogStmt.executeQuery();

            // Display exercise log data
            System.out.println("\nExercise Log:");
            while (exerciseLogRs.next()) {
                String exercise = exerciseLogRs.getString("Exercise");
                System.out.println("- " + exercise);
            }

            // Set parameters for the fitness achievements query
            fitnessAchievementsStmt.setInt(1, memberId);
            ResultSet fitnessAchievementsRs = fitnessAchievementsStmt.executeQuery();

            // Display fitness achievements
            System.out.println("\nFitness Achievements:");
            while (fitnessAchievementsRs.next()) {
                String achievementDescription = fitnessAchievementsRs.getString("AchievementDescription");
                System.out.println("- " + achievementDescription);
            }

            // Set parameters for the signed up class query
            signedUpClassStmt.setInt(1, memberId);
            ResultSet signedUpClassRs = signedUpClassStmt.executeQuery();

            // Display signed up classes
            System.out.println("\nSigned Up Classes:");
            while (signedUpClassRs.next()) {
                String signedUpClass = signedUpClassRs.getString("ClassName");
                System.out.println("- " + signedUpClass);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




}
