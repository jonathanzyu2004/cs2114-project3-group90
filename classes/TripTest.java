import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TripTest {

    private final LocalDate startDate = LocalDate.of(2026, 6, 1);
    private final LocalDate endDate = LocalDate.of(2026, 6, 10);

    private Activity createActivity(String name) {
        return new Activity(
                name,
                "Toronto",
                LocalDate.of(2026, 6, 2),
                LocalTime.of(10, 0),
                "Example activity");
    }

    // Test Trip constructor
    @Test
    public void testTrip() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertNotNull(trip);
        assertEquals("Toronto", trip.getDestination());
        assertEquals(startDate, trip.getStartDate());
        assertEquals(endDate, trip.getEndDate());
    }

    // Test getters
    @Test
    public void testGetDestination() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertEquals("Toronto", trip.getDestination());
    }

    @Test
    public void testGetStartDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertEquals(startDate, trip.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertEquals(endDate, trip.getEndDate());
    }

    // Test setters
    @Test
    public void testSetDestination() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        trip.setDestination("Montreal");

        assertEquals("Montreal", trip.getDestination());

        trip.setDestination(null);

        assertEquals("Montreal", trip.getDestination());
    }

    @Test
    public void testSetStartDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        LocalDate newDate = LocalDate.of(2026, 6, 3);

        trip.setStartDate(newDate);

        assertEquals(newDate, trip.getStartDate());

        trip.setStartDate(null);

        assertEquals(newDate, trip.getStartDate());
    }

    @Test
    public void testSetEndDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        LocalDate newDate = LocalDate.of(2026, 6, 15);

        trip.setEndDate(newDate);

        assertEquals(newDate, trip.getEndDate());

        trip.setEndDate(null);

        assertEquals(newDate, trip.getEndDate());
    }

    // Test adding activities
    @Test
    public void testAddActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        Activity activity = createActivity("Museum");

        trip.addActivity(activity);

        assertEquals(1, trip.getUpcomingActivities().size());
        assertSame(activity, trip.getUpcomingActivities().get(0));
    }

    @Test
    public void testAddNullActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        trip.addActivity(null);

        assertTrue(trip.getUpcomingActivities().isEmpty());
    }

    // Test removing activities
    @Test
    public void testRemoveActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity activity1 = createActivity("Museum");
        Activity activity2 = createActivity("Restaurant");

        trip.addActivity(activity1);
        trip.addActivity(activity2);

        assertTrue(trip.removeActivity("Museum"));
        assertEquals(1, trip.getUpcomingActivities().size());
        assertSame(activity2, trip.getUpcomingActivities().get(0));
    }

    @Test
    public void testRemoveNonexistentActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        Activity activity = createActivity("Museum");

        trip.addActivity(activity);

        assertFalse(trip.removeActivity("Airport"));
        assertEquals(1, trip.getUpcomingActivities().size());
        assertSame(activity, trip.getUpcomingActivities().get(0));
    }

    // Test editing activities
    @Test
    public void testEditActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity original = createActivity("Museum");
        Activity other = createActivity("Restaurant");
        Activity updated = createActivity("Art Gallery");

        trip.addActivity(original);
        trip.addActivity(other);

        assertTrue(trip.editActivity("Museum", updated));

        List<Activity> activities = trip.getUpcomingActivities();

        assertEquals(2, activities.size());
        assertSame(updated, activities.get(0));
        assertSame(other, activities.get(1));
    }

    @Test
    public void testEditNonexistentActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity original = createActivity("Museum");
        Activity updated = createActivity("Art Gallery");

        trip.addActivity(original);

        assertFalse(trip.editActivity("Airport", updated));

        assertEquals(1, trip.getUpcomingActivities().size());
        assertSame(original, trip.getUpcomingActivities().get(0));
    }

    // Test completing the next activity
    @Test
    public void testCompleteNextActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity activity1 = createActivity("Museum");
        Activity activity2 = createActivity("Restaurant");

        trip.addActivity(activity1);
        trip.addActivity(activity2);

        Activity completed = trip.completeNextActivity();

        assertSame(activity1, completed);
        assertTrue(activity1.isCompleted());
        assertEquals(1, trip.getUpcomingActivities().size());
        assertEquals(1, trip.getPastActivity().size());
        assertSame(activity1, trip.getPastActivity().get(0));
    }

    @Test
    public void testCompleteNextActivityEmptyQueue() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertNull(trip.completeNextActivity());
        assertTrue(trip.getPastActivity().isEmpty());
    }

    // Test upcoming activities
    @Test
    public void testGetUpcomingActivities() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity activity1 = createActivity("Museum");
        Activity activity2 = createActivity("Restaurant");

        trip.addActivity(activity1);
        trip.addActivity(activity2);

        List<Activity> activities = trip.getUpcomingActivities();

        assertEquals(2, activities.size());
        assertSame(activity1, activities.get(0));
        assertSame(activity2, activities.get(1));
    }

    @Test
    public void testGetUpcomingActivitiesEmpty() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertTrue(trip.getUpcomingActivities().isEmpty());
    }

    // Test next activity
    @Test
    public void testGetNextActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity activity1 = createActivity("Museum");
        Activity activity2 = createActivity("Restaurant");

        trip.addActivity(activity1);
        trip.addActivity(activity2);

        assertSame(activity1, trip.getNextActivity());

        // Peeking should not remove the activity
        assertEquals(2, trip.getUpcomingActivities().size());
    }

    @Test
    public void testGetNextActivityEmpty() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertNull(trip.getNextActivity());
    }

    // Test past activities
    @Test
    public void testGetPastActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity activity1 = createActivity("Museum");
        Activity activity2 = createActivity("Restaurant");

        trip.addActivity(activity1);
        trip.addActivity(activity2);

        trip.completeNextActivity();
        trip.completeNextActivity();

        List<Activity> past = trip.getPastActivity();

        assertEquals(2, past.size());
        assertSame(activity1, past.get(0));
        assertSame(activity2, past.get(1));
    }

    @Test
    public void testGetPastActivityEmpty() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertTrue(trip.getPastActivity().isEmpty());
    }
}
