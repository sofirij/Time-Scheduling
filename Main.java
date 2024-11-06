import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
public class Main
{
    public static void main(String[] args)
    {

        HashMap<String, Course> map = new HashMap<>();
        ArrayList<Course> list = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        while (true)
        {
            System.out.println("Do you wish to enter information: Enter Y or N");

            if (scan.nextLine().equals("N"))
            {
                break;
            }

            System.out.println("Enter the course code:");
            String courseCode = scan.nextLine();

            System.out.println("Enter the extra text for the course:");
            String extraText = scan.nextLine();

            System.out.println("Enter the course schedule for example 'F 2:30PM-4:20PM'");
            String courseSchedule = scan.nextLine();

            System.out.println("Enter the instructor name:");
            Instructor instructor = new Instructor(scan.nextLine());

            Course course = new Course(courseCode, extraText, courseSchedule, instructor);
            String key = String.format("%s %s", courseCode, extraText);
            map.put(key, course);
            list.add(course);
        }


        int i = 1;
        for (Course course : map.values())
        {
            System.out.print(i++ + " - ");
            course.print();
        }

        System.out.println("The numbers to the left of the course correspond to the course");
        System.out.println("You will enter that number when prompted to add a course to a group");
        System.out.println("If you wish to not enter anymore to the group enter 0");
        System.out.println("After you enter 0 your group will be printed to show whether there was a conflict or not\n");

        System.out.println("What would you like to be the name of the group?");
        Group group = new Group(scan.nextLine());


        while (true)
        {
            System.out.println("What would you like to enter to the group?");
            int num = scan.nextInt();

            if (num == 0)
            {
                break;
            }

            group.add(list.get(num - 1));
        }

        group.print();

    }
}