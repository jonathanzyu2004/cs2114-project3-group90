import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public class Activity
{
    private String name;
    private String location;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private boolean completed;
    private LocalTime endTime;

    /**
     * Constructs a new Activity
     * 
     * @param name
     *            Activity name
     * @param location
     *            Activity location
     * @param date
     *            Activity date
     * @param time
     *            Activity start time
     * @param description
     *            Activity description
     */
    public Activity(
        String name,
        String location,
        LocalDate date,
        LocalTime time,
        String description,
        LocalTime endTime)
    {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.endTime = endTime;
        
        if (description == null || description.trim().isEmpty())
        {
            this.description = "No description";
        }
        else
        {
            this.description = description;
        }
        this.completed = false;
    }


    public String getName()
    {
        return name;
    }


    public String getLocation()
    {
        return location;
    }


    public LocalDate getDate()
    {
        return date;
    }


    public LocalTime getTime()
    {
        return time;
    }


    public String getDescription()
    {
        return description;
    }


    public boolean isCompleted()
    {
        return completed;
    }


    public void setName(String name)
    {
        if (name != null && !name.trim().isEmpty())
        {
            this.name = name;
        }
    }


    public void setLocation(String location)
    {
        if (location != null)
        {
            this.location = location;
        }
    }


    public void setDate(LocalDate date)
    {
        if (date != null)
        {
            this.date = date;
        }
    }


    public void setTime(LocalTime time)
    {
        if (time != null)
        {
            this.time = time;
        }
    }


    public void setDescription(String description)
    {
        if (description != null)
        {
            this.description = description;
        }
    }


    /**
     * Updates completion status
     * 
     * @return boolean updated completion status
     */
    public boolean setCompleted()
    {
        this.completed = true;
        return this.completed;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }

    public Duration getDuration() {
        if (time == null || endTime == null) {
            return Duration.ZERO;
        }

        return Duration.between(time, endTime);
    }

    public boolean hasValidTimeRange() {
        return time != null
            && endTime != null
            && endTime.isAfter(time);
    }
    
    public void setEndTime(LocalTime endTime) {
        if (endTime != null) {
            this.endTime = endTime;
        }
    }
}
