import java.util.*;
import java.io.*;

public class Group implements Serializable
{
    private String name;
    //use tree maps to keep the order of the day when printing
    private final TreeMap<String, ArrayList<Block>> groups;

    public Group(String name)
    {
        this.name = name;
        List<String> dayOrder = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        groups = new TreeMap<>(Comparator.comparingInt(dayOrder::indexOf));
    }

    public void add(Block block)
    {
        groups.putIfAbsent(block.getDay(), new ArrayList<Block>());
        groups.get(block.getDay()).add(block);
    }

    //automatically adds course to its day group
    //if that course is already in the group replace the existing course with the new course
    public void add(Course course)
    {
        ArrayList<String> days = course.getDays();
        ArrayList<Integer> startTimeMagnitudes = course.getStartTimeMagnitudes();
        ArrayList<Integer> endTimeMagnitudes = course.getEndTimeMagnitudes();
        ArrayList<Integer> startHours = course.getStartHours();
        ArrayList<Integer> endHours = course.getEndHours();
        ArrayList<Integer> startMinutes = course.getStartMinutes();
        ArrayList<Integer> endMinutes = course.getEndMinutes();

        String displayText;



        for (int i = 0; i < days.size(); i++)
        {
            int startHour = startHours.get(i);
            int endHour = endHours.get(i);
            int startMinute = startMinutes.get(i);
            int endMinute = endMinutes.get(i);

            displayText = createDisplayText(days.get(i), startHour, endHour, startMinute, endMinute, course.getCourseCode(), course.getExtraText(), course.getInstructorName());

            Block block = new Block(displayText, days.get(i), startTimeMagnitudes.get(i), endTimeMagnitudes.get(i));
            String day = block.getDay();

            this.groups.putIfAbsent(day, new ArrayList<>());

            ArrayList<Block> list = this.groups.get(day);

            list.add(block);
            Collections.sort(list);

            this.groups.put(day, list);
        }
    }

    //check for conflicts between all courses in day group
    private boolean isConflict()
    {
        boolean result = false;

        for (HashMap.Entry<String, ArrayList<Block>> temp : this.groups.entrySet())
        {
            ArrayList<Block> list = temp.getValue();

            if (list.size() < 2)
            {
                continue;
            }

            for (int i = 0; i < list.size() - 1; i++)
            {
                Block prev = list.get(i);

                for (int j = i + 1; j < list.size(); j++)
                {
                    Block current = list.get(j);

                    if (current.getStartTimeMagnitude() < prev.getEndTimeMagnitude())
                    {
                        result = true;
                        System.out.println("There is a conflict in these 2 blocks");
                        System.out.println(current.getDay() + ":");
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

        for (HashMap.Entry<String, ArrayList<Block>> temp : this.groups.entrySet())
        {
            ArrayList<Block> list = temp.getValue();

            if (!list.isEmpty())
            {
                //print the day
                System.out.println(list.get(0).getDay() + ":");
            }

            for (Block block : list)
            {
                block.print();
            }
        }
    }

    //change the name of the group
    public void setName(String name)
    {
        this.name = name;
    }

    //remove a course from a group
    //loop through the treemap and remove every occurrence of the course
    public void remove(Course course)
    {
        ArrayList<String> days = course.getDays();
        ArrayList<Integer> startTimeMagnitudes = course.getStartTimeMagnitudes();
        ArrayList<Integer> endTimeMagnitudes = course.getEndTimeMagnitudes();
        ArrayList<Integer> startHours = course.getStartHours();
        ArrayList<Integer> endHours = course.getEndHours();
        ArrayList<Integer> startMinutes = course.getStartMinutes();
        ArrayList<Integer> endMinutes = course.getEndMinutes();

        String displayText;

        for (int i = 0; i < days.size(); i++)
        {
            displayText = createDisplayText(days.get(i), startHours.get(i), endHours.get(i), startMinutes.get(i), endMinutes.get(i), course.getCourseCode(), course.getExtraText(), course.getInstructorName());

            ArrayList<Block> list = this.groups.get(formatDay(days.get(i)));
            Iterator<Block> iterator = list.iterator();

            while (iterator.hasNext())
            {
                Block block = iterator.next();
                if (block.getDisplayText().equals(displayText))
                {
                    iterator.remove();
                }
            }
        }


    }

    public void remove(Block block)
    {
        for (HashMap.Entry<String, ArrayList<Block>> temp : this.groups.entrySet())
        {
            temp.getValue().remove(block);
        }
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

    private static String hourToMeridiem(int hour)
    {
        if (hour < 12)
        {
            return "AM";
        }
        else
        {
            return "PM";
        }
    }

    private static int to12hr(int hour)
    {
        if (hour == 0)
        {
            return 12;
        }
        if (hour > 12)
        {
            return hour - 12;
        }
        return hour;
    }

    private static String createDisplayText(String day, int startHour, int endHour, int startMinute, int endMinute, String courseCode, String extraText, String instructorName)
    {
        String blockSchedule = String.format("%s %d:%02d%s-%d:%02d%s", day, to12hr(startHour), startMinute, hourToMeridiem(startHour), to12hr(endHour), endMinute, hourToMeridiem(endHour));
        String displayText;

        if (courseCode.isEmpty())
        {
            //for non-course block
            displayText = String.format("%s, %s", extraText, blockSchedule);
        }
        else {
            //for the course block
            displayText = String.format("%s, %s, %s, %s", courseCode, extraText, instructorName, blockSchedule);
        }

        return displayText;
    }

    //get the full string of the course day
    public String formatDay(String day)
    {
        return switch (day)
        {
            case "F" -> "Friday";
            case "Th" -> "Thursday";
            case "W" -> "Wednesday";
            case "T" -> "Tuesday";
            default -> "Monday";
        };
    }

    public String getName()
    {
        return this.name;
    }

    public ArrayList<Block> getBlocks()
    {
        ArrayList<Block> res = new ArrayList<>();

        for (HashMap.Entry<String, ArrayList<Block>> temp : this.groups.entrySet())
        {
            ArrayList<Block> list = temp.getValue();
            res.addAll(list);
        }

        return res;
    }
}
