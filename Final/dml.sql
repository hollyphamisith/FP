-- Insert data into Users table
INSERT INTO Users (Name, Email, Password, Role) VALUES ('holly', 'holly@gmail.com', 'password123', 'member');
INSERT INTO Users (Name, Email, Password, Role) VALUES ('tony', 'tony@gmail.com', 'password123', 'member');
INSERT INTO Users (Name, Email, Password, Role) VALUES ('dan', 'dan@gmail.com', 'password123', 'member');
INSERT INTO Users (Name, Email, Password, Role) VALUES ('ethan', 'ethan@gmail.com', 'password123', 'trainer');
INSERT INTO Users (Name, Email, Password, Role) VALUES ('zach', 'zach@gmail.com', 'password123', 'trainer');
INSERT INTO Users (Name, Email, Password, Role) VALUES ('admin', 'admin@gmail.com', 'adminpass', 'admin');

-- Insert data into MemberProfile table
INSERT INTO MemberProfile (MemberID, FitnessGoals, HealthMetrics) VALUES (12, '55kg', '60kg');
INSERT INTO MemberProfile (MemberID, FitnessGoals, HealthMetrics) VALUES (13, '60kg', '70kg');
INSERT INTO MemberProfile (MemberID, FitnessGoals, HealthMetrics) VALUES (14, '59kg', '80kg');

-- Insert data into Trainers table
INSERT INTO Trainers (TrainerID, ScheduleDetails) VALUES (15, 'Weekdays: 9am - 5pm');
INSERT INTO Trainers (TrainerID, ScheduleDetails) VALUES (16, 'Weekdays: 9am - 5pm');

-- Insert data into Room table
INSERT INTO Room ( Name, Availability) VALUES ('Aerobics Room', 'Available');
INSERT INTO Room ( Name, Availability) VALUES ( 'Weight Room', 'Booked');
INSERT INTO Room ( Name, Availability) VALUES ('Yoga Room', 'Available');
INSERT INTO Room ( Name, Availability) VALUES ('Pilates Room', 'Available');
INSERT INTO Room ( Name, Availability) VALUES ( 'Bike Room', 'Available');

-- Insert data into ClassSchedule table
INSERT INTO ClassSchedule (RoomID, TrainerID, Time, ClassName) VALUES (13, 15, '10:00', 'Yoga Class');
INSERT INTO ClassSchedule (RoomID, TrainerID, Time, ClassName) VALUES (14, 16, '12:00', 'Pilates Class');

-- Insert data into Equipment table
INSERT INTO Equipment (MaintenanceDate) VALUES ('2023-07-10');
INSERT INTO Equipment (MaintenanceDate) VALUES ('2023-08-15');
INSERT INTO Equipment (MaintenanceDate) VALUES ('2024-03-14');
INSERT INTO Equipment (MaintenanceDate) VALUES ('2022-01-01');


-- Insert data into ClassAttendees table
INSERT INTO ClassAttendees (MemberID, ClassName) VALUES (12, 'Yoga Class');
INSERT INTO ClassAttendees (MemberID, ClassName) VALUES (12, 'Pilates Class');
INSERT INTO ClassAttendees (MemberID, ClassName) VALUES (13, 'Yoga Class');
INSERT INTO ClassAttendees (MemberID, ClassName) VALUES (13, 'Pilates Class');

-- Insert data into ExerciseLog table
INSERT INTO ExerciseLog (MemberID, Exercise) VALUES (12, 'Treadmill 30 mins');
INSERT INTO ExerciseLog (MemberID, Exercise) VALUES (12, 'Sit ups 20');
INSERT INTO ExerciseLog (MemberID, Exercise) VALUES (13, 'Cycling 20 mins');
INSERT INTO ExerciseLog (MemberID, Exercise) VALUES (13, 'Push ups 20');
INSERT INTO ExerciseLog (MemberID, Exercise) VALUES (14, '100m sprints');

-- Insert data into FitnessAchievements table
INSERT INTO FitnessAchievements (MemberID, AchievementDescription) VALUES (12, 'Completed 5k run');
INSERT INTO FitnessAchievements (MemberID, AchievementDescription) VALUES (12, 'Completed first marathon');
INSERT INTO FitnessAchievements (MemberID, AchievementDescription) VALUES (12, 'Lost 5kg');
INSERT INTO FitnessAchievements (MemberID, AchievementDescription) VALUES (13, 'Lost 10kg');
INSERT INTO FitnessAchievements (MemberID, AchievementDescription) VALUES (14, 'Lost 15kg');
-- Insert data into Billing table
INSERT INTO Billing (BillID, Status) VALUES (12, 'Pending');
INSERT INTO Billing (BillID, Status) VALUES (13, 'Paid');
INSERT INTO Billing (BIllID, Status) VALUES (14, 'Pending');

-- Insert data into Admin table
INSERT INTO Admin (AdminID) VALUES (17);