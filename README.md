# Time-Scheduling Software

Overview

This application is designed to help manage course schedules by identifying and resolving conflicts. It accepts course schedules and checks for overlaps within specified days. If there are no conflicts, it prints the schedules in a neatly ordered format.

Features
Conflict Detection: Automatically identifies overlapping course schedules.
Organized Display: Outputs a nicely formatted schedule sorted by day if there are no conflicts.
Flexible Course Management: Easily add or remove courses from a schedule.

Code Structure
Group.java: 
Manages a collection of courses organized by day. It handles conflict detection and sorting of courses within each day. Courses are stored in TreeMap to keep the weekday order consistent.
Course.java: 
Represents individual course details, including day, time, and course-specific information.
Instructor.java: 
Handles instructor information associated with each course.
Main.java: 
Contains the main execution logic for running the application.


How It Works
Adding Courses: Courses are added to the group based on the specified day. If a course already exists for that day, it replaces the existing one.
Conflict Check: The isConflict() function scans all courses within each day to detect overlapping schedules.
Printing Schedule: If no conflicts are found, print() outputs the ordered list of courses by day.

Sample Usage
Here's a brief example of how to add and print a course schedule:

java
Group group = new Group("Sample Schedule");
Course course1 = new Course("CS101", "Monday", "10:00AM", "11:00AM");
Course course2 = new Course("CS102", "Monday", "11:30AM", "12:30PM");

group.add(course1);
group.add(course2);
group.print();

Future Improvements
Expand to support customizable schedule views (e.g., weekly or monthly).
Add options for exporting schedules in various formats like PDF or CSV.

Getting Started
Clone the repository:
git clone <repository-url>

Compile the project:
javac Main.java

Run the program:
java Main
