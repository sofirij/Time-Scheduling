# Time-Scheduling Software

The aim of this project was to come up with a schedule checker for the CS faculty to plan course timetables.

## Essential Classes
### Instructor.java
This defines an instructor of a course 

### Course.java
This defines a course and is also used to define a non-course. A non-course is a course but without an instructor nor a course code but it is abstracted. 
A course is created with a course code, extra text (for extra information), an instructor and the course schedule.
The course schedule is represented in the format "MTh 2:30PM-4:30PM" or it can have multiple times in this format "MTh 1:30PM-2:30PM ; TF 12:00PM-3:00PM"
When a course object is initiated the time elements are retrieved and put in arraylists for convenient calculation.

### Block.java
This defines a block for one timeslot. A course object is broken down to a block where a block is made for each time slot of a course.

### Group.java
A group consists of a treemap that maps the days of a week to an arraylist of blocks.
This group represents a weekly schedule and courses can be added to the group. When a course is added to a group, the course is broken to its different blocks and the blocks are 
added to its corresponding arraylist in the treemap and then sorted.
This is where conflicts are checked when the print method is called. It checks the arraylists of blocks in the treemap and checks their time elements for any overlaps. If there are overlaps they are shown otherwise
the group is printed with the block in their corresponding time slot.

### Main.java
This runs a text based interface that allows users to create courses, non-courses, groups, add courses to groups, check for conflicts in groups, etc. When the program is exited the courses, non-courses and group objects are saved to a file for continuation at a later time.

It was assumed that no input errors from the user would occur as the program is used.
