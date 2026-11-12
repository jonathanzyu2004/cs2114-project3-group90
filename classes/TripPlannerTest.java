
import student.TestCase;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Tests the functionality of the TripPlanner controller.
 *
 * @author Jonathan Yu
 * @version 2026.11.11
 */
public class TripPlannerTest
    extends TestCase
{

    private TripPlanner planner;

    /**
     * Sets up a new TripPlanner before each test.
     */
    public void setUp()
    {
        TripView view = new TripView();
        planner = new TripPlanner(view);
    }


    /**
     * Tests creating a trip with valid information.
     */
    public void testCreateTripValid()
    {
        assertTrue(
            planner.createTrip(
                "New York",
                LocalDate.of(2027, 1, 10),
                LocalDate.of(2027, 1, 15)));
    }


    /**
     * Tests creating a trip with an empty destination.
     */
    public void testCreateTripEmptyDestination()
    {
        assertFalse(
            planner.createTrip(
                "",
                LocalDate.of(2027, 1, 10),
                LocalDate.of(2027, 1, 15)));
    }


    /**
     * Tests creating a trip with a null destination.
     */
    public void testCreateTripNullDestination()
    {
        assertFalse(
            planner.createTrip(
                null,
                LocalDate.of(2027, 1, 10),
                LocalDate.of(2027, 1, 15)));
    }


    /**
     * Tests creating a trip with a null start date.
     */
    public void testCreateTripNullStartDate()
    {
        assertFalse(
            planner.createTrip("New York", null, LocalDate.of(2027, 1, 15)));
    }


    /**
     * Tests creating a trip with a null end date.
     */
    public void testCreateTripNullEndDate()
    {
        assertFalse(
            planner.createTrip("New York", LocalDate.of(2027, 1, 10), null));
    }


    /**
     * Tests creating a trip when the end date is before the start date.
     */
    public void testCreateTripEndBeforeStart()
    {
        assertFalse(
            planner.createTrip(
                "New York",
                LocalDate.of(2027, 1, 15),
                LocalDate.of(2027, 1, 10)));
    }


    /**
     * Tests successfully adding an activity.
     */
    public void testAddActivityValid()
    {
        createValidTrip();

        assertTrue(addTestActivity("Museum", 11, 10));
        assertEquals(1, planner.viewUpcomingActivities().size());
    }


    /**
     * Tests adding an activity without first creating a trip.
     */
    public void testAddActivityWithoutTrip()
    {
        assertFalse(addTestActivity("Museum", 11, 10));
    }


    /**
     * Tests adding an activity with a null name.
     */
    public void testAddActivityNullName()
    {
        createValidTrip();

        assertFalse(
            planner.addActivity(
                null,
                "New York",
                LocalDate.of(2027, 1, 11),
                LocalTime.of(10, 0),
                "Test description"));
    }


    /**
     * Tests adding an activity with an empty name.
     */
    public void testAddActivityEmptyName()
    {
        createValidTrip();

        assertFalse(
            planner.addActivity(
                "",
                "New York",
                LocalDate.of(2027, 1, 11),
                LocalTime.of(10, 0),
                "Test description"));
    }


    /**
     * Tests adding an activity with a null location.
     */
    public void testAddActivityNullLocation()
    {
        createValidTrip();

        assertFalse(
            planner.addActivity(
                "Museum",
                null,
                LocalDate.of(2027, 1, 11),
                LocalTime.of(10, 0),
                "Test description"));
    }


    /**
     * Tests adding an activity with a null date.
     */
    public void testAddActivityNullDate()
    {
        createValidTrip();

        assertFalse(
            planner.addActivity(
                "Museum",
                "New York",
                null,
                LocalTime.of(10, 0),
                "Test description"));
    }


    /**
     * Tests adding an activity with a null time.
     */
    public void testAddActivityNullTime()
    {
        createValidTrip();

        assertFalse(
            planner.addActivity(
                "Museum",
                "New York",
                LocalDate.of(2027, 1, 11),
                null,
                "Test description"));
    }


    /**
     * Tests adding an activity outside the trip dates.
     */
    public void testAddActivityOutsideTripDates()
    {
        createValidTrip();

        assertFalse(addTestActivity("Museum", 16, 10));
    }


    /**
     * Tests preventing duplicate activity names regardless of capitalization.
     */
    public void testAddActivityDuplicateName()
    {
        createValidTrip();

        assertTrue(addTestActivity("Museum", 11, 10));
        assertFalse(addTestActivity("museum", 12, 10));
    }


    /**
     * Tests successfully editing an existing activity.
     */
    public void testEditActivityValid()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        Activity updatedActivity = new Activity(
            "Museum",
            "New York City Museum",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(11, 0),
            "Updated description");

        assertTrue(planner.editActivity("Museum", updatedActivity));
    }


    /**
     * Tests editing an activity using a name with different capitalization.
     */
    public void testEditActivityDifferentCapitalization()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        Activity updatedActivity = new Activity(
            "Museum",
            "New York City Museum",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(11, 0),
            "Updated description");

        assertTrue(planner.editActivity("museum", updatedActivity));
    }


    /**
     * Tests attempting to edit an activity that does not exist.
     */
    public void testEditActivityDoesNotExist()
    {
        createValidTrip();

        Activity updatedActivity = new Activity(
            "Museum",
            "New York",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(10, 0),
            "Updated description");

        assertFalse(planner.editActivity("Museum", updatedActivity));
    }


    /**
     * Tests attempting to edit an activity without creating a trip.
     */
    public void testEditActivityWithoutTrip()
    {
        Activity updatedActivity = new Activity(
            "Museum",
            "New York",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(10, 0),
            "Updated description");

        assertFalse(planner.editActivity("Museum", updatedActivity));
    }


    /**
     * Tests editing an activity with a duplicate name.
     */
    public void testEditActivityDuplicateName()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        Activity updatedActivity = new Activity(
            "Aquarium",
            "New York",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(11, 0),
            "Updated description");

        assertFalse(planner.editActivity("Museum", updatedActivity));
    }


    /**
     * Tests editing an activity to a date outside the trip dates.
     */
    public void testEditActivityOutsideTripDates()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        Activity updatedActivity = new Activity(
            "Museum",
            "New York",
            LocalDate.of(2027, 1, 16),
            LocalTime.of(11, 0),
            "Updated description");

        assertFalse(planner.editActivity("Museum", updatedActivity));
    }


    /**
     * Tests editing an activity with a null updated activity.
     */
    public void testEditActivityNullUpdatedActivity()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        assertFalse(planner.editActivity("Museum", null));
    }


    /**
     * Tests successfully deleting an upcoming activity.
     */
    public void testDeleteActivityValid()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        assertTrue(planner.deleteActivity("Museum"));
        assertNull(planner.viewNextActivity());
    }


    /**
     * Tests deleting an activity using a name with different capitalization.
     */
    public void testDeleteActivityDifferentCapitalization()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        assertTrue(planner.deleteActivity("museum"));
        assertNull(planner.viewNextActivity());
    }


    /**
     * Tests attempting to delete an activity that does not exist.
     */
    public void testDeleteActivityDoesNotExist()
    {
        createValidTrip();

        assertFalse(planner.deleteActivity("Museum"));
    }


    /**
     * Tests attempting to delete an activity without creating a trip.
     */
    public void testDeleteActivityWithoutTrip()
    {
        assertFalse(planner.deleteActivity("Museum"));
    }


    /**
     * Tests successfully completing the next upcoming activity.
     */
    public void testCompleteNextActivityValid()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        assertTrue(planner.completeNextActivity());
        assertEquals(1, planner.viewPastActivities().size());
        assertEquals(0, planner.viewUpcomingActivities().size());
    }


    /**
     * Tests completing an activity when no upcoming activities exist.
     */
    public void testCompleteNextActivityEmpty()
    {
        createValidTrip();

        assertFalse(planner.completeNextActivity());
    }


    /**
     * Tests completing activities in the order they were added.
     */
    public void testCompleteActivitiesInOrder()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        assertTrue(planner.completeNextActivity());
        assertEquals("Museum", planner.viewPastActivities().get(0).getName());

        assertTrue(planner.completeNextActivity());
        assertEquals("Aquarium", planner.viewPastActivities().get(1).getName());

        assertFalse(planner.completeNextActivity());
    }


    /**
     * Tests retrieving the next upcoming activity.
     */
    public void testViewNextActivity()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        assertEquals("Museum", planner.viewNextActivity().getName());
    }


    /**
     * Tests retrieving the next activity when the upcoming list is empty.
     */
    public void testViewNextActivityEmpty()
    {
        createValidTrip();

        assertNull(planner.viewNextActivity());
    }


    /**
     * Tests retrieving all upcoming activities.
     */
    public void testViewUpcomingActivities()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        assertEquals(2, planner.viewUpcomingActivities().size());
    }


    /**
     * Tests retrieving past activities when none have been completed.
     */
    public void testViewPastActivitiesInitiallyEmpty()
    {
        createValidTrip();

        assertEquals(0, planner.viewPastActivities().size());
    }


    /**
     * Tests retrieving past activities after completing an activity.
     */
    public void testViewPastActivitiesAfterCompletion()
    {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        planner.completeNextActivity();

        assertEquals(1, planner.viewPastActivities().size());
    }


    /**
     * Tests view methods when no trip has been created.
     */
    public void testViewMethodsWithoutTrip()
    {
        assertNull(planner.viewNextActivity());
        assertEquals(0, planner.viewUpcomingActivities().size());
        assertEquals(0, planner.viewPastActivities().size());
    }


    /**
     * Creates a valid trip for testing.
     */
    private void createValidTrip()
    {
        planner.createTrip(
            "New York",
            LocalDate.of(2027, 1, 10),
            LocalDate.of(2027, 1, 15));
    }


    /**
     * Adds a test activity using the specified name, day, and hour.
     *
     * @param name
     *            the activity name
     * @param day
     *            the day of January 2027
     * @param hour
     *            the activity start hour
     * @return true if the activity was successfully added
     */
    private boolean addTestActivity(String name, int day, int hour)
    {
        return planner.addActivity(
            name,
            "New York",
            LocalDate.of(2027, 1, day),
            LocalTime.of(hour, 0),
            "Test description");
    }
}
