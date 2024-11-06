import java.util.*;

public class Group
{
    private String name;
    //use tree maps to keep the order of the day when printing
    private final TreeMap<String, ArrayList<Course>> groups;

    public Group(String name)
    {
        this.name = name;
        List<String> dayOrder = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        groups = new TreeMap<>(Comparator.comparingInt(dayOrder::indexOf));
    }

    //automatically adds course to its day group
    //if that course is already in the group replace the existing course with the new course
    public void add(Course course)
    {
        String day = course.formatDay();
        this.groups.putIfAbsent(day, new ArrayList<>());

        ArrayList<Course> list = this.groups.get(day);

        removeIfContained(list, course);
        list.add(course);
        Collections.sort(list);

        this.groups.put(day, list);
    }

    //check for conflicts between all courses in day group
    private boolean isConflict()
    {
        boolean result = false;

        for (HashMap.Entry<String, ArrayList<Course>> temp : this.groups.entrySet())
        {
            ArrayList<Course> list = temp.getValue();

            if (list.size() < 2)
            {
                continue;
            }

            for (int i = 0; i < list.size() - 1; i++)
            {
                Course prev = list.get(i);

                for (int j = i + 1; j < list.size(); j++)
                {
                    Course current = list.get(j);

                    if (current.getStartTimeMagnitude() < prev.getEndTimeMagnitude())
                    {
                        result = true;
                        System.out.println("There is a conflict in these 2 courses");
                        System.out.println(current.formatDay() + ":");
                        prev.print();
                        current.print();
                    }
                }
            }
        }

        return result;
    }

    //print all the courses nicely assuming there are no conflicts
    public void print()
    {
        if (isConflict())
        {
            return;
        }

        System.out.println(this.name);

        for (HashMap.Entry<String, ArrayList<Course>> temp : this.groups.entrySet())
        {
            ArrayList<Course> list = temp.getValue();

            if (!list.isEmpty())
            {
                //print the day
                System.out.println(list.get(0).formatDay() + ":");
            }

            for (Course course : list)
            {
                course.print();
            }
        }
    }

    //change the name of the group
    public void setName(String name)
    {
        this.name = name;
    }

    //remove a course from a group
    public void remove(Course course)
    {
        removeIfContained(this.groups.get(course.formatDay()), course);
    }

    //remove a course from its day group if that course exists in the day group
    private void removeIfContained(ArrayList<Course> list, Course course)
    {
        for (Course temp : list)
        {
            if (course.getCourseCode().equals(temp.getCourseCode()) && course.getExtraText().equals(temp.getExtraText()))
            {
                list.remove(temp);
                return;
            }
        }
    }
}