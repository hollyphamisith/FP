import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    // Method to register a new user
    public void registerMember(String name, String email, String password, String role, String fitness, String health) {
        String userInsertSQL = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, CAST(? AS user_role));";
        String memberProfileInsertSQL = "INSERT INTO memberProfile(memberId, fitnessGoals, healthMetrics) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement userInsertStmt = conn.prepareStatement(userInsertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement memberProfileInsertStmt = conn.prepareStatement(memberProfileInsertSQL)) {

            // Insert user details into 'users' table
            userInsertStmt.setString(1, name);
            userInsertStmt.setString(2, email);
            userInsertStmt.setString(3, password); // Note: Passwords should be hashed in production
            userInsertStmt.setString(4, role);

            int affectedRows = userInsertStmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User registered successfully.");

                // Retrieve the generated user ID
                ResultSet generatedKeys = userInsertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    // Insert member profile details into 'memberProfile' table
                    memberProfileInsertStmt.setInt(1, userId);
                    memberProfileInsertStmt.setString(2, fitness);
                    memberProfileInsertStmt.setString(3, health);

                    int profileAffectedRows = memberProfileInsertStmt.executeUpdate();
                    if (profileAffectedRows > 0) {
                        System.out.println("Member profile created successfully.");
                    } else {
                        System.out.println("Failed to create member profile.");
                    }
                } else {
                    System.out.println("Failed to retrieve user ID.");
                }
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void registerTrainer(String name, String email, String password, String role, String schedule) {
        String userInsertSQL = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, CAST(? AS user_role));";
        String memberProfileInsertSQL = "INSERT INTO trainers(TrainerID, scheduleDetails) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement userInsertStmt = conn.prepareStatement(userInsertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement memberProfileInsertStmt = conn.prepareStatement(memberProfileInsertSQL)) {

            // Insert user details into 'users' table
            userInsertStmt.setString(1, name);
            userInsertStmt.setString(2, email);
            userInsertStmt.setString(3, password); // Note: Passwords should be hashed in production
            userInsertStmt.setString(4, role);

            int affectedRows = userInsertStmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User registered successfully.");

                // Retrieve the generated user ID
                ResultSet generatedKeys = userInsertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    // Insert member profile details into 'memberProfile' table
                    memberProfileInsertStmt.setInt(1, userId);
                    memberProfileInsertStmt.setString(2, schedule);


                    int profileAffectedRows = memberProfileInsertStmt.executeUpdate();
                    if (profileAffectedRows > 0) {
                        System.out.println("Trainer profile created successfully.");
                    } else {
                        System.out.println("Failed to create member profile.");
                    }
                } else {
                    System.out.println("Failed to retrieve user ID.");
                }
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerAdmin(String name, String email, String password, String role) {
        String userInsertSQL = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, CAST(? AS user_role));";
        String memberProfileInsertSQL = "INSERT INTO admin(AdminID) VALUES (?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement userInsertStmt = conn.prepareStatement(userInsertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement memberProfileInsertStmt = conn.prepareStatement(memberProfileInsertSQL)) {

            // Insert user details into 'users' table
            userInsertStmt.setString(1, name);
            userInsertStmt.setString(2, email);
            userInsertStmt.setString(3, password); // Note: Passwords should be hashed in production
            userInsertStmt.setString(4, role);

            int affectedRows = userInsertStmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User registered successfully.");

                // Retrieve the generated user ID
                ResultSet generatedKeys = userInsertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    // Insert member profile details into 'memberProfile' table
                    memberProfileInsertStmt.setInt(1, userId);


                    int profileAffectedRows = memberProfileInsertStmt.executeUpdate();
                    if (profileAffectedRows > 0) {
                        System.out.println("Admin profile created successfully.");
                    } else {
                        System.out.println("Failed to create member profile.");
                    }
                } else {
                    System.out.println("Failed to retrieve user ID.");
                }
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public String getUserID(String name) throws SQLException {
        String SQL = "SELECT UserID FROM users WHERE name = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("UserId");
            } else {
                throw new SQLException("User not found for name: " + name);
            }
        }
    }



    // Method to update a member's profile
    public void updateMemberProfile(int memberId, String newFitnessGoals, String newHealthMetrics) {
        String SQL = "UPDATE memberProfile SET fitnessGoals = ?, healthMetrics = ? WHERE memberId = ?;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, newFitnessGoals);
            pstmt.setString(2, newHealthMetrics);
            pstmt.setInt(3, memberId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Member profile updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Simulated method for viewing a member profile
    public void viewMemberProfile(String name) {
        String SQL = "SELECT u.name, u.UserID, u.Password, u.Email, m.fitnessGoals, m.healthMetrics " +
                "FROM users u " +
                "JOIN memberProfile m ON u.UserId = m.memberId " +
                "JOIN ClassAttendees c ON u.UserId = c.MemberID " +
                "WHERE u.name = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Fetch data from the result set
                String memberName = rs.getString("name");
                int userID = rs.getInt("UserID");
                String password = rs.getString("Password");
                String email = rs.getString("Email");

                // Display member profile
                System.out.println("\nName: " + memberName);
                System.out.println("\nUserID: " + userID);
                System.out.println("\nPassword: " + password);
                System.out.println("\nEmail: " + email);

                // Display member dashboard
                DashboardManager.displayMemberDashboard(userID); // Call displayMemberDashboard with the userID
            } else {
                System.out.println("Member profile not found for name: " + name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void addExercise(int memberId, String exercise) {
        // Add the exercise to the ExerciseLog table
        String SQL = "INSERT INTO ExerciseLog(MemberID, Exercise) VALUES (?, ?);";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, memberId);
            pstmt.setString(2, exercise);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Exercise added successfully.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFitnessAchievement(int memberId, String achievementDescription) {
        String SQL = "INSERT INTO FitnessAchievements(MemberID, AchievementDescription) VALUES (?, ?);";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, memberId);
            pstmt.setString(2, achievementDescription);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Fitness achievement added successfully.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

