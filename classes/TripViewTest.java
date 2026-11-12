import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

import student.TestCase;

/**
 * Tests the TripView class.
 * 
 * @author Angelina Gust
 * @version Nov 10, 2026
 */
public class TripViewTest
    extends TestCase
{
    private ByteArrayOutputStream output;

    /**
     * Sets up the test output.
     */
    public void setUp()
    {
        output = new ByteArrayOutputStream();
    }


    /**
     * Creates a TripView using simulated input.
     * 
     * @param String
     *            simulatedInput
     * @return TripView for testing
     */
    private TripView createView(String simulatedInput)
    {
        Scanner scanner = new Scanner(simulatedInput);

        PrintStream printStream = new PrintStream(output);

        return new TripView(scanner, printStream);
    }


    /**
     * Tests the displayMenu method.
     */
    public void testDisplayMenu()
    {
        TripView view = createView("");

        view.displayMenu();

        String result = output.toString();

        assertTrue(result.contains("TRIP PLANNER"));
        assertTrue(result.contains("Create Trip"));
        assertTrue(result.contains("Add Activity"));
        assertTrue(result.contains("Delete Activity"));
        assertTrue(result.contains("Exit"));
    }


    /**
     * Tests the getUserChoice method.
     */
    public void testGetUserChoice()
    {
        TripView view = createView("2\n");

        assertEquals(2, view.getUserChoice());
    }


    /**
     * Tests the askTripDestination method.
     */
    public void testAskTripDestination()
    {
        TripView view = createView("New York\n");

        assertEquals("New York", view.askTripDestination());
    }


    /**
     * Tests the askTripDestination method if the input is empty.
     */
    public void testAskTripDestinationEmpty()
    {
        TripView view = createView("\nBoston\n");

        assertEquals("Boston", view.askTripDestination());

        assertTrue(output.toString().contains("Destination can't be empty"));
    }


    /**
     * Tests the askTripStartDate method.
     */
    public void testAskTripStartDate()
    {
        TripView view = createView("2026-01-01\n");

        assertEquals(LocalDate.of(2026, 1, 1), view.askTripStartDate());
    }


    /**
     * Tests the askTripStartDate method if the date is invalid.
     */
    public void testAskTripStartDateInvalid()
    {
        TripView view = createView("January 1\n2026-01-01\n");

        assertEquals(LocalDate.of(2026, 1, 1), view.askTripStartDate());

        assertTrue(output.toString().contains("Invalid date."));
    }


    /**
     * Tests the askTripEndDate method.
     */
    public void testAskTripEndDate()
    {
        TripView view = createView("2026-02-01");

        LocalDate start = LocalDate.of(2026, 1, 1);

        assertEquals(LocalDate.of(2026, 2, 1), view.askTripEndDate(start));
    }


    /**
     * Tests the askTripEndDate method if the given end date is before the start
     * date.
     */
    public void testAskTripEndDateBeforeStart()
    {
        TripView view = createView("2025-12-31\n2026-02-01\n");

        LocalDate start = LocalDate.of(2026, 1, 1);

        assertEquals(LocalDate.of(2026, 2, 1), view.askTripEndDate(start));

        assertTrue(
            output.toString()
                .contains("End date can't start before the start date."));
    }


    /**
     * Tests the askActivityDetails method. 
     */
    public void testAskActivityDetails()
    {
        TripView view = createView(
            "Visit Museum\n" + "The MET\n" + "2026-01-06\n" + "10:30\n"
                + "Explore the museum\n");

        Activity activity = view.askActivityDetails();

        assertEquals("Visit Museum", activity.getName());
        assertEquals("The MET", activity.getLocation());
        assertEquals(LocalDate.of(2026, 1, 6), activity.getDate());
        assertEquals(LocalTime.of(10, 30), activity.getTime());
        assertEquals("Explore the museum", activity.getDescription());
    }


    /**
     * Tests the askActivityDetails method if the name is empty. 
     */
    public void testAskActivityDetailsEmptyName()
    {
        TripView view = createView(
            "\n" + "Visit Museum\n" + "The MET\n" + "2026-01-06\n" + "10:30\n"
                + "Explore the museum\n");

        Activity activity = view.askActivityDetails();

        assertEquals("Visit Museum", activity.getName());

        assertTrue(output.toString().contains("This field can't be empty."));
    }


    /**
     * Tests the askActivityDetails method if there's an invalid date.
     */
    public void testAskActivityDetailsInvalidDate()
    {
        TripView view = createView(
            "Visit Museum\n" + "The MET\n" + "not-a-date\n" + "2026-01-06\n"
                + "10:30\n" + "Explore the museum\n");

        Activity activity = view.askActivityDetails();

        assertEquals(LocalDate.of(2026, 1, 6), activity.getDate());

        assertTrue(output.toString().contains("Invalid date."));
    }


    /**
     * Tests the askActivityDetails method if there's an invalid time. 
     */
    public void testAskActivityDetailsInvalidTime()
    {
        TripView view = createView(
            "Visit Museum\n" + "The MET\n" + "2026-01-06\n" + "invalid\n"
                + "10:30\n" + "Explore the museum\n");

        Activity activity = view.askActivityDetails();

        assertEquals(LocalTime.of(10, 30), activity.getTime());

        assertTrue(output.toString().contains("Invalid time."));
    }


    /**
     * Tests the displayActivity method.
     */
    public void testDisplayActivity()
    {
        Activity activity = new Activity(
            "Visit Museum",
            "The MET",
            LocalDate.of(2026, 1, 6),
            LocalTime.of(10, 30),
            "Explore the museum");

        TripView view = createView("");

        view.displayActivity(activity);

        String result = output.toString();

        assertTrue(result.contains("Visit Museum"));
        assertTrue(result.contains("The MET"));
        assertTrue(result.contains("2026-01-06"));
        assertTrue(result.contains("10:30"));
        assertTrue(result.contains("Explore the museum"));
    }


    /**
     * Tests the displayActivities method.
     */
    public void testDisplayActivities()
    {
        Activity first = new Activity(
            "Visit Museum",
            "The MET",
            LocalDate.of(2026, 1, 6),
            LocalTime.of(10, 30),
            "Explore the museum");

        Activity second = new Activity(
            "Visit Park",
            "Central Park",
            LocalDate.of(2026, 1, 6),
            LocalTime.of(14, 30),
            "Explore the park");

        TripView view = createView("");

        view.displayActivities(Arrays.asList(first, second));

        String result = output.toString();

        assertTrue(result.contains("Visit Museum"));
        assertTrue(result.contains("Visit Park"));
    }


    /**
     * Tests the displayActivities method if it is empty. 
     */
    public void testDisplayActivitiesEmpty()
    {
        TripView view = createView("");

        view.displayActivities(Arrays.asList());

        assertTrue(output.toString().contains("No activities to display."));
    }


    /**
     * Tests the displayMessage method.
     */
    public void testDisplayMessage()
    {
        TripView view = createView("");

        view.displayMessage("Trip created successfully!");

        assertTrue(output.toString().contains("Trip created successfully!"));
    }


    /**
     * Tests the getUserChoice method if the input is invalid. 
     */
    public void testGetUserChoiceInvalid()
    {
        TripView view = createView("hello\n2\n");

        assertEquals(2, view.getUserChoice());

        assertTrue(
            output.toString()
                .contains("Invalid choice. Please enter a number."));
    }

}