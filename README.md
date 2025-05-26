# Time-Scheduling

A Java-based console application for managing and scheduling university courses, non-course blocks, and groups, with conflict detection.

## Features

- **Create Courses:** Add courses with code, description, schedule, and instructor.
- **Create Non-Course Blocks:** Add custom time blocks (e.g., meetings, breaks).
- **Group Management:** Create groups to organize courses and blocks.
- **Conflict Detection:** Automatically checks for scheduling conflicts within groups.
- **Persistent Storage:** Saves and loads courses, non-courses, and groups between sessions.

## Usage

1. **Run the Application**


2. **Follow Prompts**
- The application will guide you through creating courses, non-course blocks, and groups.
- You can add or remove courses/blocks from groups and check for conflicts.

3. **Sample Input**
- See the [Usage](Usage) file for detailed step-by-step instructions and sample data to test the system.

## Menu Options

1. Create a course block
2. Create a group
3. Create a non-course block
4. Check for a conflict in a group
5. Add to a group
6. Remove from a group
7. Exit

## File Structure

- `Main.java` - Main application logic and menu system
- `Course.java` - Course and non-course block representation
- `Block.java` - Time block representation
- `Group.java` - Grouping and conflict logic
- `Instructor.java` - Instructor representation
- `Usage` - Example usage and test data

## Data Persistence

Data is saved to `.dat` files in the working directory and loaded automatically on startup.

