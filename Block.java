import java.util.ArrayList;
import java.io.*;

public class Block implements Comparable<Block>, Serializable
{
    private String displayText;
    private int startTimeMagnitude;
    private int endTimeMagnitude;
    private String day;
    private String schedule;

    public Block(String displayText, String day, int startTimeMagnitude, int endTimeMagnitude)
    {
        this.displayText = displayText;
        this.startTimeMagnitude = startTimeMagnitude;
        this.endTimeMagnitude = endTimeMagnitude;
        this.day = day;
    }

    public Block(String displayText, Instructor instructor, String schedule)
    {
        //block will be created similar to a course
        Course course = new Course("", displayText, schedule, instructor);
    }

    public int compareTo(Block other)
    {
        return Integer.compare(startTimeMagnitude, other.getStartTimeMagnitude());
    }

    public int getStartTimeMagnitude()
    {
        return this.startTimeMagnitude;
    }

    public int getEndTimeMagnitude()
    {
        return this.endTimeMagnitude;
    }

    public String getDay()
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

    public void print()
    {
        System.out.println(this.displayText);
    }

    public String getDisplayText()
    {
        return this.displayText;
    }
}