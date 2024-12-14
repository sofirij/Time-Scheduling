import java.util.*;
import java.io.*;
public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Course> nonCourses = new ArrayList<>();

        //load data
        read(courses, nonCourses, groups);

        int option;

        do
        {
            introduce();

            option = scan.nextInt();

            performOption(option, courses, groups, nonCourses);

        } while (option != 7);


        System.out.println("Current Courses");
        for (Course c : courses)
        {
            c.print();
        }

        System.out.println("\nCurrent Non-Courses");
        for (Course c : nonCourses)
        {
            c.print();
        }

        System.out.println("\nCurrent Groups");
        for (Group g : groups)
        {
            System.out.println(g.getName());
        }

        System.out.println("\nGoodbye!");
        save(courses, nonCourses, groups);

    }

    public static void introduce()
    {
        System.out.println("What would you like to do?");
        System.out.println("Enter the number to an option that suits you:");

        System.out.println("1 - Create a course block");
        System.out.println("2 - Create a group");
        System.out.println("3 - Create a non-course block");
        System.out.println("4 - Check for a conflict in a group");
        System.out.println("5 - Add to a group");
        System.out.println("6 - Remove from a group");
        System.out.println("7 - Exit");
    }

    public static void performOption(int option, ArrayList<Course> courses, ArrayList<Group> groups, ArrayList<Course> nonCourses)
    {
        switch (option)
        {
            case 1:
                createCourse(courses);
                break;
            case 2:
                createGroup(groups);
                break;
            case 3:
                createNonCourse(nonCourses);
                break;
            case 4:
                checkConflict(groups);
                break;
            case 5:
                addToGroup(groups, courses, nonCourses);
                break;
            case 6:
                removeFromGroup(groups);
            case 7:
                //saveGroup(groups);
                break;
            default:
                break;
        }
    }

    /* Create a course by asking the user for information and add the course to the courses list */
    public static void createCourse(ArrayList<Course> courses)
    {
        Scanner scan = new Scanner(System.in);

        //declare course variables
        String instructorName = "";
        String courseSchedule;
        String courseCode;
        String extraText;

        //ask for the required course information
        System.out.print("Will this course have an instructor. y or n? -> ");
        char forInstructor = scan.nextLine().charAt(0);

        if (forInstructor == 'y')
        {
            System.out.print("Enter the name of the instructor? -> ");
            instructorName = scan.nextLine();
        }

        System.out.print("Enter the course code -> ");
        courseCode = scan.nextLine();

        System.out.print("Enter the course schedule -> ");
        courseSchedule = scan.nextLine();

        System.out.print("Enter the extra text for the course -> ");
        extraText = scan.nextLine();

        Course course = new Course(courseCode, extraText, courseSchedule);

        if (forInstructor == 'y')
        {
            course.setInstructor(new Instructor(instructorName));
        }

        //add the created course to the list
        courses.add(course);

        //give feedback to the user
        System.out.println("Course has been created");
    }

    /* Create a group by asking the user for the name of the group and add the group to the groups list */
    public static void createGroup(ArrayList<Group> groups)
    {
        Scanner scan = new Scanner(System.in);

        //declare group variable
        String groupName;

        //ask the user to enter a new group name as long as the already inputted name is used by another group
        do
        {
            System.out.print("What would you like to name your group? -> ");
            groupName = scan.nextLine();
        } while (groupNameExists(groups, groupName));

        //add the new group to the groups list
        groups.add(new Group(groupName));

        //provide feedback for the user
        System.out.println("Group has been created");
    }

    /* Check if the provided group name matched an already created group */
    private static boolean groupNameExists(ArrayList<Group> groups, String groupName)
    {
        for (Group group : groups)
        {
            if (group.getName().equals(groupName))
            {
                System.out.format("%s is already in use\n", groupName);
                return true;
            }
        }

        return false;
    }

    /* Create a non - course block by asking the user for the required information.
    *  A non - course block is still a course, but it is abstracted from the user */
    public static void createNonCourse(ArrayList<Course> nonCourses)
    {
        Scanner scan = new Scanner(System.in);

        //declare course variables
        String extraText;
        String schedule;

        //get the course information from the user
        System.out.print("What is the display text for the non-course block -> ");
        extraText = scan.nextLine();

        System.out.print("Enter the block schedule -> ");
        schedule = scan.nextLine();

        Course course = new Course(extraText, schedule);

        //add the new course to the non - course list
        nonCourses.add(course);

        //provide feedback for the user
        System.out.println("Non-course block has been created");
    }

    /* Allow the user to check for conflicts in a group by letting them select the group they want to check for conflict */
    public static void checkConflict(ArrayList<Group> groups)
    {
        //make sure that there already exists a group to check for conflict
        if (groupsEmptyError(groups))
        {
            return;
        }

        Scanner scan = new Scanner(System.in);

        //provide options of the group for the user to choose
        int i = 1;
        for (Group group : groups)
        {
            System.out.println(i++ + " - " + group.getName());
        }

        //get the group option from the user
        System.out.print("Enter the number of the group you would like to check the conflict for -> ");
        int index = scan.nextInt();
        index--;

        //display the group to show conflicts or to show the group schedule
        groups.get(index).print();
    }

    /* Allow the user to add a course to a group by letting them choose the group and the block they would like to add */
    public static void addToGroup(ArrayList<Group> groups, ArrayList<Course> courses, ArrayList<Course> nonCourses)
    {
        //make sure that there already exists a group to add to
        if (groupsEmptyError(groups))
        {
            return;
        }

        Scanner scan = new Scanner(System.in);

        //display the group options for the user to choose
        int i = 1;
        for (Group group : groups)
        {
            System.out.println(i++ + " - " + group.getName());
        }

        //get the group option from the user
        System.out.print("Enter the number of the group you would like to add a block to -> ");
        int groupIndex = scan.nextInt();
        groupIndex--;

        //identify the group we will be adding a block to
        Group group = groups.get(groupIndex);

        while (true)
        {
            //determine whether a course or non - course block will be added
            System.out.println("Would you like to add a course block or non-course block?");
            System.out.print("Enter 1 for a course block or 2 for a non-course block. Enter 3 to exit -> ");
            int answer = scan.nextInt();

            if (answer == 3)
            {
                break;
            }
            else if (answer == 1)
            {
                //make sure the course list is not empty first
                if (coursesEmptyError(courses))
                {
                    continue;
                }

                //show the options for the courses
                i = 1;
                for (Course course : courses)
                {
                    System.out.print(i++ + " - ");
                    course.print();
                }
            }
            else if (answer == 2)
            {
                //make sure the non - course list is not empty first
                if (coursesEmptyError(nonCourses))
                {
                    continue;
                }

                //show the options for the non-courses
                i = 1;
                for (Course course : nonCourses)
                {
                    System.out.print(i++ + " - ");
                    course.print();
                }
            }

            //get the block option from the user
            System.out.format("Enter the index of the block you would like to add to %s -> ", group.getName());
            int blockIndex = scan.nextInt();
            blockIndex--;

            if (answer == 1)
            {
                //add a course block and show user feedback
                group.add(courses.get(blockIndex));
                System.out.format("Course block at index %d was added\n", blockIndex+1);
            }
            else if (answer == 2)
            {
                //add a non-course block and show user feedback
                group.add(nonCourses.get(blockIndex));
                System.out.format("Non - Course block at index %d was added\n", blockIndex+1);
            }
        }
    }

    /* Remove a block from a group by getting the block to remove from the user */
    public static void removeFromGroup(ArrayList<Group> groups)
    {
        //make sure there exists a group to remove from
        if (groupsEmptyError(groups))
        {
            return;
        }

        Scanner scan = new Scanner(System.in);

        //show all the options of groups to remove from
        int i = 1;
        for (Group group : groups)
        {
            System.out.println(i++ + " - " + group.getName());
        }

        //get the user to decide on the group to remove from
        System.out.print("Enter the number of the group you would like to remove from -> ");
        int groupIndex = scan.nextInt();
        groupIndex--;

        //select the group that the user chose
        Group group = groups.get(groupIndex);

        //repeatedly allow the user to remove a block from the group
        while (true)
        {
            //get the blocks that the user will remove from the group
            ArrayList<Block> blocks = group.getBlocks();

            //make sure that there is something to remove from the group
            if (blocks.isEmpty())
            {
                System.out.println("There is no block left in this group");
                return;
            }

            //show all the block options in that group
            i = 1;
            for (Block block : blocks)
            {
                System.out.print(i++ + " - ");
                block.print();
            }

            //get the block option from the user
            System.out.print("Enter the index of the block you would like to remove. Enter 0 to quit - >");
            int blockIndex = scan.nextInt();

            if (blockIndex == 0)
            {
                break;
            }

            blockIndex--;

            //remove the block
            group.remove(blocks.get(blockIndex));

            //show user feedback
            System.out.format("Block at index %d was removed\n", blockIndex+1);
        }
    }

    /* Error control for group list */
    private static boolean groupsEmptyError(ArrayList<Group> groups)
    {
        if (groups.isEmpty())
        {
            System.out.println("Create a group first\n");
            return true;
        }
        return false;
    }

    /* Error control for course list */
    private static boolean coursesEmptyError(ArrayList<Course> courses)
    {
        if (courses.isEmpty())
        {
            System.out.println("Create a block first\n");
            return true;
        }
        return false;
    }

    private static void save(ArrayList<Course> courses, ArrayList<Course> nonCourses, ArrayList<Group> groups)
    {
        FileOutputStream fos = null;
        ObjectOutputStream outfile = null;

        ArrayList<String> groupNames = new ArrayList<>();
        ArrayList<ArrayList<Block>> groupBlocks = new ArrayList<>();
        for (Group group : groups)
        {
            groupNames.add(group.getName());
            groupBlocks.add(group.getBlocks());
        }

        try
        {
            File file1 = new File("courses.dat");
            File file2 = new File("noncourses.dat");
            File file3 = new File("groups.dat");
            File file4 = new File("groupnames.dat");

            fos = new FileOutputStream(file1);
            outfile = new ObjectOutputStream(fos);
            outfile.writeObject(courses);

            fos = new FileOutputStream(file2);
            outfile = new ObjectOutputStream(fos);
            outfile.writeObject(nonCourses);

            fos = new FileOutputStream(file3);
            outfile = new ObjectOutputStream(fos);
            outfile.writeObject(groupBlocks);

            fos = new FileOutputStream(file4);
            outfile = new ObjectOutputStream(fos);
            outfile.writeObject(groupNames);
        }
        catch (IOException e)
        {
            System.out.println("Error saving the file");
        }
        finally
        {
            try
            {
                if (outfile != null) {
                    outfile.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("Error closing the file");
            }
        }
    }


    private static void read(ArrayList<Course> courses, ArrayList<Course> nonCourses, ArrayList<Group> groups)
    {
        FileInputStream fis = null;
        ObjectInputStream infile = null;

        try
        {
            File file1 = new File("courses.dat");
            File file2 = new File("noncourses.dat");
            File file3 = new File("groups.dat");
            File file4 = new File("groupnames.dat");

            fis = new FileInputStream(file1);
            infile = new ObjectInputStream(fis);
            courses.addAll((ArrayList<Course>) infile.readObject());

            fis = new FileInputStream(file2);
            infile = new ObjectInputStream(fis);
            nonCourses.addAll((ArrayList<Course>) infile.readObject());

            fis = new FileInputStream(file3);
            infile = new ObjectInputStream(fis);
            ArrayList<ArrayList<Block>> groupBlocks = (ArrayList<ArrayList<Block>>) infile.readObject();

            fis = new FileInputStream(file4);
            infile = new ObjectInputStream(fis);
            ArrayList<String> groupNames = (ArrayList<String>) infile.readObject();

            //load the groups back with the names and blocks
            int i = 0;
            for (String groupName : groupNames)
            {
                 Group group = new Group(groupName);
                 ArrayList<Block> blocks = groupBlocks.get(i++);
                 for (Block block : blocks)
                 {
                     group.add(block);
                 }
                 groups.add(group);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch (IOException e)
        {
            System.out.println("Problem reading the file");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Problem parsing the file");
        }
        finally
        {
            try
            {
                if (infile != null)
                    infile.close();
            }
            catch (IOException e)
            {
                System.out.println("Problem closing the file");
            }
        }
    }

}
