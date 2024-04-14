import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManager userManager = new UserManager();
    private static final ScheduleManager scheduleManager = new ScheduleManager();
    private static final FacilityManager facilityManager = new FacilityManager();
    private static final BillingManager billingManager = new BillingManager();

    // Declare an instance of DashboardManager.
    private static final DashboardManager dashboardManager = new DashboardManager();

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to the Health and Fitness Club Management System");
        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Access Member System");
            System.out.println("2. Access Trainer System");
            System.out.println("3. Access Admin Staff System");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int systemChoice = Integer.parseInt(scanner.nextLine());

            switch (systemChoice) {
                case 1:
                    accessMemberSystem();
                    break;
                case 2:
                    accessTrainerSystem();
                    break;
                case 3:
                    accessAdminSystem();
                    break;
                case 4:
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void accessMemberSystem() throws SQLException {
        System.out.println("\n--- Member System ---");
        System.out.println("1. User Registration");
        System.out.println("2. Profile Management (Update Weight/Weight Goal");
        System.out.println("3. Dashboard Display");
        System.out.println("4. Schedule Management");
        System.out.println("5. Add Exercise");
        System.out.println("6. Add Fitness Achievement");
        System.out.print("Choose an action: ");
        int action = Integer.parseInt(scanner.nextLine());
        int memberId; // Declare memberId variable
        String newFitnessGoals; // Declare newFitnessGoals variable
        String newHealthMetrics; // Declare newHealthMetrics variable
        switch (action) {
            case 1:
                System.out.println("Registration");
                // Assuming inputs are validated and sanitized
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine(); // Note: Password should be hashed in a real-world scenario
                System.out.print("Enter role (member/trainer/admin): ");
                String role = scanner.nextLine();

                if (role.equals("member")) {
                    System.out.print("Enter FitnessGoal Weight: ");
                    String fitness = scanner.nextLine();
                    System.out.print("Enter HealthMetric Weight: ");
                    String health = scanner.nextLine();

                    userManager.registerMember(name, email, password, role, fitness, health);
                    break;
                }

                else if(role.equals("trainer"))      {
                    System.out.print("Enter Schedule Details: ");
                    String schedule = scanner.nextLine();

                    userManager.registerTrainer(name, email, password, role, schedule);
                    break;
                }
                else if(role.equals("admin"))    {
                    userManager.registerAdmin(name, email, password, role);
                    break;
                }
            case 2:
                System.out.println("Profile Management");
                System.out.print("Enter Member ID: ");
                memberId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter new Fitness Goal Weight: ");
                newFitnessGoals = scanner.nextLine();
                System.out.print("Enter new Health Metric Weight: ");
                newHealthMetrics = scanner.nextLine();
                userManager.updateMemberProfile(memberId, newFitnessGoals, newHealthMetrics );
                break;
            case 3:
                System.out.println("Dashboard Display");
                System.out.print("Enter Member ID for Dashboard: ");
                memberId = Integer.parseInt(scanner.nextLine());
                dashboardManager.displayMemberDashboard(memberId);
                break;
            case 4:

                System.out.print("Enter Member ID: ");
                memberId = Integer.parseInt(scanner.nextLine());
                scheduleManager.scheduleSession(memberId);
                break;
            case 5:
                System.out.println("Add Exercise");
                System.out.print("Enter Member ID: ");
                memberId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Exercise: ");
                String exercise = scanner.nextLine();
                // Add the exercise to the ExerciseLog
                UserManager.addExercise(memberId, exercise);
                // Display the updated dashboard
                //dashboardManager.displayMemberDashboard(memberId);
                break;
            case 6:
                System.out.println("Add Fitness Achievement");
                System.out.print("Enter Member ID: ");
                memberId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Achievement Description: ");
                String achievementDescription = scanner.nextLine();
                userManager.addFitnessAchievement(memberId, achievementDescription);
                // Display the updated dashboard
                //dashboardManager.displayMemberDashboard(memberId);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void accessTrainerSystem() {
        System.out.println("\n--- Trainer System ---");
        System.out.println("1. Schedule Management");
        System.out.println("2. Member Profile Viewing");
        System.out.print("Choose an action: ");

        int action = Integer.parseInt(scanner.nextLine());

        //int trainerId; // Declare trainerId variable
        int memberId; // Declare memberId variable again for use in this scope

        switch (action) {
            case 1:
                System.out.println("Schedule Management (Create New Available Class)");

                System.out.print("Enter Room ID: ");
                int roomId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Trainer ID: ");
                int trainerId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Class Name: ");
                String className = scanner.nextLine();
                System.out.print("Enter Class Time (In String): ");
                String classTime = scanner.nextLine();

                ScheduleManager.createNewClass(roomId, trainerId, classTime, className);
                break;
            case 2:
                System.out.println("Member Profile Viewing");
                System.out.print("Enter Member Name: ");
                String name = scanner.nextLine();
                // Simulated method call
                userManager.viewMemberProfile(name);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }



    private static void accessAdminSystem() {
        System.out.println("\n--- Admin Staff System ---");
        System.out.println("1. Room Booking Management");
        System.out.println("2. Equipment Maintenance Monitoring");
        System.out.println("3. Class Schedule Updating");
        System.out.println("4. Billing and Payment Processing");
        System.out.print("Choose an action: ");
        int action = Integer.parseInt(scanner.nextLine());

        switch (action) {
            case 1:
                System.out.println("Room Booking Management");
//                System.out.print("Enter Room ID: ");
//                int roomId = Integer.parseInt(scanner.nextLine());
                facilityManager.bookRoom();
                break;
            case 2:
                System.out.println("Update Equipment Maintenance (Tracks when it was last maintained)");
                System.out.print("Enter Equipment ID: ");
                int equipmentId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter most recent maintenance date (In String): ");
                String maintenanceDate = String.valueOf(scanner.nextLine());
                facilityManager.scheduleMaintenance(equipmentId, maintenanceDate);
                break;
            case 3:
                System.out.println("Class Schedule Updating");
                scheduleManager.updateClassSchedule();
                break;
            case 4:
                billingManager.processPayment();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

}
