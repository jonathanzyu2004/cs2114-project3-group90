import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.io.PrintStream;

/**
 * Handles all of the user input and output for the Trip Planner.
 * 
 * @author Angelina Gust
 * @version Nov 10, 2026
 */
public class TripView
{
    private Scanner input;
    private PrintStream output;

    /**
     * Creates a TripView that allows input and output.
     */
    public TripView()
    {
        this(new Scanner(System.in), System.out);
    }


    /**
     * Create a TripView with supplied Scanner input.
     * 
     * @param Scanner input 
     */
    public TripView(Scanner input)
    {
        this(input, System.out);
    }


    /**
     * Create a TripView with supplied Scanner input and PrintStream output.
     * 
     * @param Scanner input
     * @param PrintStream output
     */
    public TripView(Scanner input, PrintStream output)
    {
        this.input = input;
        this.output = output;
    }


    /**
     * Displays the systems menu.
     */
    public void displayMenu()
    {
        output.println("\nTRIP PLANNER");
        output.println("1. Create Trip");
        output.println("2. Add Activity");
        output.println("3. Edit Activity");
        output.println("4. Delete Activity");
        output.println("5. Complete Next Activity");
        output.println("6. View Next Activity");
        output.println("7. View Upcoming Activities");
        output.println("8. View Past Activities");
        output.println("9. Exit");
    }


    /**
     * Gets the users choice selection. 
     * 
     * @return the integer selected by the user
     */
    public int getUserChoice()
    {
        while (true)
        {
            String response = readInput("Enter your choice: ");

            try
            {
                return Integer.parseInt(response.trim());
            }

            catch (NumberFormatException e)
            {
                output.println("Invalid choice. Please enter a number.");
            }
        }
    }


    /**
     * Asks the user for the trip destination.
     * 
     * @return the destination entered
     */
    public String askTripDestination()
    {
        while (true)
        {
            String destination = readInput("Enter trip destination: ").trim();

            if (!destination.isEmpty())
            {
                return destination;
            }

            output.println("Destination can't be empty.");
        }
    }


    /**
     * Asks the user for the trip start date.
     * 
     * @return the start date entered
     */
    public LocalDate askTripStartDate()
    {
        return askDate("Enter trip start date (YYYY-MM-DD): ");
    }


    /**
     * Asks the user for the trip end date.
     * 
     * @return the end date entered
     */
    public LocalDate askTripEndDate(LocalDate startDate)
    {
        while (true)
        {
            LocalDate endDate = askDate("Enter trip end date (YYYY-MM-DD): ");

            if (!endDate.isBefore(startDate))
            {
                return endDate;
            }

            output.println("End date can't start before the start date.");
        }
    }


    /**
     * Reads and validates a date.
     * 
     * @param String prompt
     * @return valid LocalDate
     */
    private LocalDate askDate(String prompt)
    {
        while (true)
        {
            String response = readInput(prompt).trim();

            try
            {
                return LocalDate.parse(response);
            }
            catch (DateTimeParseException e)
            {
                output.println("Invalid date. Use YYYY-MM-DD.");
            }
        }
    }


    /**
     * Asks the user for activity details and creates the activity accordingly. 
     * 
     * @return the activity created by the user
     */
    public Activity askActivityDetails()
    {
        output.println("\nAdd New Activity");

        String name = askRequiredText("Activity name: ");
        String location = askRequiredText("Activity location: ");

        LocalDate date = askDate("Activity Date (YYYY-MM-DD): ");
        LocalTime time = askTime("Activity Time (HH:MM): ");

        String description =
            readInput("Activity description (press Enter for default): ");

        return new Activity(name, location, date, time, description);
    }


    /**
     * Asks for a non-empty text value.
     * 
     * @param String prompt
     * @return non-empty response
     */
    private String askRequiredText(String prompt)
    {
        while (true)
        {
            String response = readInput(prompt).trim();

            if (!response.isEmpty())
            {
                return response;
            }

            output.println("This field can't be empty.");
        }
    }


    /**
     * Reads and validates a time.
     * 
     * @param String prompt
     * @return valid LocalTime
     */
    private LocalTime askTime(String prompt)
    {
        while (true)
        {
            String response = readInput(prompt).trim();

            try
            {
                return LocalTime.parse(response);
            }

            catch (DateTimeParseException e)
            {
                output.println("Invalid time. Use HH:MM (24 hour format).");
            }
        }
    }


    /**
     * Displays one activity.
     * 
     * @param activity activity 
     */
    public void displayActivity(Activity activity)
    {
        if (activity == null)
        {
            output.println("No activity available.");
            return;
        }

        output.println("\nActivity Details");
        output.println("Name: " + activity.getName());
        output.println("Location: " + activity.getLocation());
        output.println("Date: " + activity.getDate());
        output.println("Time: " + activity.getTime());
        output.println("Description: " + activity.getDescription());
        output.println("Completed: " + activity.isCompleted());
    }


    /**
     * Displays a list of activities.
     * 
     * @param List<Activity> activities
     */
    public void displayActivities(List<Activity> activities)
    {
        if (activities == null || activities.isEmpty())
        {
            output.println("No activities to display.");
            return;
        }

        output.println("\nActivities");

        for (Activity activity : activities)
        {
            displayActivity(activity);
        }
    }


    /**
     * Displays a general message.
     * 
     * @param String message
     */
    public void displayMessage(String message)
    {
        output.println(message);
    }


    /**
     * Reads one line of input. 
     * 
     * @param String prompt
     * @return input entered by user
     */
    private String readInput(String prompt)
    {
        output.print(prompt);

        if (!input.hasNextLine())
        {
            throw new IllegalStateException("No more user input available.");
        }

        return input.nextLine();
    }

}
