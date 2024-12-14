import java.util.*;
import java.io.*;
public class Course implements Serializable
{
    private String courseCode;
    private String extraText;
    private Instructor instructor;

    //schedule to be in this format "F 2:30PM-4:20PM"
    private String courseSchedule;

    //split up the course schedule into arraylist of the different elements
    public ArrayList<String> days;

    //hour here is in 24-hour format
    public ArrayList<Integer> startHours;
    public ArrayList<Integer> startMinutes;
    public ArrayList<Integer> startTimeMagnitudes;

    public ArrayList<Integer> endHours;
    public ArrayList<Integer> endMinutes;
    public ArrayList<Integer> endTimeMagnitudes;

    //course could be created with or without an instructor
    public Course(String courseCode, String extraText, String courseSchedule)
    {
        this.courseCode = courseCode;
        this.extraText = extraText;
        this.courseSchedule = courseSchedule;

        days = new ArrayList<>();

        startHours = new ArrayList<>();
        startMinutes = new ArrayList<>();
        startTimeMagnitudes = new ArrayList<>();

        endHours = new ArrayList<>();
        endMinutes = new ArrayList<>();
        endTimeMagnitudes = new ArrayList<>();

        instructor = new Instructor("No instructor");

        setSchedule(courseSchedule);
    }

    //a non-block course is still a course, but it is abstracted from the user
    public Course(String extraText, String courseSchedule)
    {
        this("", extraText, courseSchedule);
    }

    //course could be created with or without an instructor
    public Course(String courseCode, String extraText, String courseSchedule, Instructor instructor)
    {
        this(courseCode, extraText, courseSchedule);
        this.instructor = instructor;
    }

    public void setInstructor(Instructor instructor)
    {
        this.instructor = instructor;
    }

    public void setSchedule(String courseSchedule)
    {
        this.courseSchedule = courseSchedule;

        String[] schedules = courseSchedule.split(";");

        for (String schedule : schedules)
        {
            setIndividualSchedule(schedule.trim());
        }
    }

    public void setIndividualSchedule(String schedule)
    {
        int i = days.size();

        String daysText = schedule.split(" ")[0];
        String timeText = schedule.split(" ")[1];

        setDays(daysText);

        int j = days.size();
        int size = j - i;

        setTimes(size, timeText);
    }

    public void setTimes(int size, String text)
    {
        int startHour;
        int startMinute;

        int endHour;
        int endMinute;

        int startTimeMagnitude;
        int endTimeMagnitude;

        //"2:30PM" "4:20PM"
        String[] times = text.split("-");

        //"2" "30PM"
        String[] startTime = times[0].split(":");

        //2
        startHour = Integer.parseInt(startTime[0]);
        String startMeridiem = startTime[1].substring(2);

        //30
        startMinute = Integer.parseInt(startTime[1].split("\\p{Alpha}")[0]);

        //"4" "20PM"
        String[] endTime = times[1].split(":");

        //4
        endHour = Integer.parseInt(endTime[0]);
        String endMeridiem = endTime[1].substring(2);

        //20
        endMinute = Integer.parseInt(endTime[1].split("\\p{Alpha}")[0]);

        startHour = hourFormat(startHour, startMeridiem);
        endHour = hourFormat(endHour, endMeridiem);

        startTimeMagnitude = startHour * 60 + startMinute;
        endTimeMagnitude = endHour * 60 + endMinute;

        for (int i = 0; i < size; i++)
        {
            startHours.add(startHour);
            startMinutes.add(startMinute);
            startTimeMagnitudes.add(startTimeMagnitude);

            endHours.add(endHour);
            endMinutes.add(endMinute);
            endTimeMagnitudes.add(endTimeMagnitude);
        }
    }

    public int setDays(String text)
    {
        // to be used by the block when creating non-block courses
        int count = 0;


        int i = 0;
        int j = 1;

        while (i < text.length())
        {
            switch (text.substring(i, j))
            {
                case "F":
                    days.add("F");
                    break;
                case "Th":
                    days.add("Th");
                    break;
                case "W":
                    days.add("W");
                    break;
                case "T":
                    if (i+1 < text.length())
                    {
                        if (text.charAt(i+1) == 'h')
                        {
                            j++;
                            continue;
                        }
                        else
                        {
                            days.add("T");
                        }
                    }
                    else
                    {
                        days.add("T");
                    }
                    break;
                default:
                    days.add("M");
            }

            i = j;
            j++;
        }

        return count;
    }

    //format the time to 24 hr format
    private int hourFormat(int hour, String meridiem)
    {
        if (meridiem.equals("AM"))
        {
            if (hour == 12)
            {
                return 0;
            }
            else
            {
                return hour;
            }
        }
        else
        {
            if (hour == 12)
            {
                return hour;
            }
            else
            {
                return hour + 12;
            }
        }
    }

    //implements comparable for sorting courses
    public int compareTo(Course other, String day)
    {
        int index = days.indexOf(day);
        return Integer.compare(startTimeMagnitudes.get(index), other.getStartTimeMagnitudes().get(index));
    }

    //print course nicely
    public void print()
    {
        String displayMessage;
        if (courseCode.isEmpty())
        {
            displayMessage = String.format("%s", extraText);
        }
        else
        {
            displayMessage = String.format("%s, %s", courseCode, extraText);
        }

        System.out.format("%s, %s, %s\n", displayMessage, instructor.getName(), courseSchedule);
    }



    public ArrayList<String> getDays()
    {
        return this.days;
    }

    public ArrayList<Integer> getStartTimeMagnitudes()
    {
        return this.startTimeMagnitudes;
    }

    public ArrayList<Integer> getEndTimeMagnitudes()
    {
        return this.endTimeMagnitudes;
    }

    public String getCourseCode()
    {
        return this.courseCode;
    }

    public String getExtraText()
    {
        return this.extraText;
    }

    public String getInstructorName()
    {
        return this.instructor.getName();
    }

    public String getCourseSchedule()
    {
        return this.courseSchedule;
    }

    public ArrayList<Integer> getStartHours()
    {
        return this.startHours;
    }

    public ArrayList<Integer> getStartMinutes()
    {
        return this.startMinutes;
    }

    public ArrayList<Integer> getEndHours()
    {
        return this.endHours;
    }

    public ArrayList<Integer> getEndMinutes()
    {
        return this.endMinutes;
    }
}
