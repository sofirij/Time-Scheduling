public class Course implements Comparable<Course>
{
    private String courseCode;
    private String extraText;
    
    //schedule to be in this format "F 2:30PM-4:20PM"
    private String courseSchedule;
    private Instructor instructor;
    
    //hour here is in 24-hour format
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String day;
    private int startTimeMagnitude;
    private int endTimeMagnitude;
    
    //course could be created with or without an instructor
    public Course(String courseCode, String extraText, String courseSchedule)
    {
        this.courseCode = courseCode;
        this.extraText = extraText;
        this.courseSchedule = courseSchedule;
        instructor = new Instructor("No instructor");
        
        setTimes(courseSchedule);
        
        this.startTimeMagnitude = startHour * 60 + startMinute;
        this.endTimeMagnitude = endHour * 60 + endMinute;
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
    
    //store the time individually for simplified calculation
    public void setTimes(String courseSchedule)
    {
        this.courseSchedule = courseSchedule;

        //"F" "2:30PM-4:20PM"
        String[] schedule = courseSchedule.split(" ");
        
        //"F"
        this.day = schedule[0];
        
        //"2:30PM" "4:20PM"
        String[] times = schedule[1].split("-");
        
        //"2" "30PM"
        String[] startTime = times[0].split(":");
        
        //2
        this.startHour = Integer.parseInt(startTime[0]);
        String startMeridiem = startTime[1].substring(2);
        
        //30
        this.startMinute = Integer.parseInt(startTime[1].split("\\p{Alpha}")[0]);
        
        //"4" "20PM"
        String[] endTime = times[1].split(":");
        
        //4
        this.endHour = Integer.parseInt(endTime[0]);
        String endMeridiem = endTime[1].substring(2);
        
        //20
        this.endMinute = Integer.parseInt(endTime[1].split("\\p{Alpha}")[0]);
        
        startHour = hourFormat(startHour, startMeridiem);
        endHour = hourFormat(endHour, endMeridiem);
        
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
    public int compareTo(Course other)
    {
        return Integer.compare(this.startTimeMagnitude, other.startTimeMagnitude);
    }
    
    //print course nicely
    public void print()
    {
        System.out.format("%s, %s, %s, %s\n", courseCode, extraText, instructor.getName(), courseSchedule);
    }
    
    //get the full string of the course day
    public String formatDay()
    {
        return switch (this.day)
        {
            case "F" -> "Friday";
            case "Th" -> "Thursday";
            case "W" -> "Wednesday";
            case "T" -> "Tuesday";
            default -> "Monday";
        };
    }

    public String getDay()
    {
        return this.day;
    }

    public int getStartTimeMagnitude()
    {
        return this.startTimeMagnitude;
    }

    public int getEndTimeMagnitude()
    {
        return this.endTimeMagnitude;
    }

    public String getCourseCode()
    {
        return this.courseCode;
    }

    public String getExtraText()
    {
        return this.extraText;
    }
} 