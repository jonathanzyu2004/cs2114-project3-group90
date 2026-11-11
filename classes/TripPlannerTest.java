import student.TestCase;
import java.time.LocalDate;
import java.time.LocalTime;

public class TripPlannerTest
    extends TestCase
{
    // ~ Fields ................................................................
    private TripPlanner planner;

    // ~ Constructors ..........................................................
    public void setUp()
    {
        TripView view = new TripView();
        planner = new TripPlanner(view);
    }


    // ~Public Methods ........................................................
    public void testCreateTripValid()
    {
        assertTrue(
            planner.createTrip(
                "New York",
                LocalDate.of(2027, 1, 10),
                LocalDate.of(2027, 1, 15)));
    }


    public void testCreateTripEmptyDestination()
    {
        assertFalse(
            planner.createTrip(
                "",
                LocalDate.of(2027, 1, 10),
                LocalDate.of(2027, 1, 15)));
    }


    public void testCreateTripNullDestination()
    {
        assertFalse(
            planner.createTrip(
                null,
                LocalDate.of(2027, 1, 10),
                LocalDate.of(2027, 1, 15)));
    }


    public void testCreateTripNullDate()
    {
        assertFalse(
            planner.createTrip("New York", null, LocalDate.of(2027, 1, 15)));
    }


    public void testCreateTripEndBeforeStart() {
        assertFalse(planner.createTrip(
            "New York",
            LocalDate.of(2027, 1, 15),
            LocalDate.of(2027, 1, 10)
        ));

    }
    
    public void testAddActivityNullTime() {
        createValidTrip();

        assertFalse(planner.addActivity(
            "Museum",
            "New York",
            LocalDate.of(2027, 1, 11),
            null,
            "Test description"
        ));
    }

    public void testAddActivityOutsideTripDates() {
        createValidTrip();

        assertFalse(addTestActivity("Museum", 16, 10));
    }
    
    public void testEditActivityValid() {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        Activity updatedActivity = new Activity(
            "Museum",
            "New York City Museum",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(11, 0),
            "Updated description"
        );

        assertTrue(planner.editActivity("Museum", updatedActivity));
    }

    public void testEditActivityDoesNotExist() {
        createValidTrip();

        Activity updatedActivity = new Activity(
            "Museum",
            "New York",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(10, 0),
            "Updated description"
        );

        assertFalse(planner.editActivity("Museum", updatedActivity));
    }

    public void testEditActivityDuplicateName() {
        createValidTrip();

        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        Activity updatedActivity = new Activity(
            "Aquarium",
            "New York",
            LocalDate.of(2027, 1, 11),
            LocalTime.of(11, 0),
            "Updated description"
        );

        assertFalse(planner.editActivity("Museum", updatedActivity));
    }

    public void testEditActivityOutsideTripDates() {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        Activity updatedActivity = new Activity(
            "Museum",
            "New York",
            LocalDate.of(2027, 1, 16),
            LocalTime.of(11, 0),
            "Updated description"
        );

        assertFalse(planner.editActivity("Museum", updatedActivity));
    }
    
    public void testDeleteActivityValid() {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        assertTrue(planner.deleteActivity("Museum"));

        assertNull(planner.viewNextActivity());
    }

    public void testDeleteActivityDoesNotExist() {
        createValidTrip();

        assertFalse(planner.deleteActivity("Museum"));
    }

    public void testDeleteActivityWithoutTrip() {
        assertFalse(planner.deleteActivity("Museum"));
    }
    
    public void testCompleteNextActivityValid() {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        assertTrue(planner.completeNextActivity());

        assertEquals(1, planner.viewPastActivities().size());
        assertEquals(0, planner.viewUpcomingActivities().size());
    }

    public void testCompleteNextActivityEmpty() {
        createValidTrip();

        assertFalse(planner.completeNextActivity());
    }

    public void testCompleteActivitiesInOrder() {
        createValidTrip();

        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        assertTrue(planner.completeNextActivity());
        assertEquals("Museum", planner.viewPastActivities().get(0).getName());

        assertTrue(planner.completeNextActivity());
        assertEquals("Aquarium", planner.viewPastActivities().get(1).getName());

        assertFalse(planner.completeNextActivity());
    }
    
    public void testViewNextActivity() {
        createValidTrip();
        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        assertEquals("Museum", planner.viewNextActivity().getName());
    }

    public void testViewNextActivityEmpty() {
        createValidTrip();

        assertNull(planner.viewNextActivity());
    }

    public void testViewUpcomingActivities() {
        createValidTrip();

        addTestActivity("Museum", 11, 10);
        addTestActivity("Aquarium", 12, 10);

        assertEquals(2, planner.viewUpcomingActivities().size());
    }

    public void testViewPastActivitiesInitiallyEmpty() {
        createValidTrip();

        assertEquals(0, planner.viewPastActivities().size());
    }

    public void testViewPastActivitiesAfterCompletion() {
        createValidTrip();
        addTestActivity("Museum", 11, 10);

        planner.completeNextActivity();

        assertEquals(1, planner.viewPastActivities().size());
    }

    public void testViewMethodsWithoutTrip() {
        assertNull(planner.viewNextActivity());
        assertEquals(0, planner.viewUpcomingActivities().size());
        assertEquals(0, planner.viewPastActivities().size());
    }
    // Helper methods
    private void createValidTrip()
    {
        planner.createTrip(
            "New York",
            LocalDate.of(2027, 1, 10),
            LocalDate.of(2027, 1, 15));
    }


    private boolean addTestActivity(String name, int day, int hour) {
        return planner.addActivity(
            name,
            "New York",
            LocalDate.of(2027, 1, day),
            LocalTime.of(hour, 0),
            "Test description"
        );
    }
}
