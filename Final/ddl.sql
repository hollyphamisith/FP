-- Define ENUM types
CREATE TYPE billing_status AS ENUM ('Pending', 'Paid');
CREATE TYPE room_availability AS ENUM ('Available', 'Booked');
CREATE TYPE user_role AS ENUM ('member', 'trainer', 'admin');

-- Create Users table
CREATE TABLE Users (
    UserID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Role user_role NOT NULL
);

-- Create MemberProfile table
CREATE TABLE MemberProfile (
    MemberID INT PRIMARY KEY,
    FitnessGoals TEXT,
    HealthMetrics TEXT,
    UserID INT REFERENCES Users(UserID)
);

-- Create Trainers table
CREATE TABLE Trainers (
    TrainerID INT PRIMARY KEY,
    ScheduleDetails TEXT,
    UserID INT REFERENCES Users(UserID)
);

-- Create Room table
CREATE TABLE Room (
    RoomID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Availability room_availability NOT NULL
);

-- Create ClassSchedule table
CREATE TABLE ClassSchedule (
    ClassID SERIAL PRIMARY KEY,
    RoomID INT REFERENCES Room(RoomID),
    TrainerID INT REFERENCES Trainers(TrainerID),
    Time TEXT NOT NULL,
    ClassName TEXT NOT NULL
);

-- Create Equipment table
CREATE TABLE Equipment (
    EquipmentID SERIAL PRIMARY KEY,
    MaintenanceDate VARCHAR(255) NOT NULL
);

-- Create ClassAttendees table
CREATE TABLE ClassAttendees (
    AttendeeID SERIAL PRIMARY KEY,
    MemberID INT REFERENCES MemberProfile(MemberID),
    ClassName TEXT REFERENCES ClassSchedule(ClassName)
);

-- Create ExerciseLog table
CREATE TABLE ExerciseLog (
    LogID SERIAL PRIMARY KEY,
    MemberID INT REFERENCES MemberProfile(MemberID),
    Exercise VARCHAR(255) NOT NULL
);

-- Create FitnessAchievements table
CREATE TABLE FitnessAchievements (
    AchievementID SERIAL PRIMARY KEY,
    MemberID INT REFERENCES MemberProfile(MemberID),
    AchievementDescription TEXT NOT NULL
);

-- Create Billing table
CREATE TABLE Billing (
    BillID SERIAL PRIMARY KEY,
    Status billing_status NOT NULL
);

-- Create Admin table
CREATE TABLE Admin (
    AdminID INT PRIMARY KEY REFERENCES Users(UserID)
);
