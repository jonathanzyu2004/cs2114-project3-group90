
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
//me think one of these shiould work but they don't
import student.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTrip {

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
    public void testTrip() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertNotNull(trip);
        assertEquals("Toronto", trip.getDestination());
        assertEquals(startDate, trip.getStartDate());
        assertEquals(endDate, trip.getEndDate());
    }

    // Test getters
    public void testGetDestination() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertEquals("Toronto", trip.getDestination());
    }

    public void testGetStartDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertEquals(startDate, trip.getStartDate());
    }

    public void testGetEndDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertEquals(endDate, trip.getEndDate());
    }

    // Test setters
    public void testSetDestination() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        trip.setDestination("Montreal");

        assertEquals("Montreal", trip.getDestination());

        trip.setDestination(null);

        assertEquals("Montreal", trip.getDestination());
    }

    public void testSetStartDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        LocalDate newDate = LocalDate.of(2026, 6, 3);

        trip.setStartDate(newDate);

        assertEquals(newDate, trip.getStartDate());

        trip.setStartDate(null);

        assertEquals(newDate, trip.getStartDate());
    }

    public void testSetEndDate() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        LocalDate newDate = LocalDate.of(2026, 6, 15);

        trip.setEndDate(newDate);

        assertEquals(newDate, trip.getEndDate());

        trip.setEndDate(null);

        assertEquals(newDate, trip.getEndDate());
    }

    // Test adding activities
    public void testAddActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        Activity activity = createActivity("Museum");

        trip.addActivity(activity);

        assertEquals(1, trip.getUpcomingActivities().size());
        assertSame(activity, trip.getUpcomingActivities().get(0));
    }

    public void testAddNullActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        trip.addActivity(null);

        assertTrue(trip.getUpcomingActivities().isEmpty());
    }

    // Test removing activities
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

    public void testRemoveNonexistentActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);
        Activity activity = createActivity("Museum");

        trip.addActivity(activity);

        assertFalse(trip.removeActivity("Airport"));
        assertEquals(1, trip.getUpcomingActivities().size());
        assertSame(activity, trip.getUpcomingActivities().get(0));
    }

    // Test editing activities
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
    public void testCompleteNextActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity activity1 = createActivity("Museum");
        Activity activity2 = createActivity("Restaurant");

        trip.addActivity(activity1);
        trip.addActivity(activity2);

        Activity completed = trip.completeNextAcitivity();

        assertSame(activity1, completed);
        assertTrue(activity1.isCompleted());
        assertEquals(1, trip.getUpcomingActivities().size());
        assertEquals(1, trip.getPastActivity().size());
        assertSame(activity1, trip.getPastActivity().get(0));
    }

    public void testCompleteNextActivityEmptyQueue() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertNull(trip.completeNextAcitivity());
        assertTrue(trip.getPastActivity().isEmpty());
    }

    // Test upcoming activities
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

    public void testGetUpcomingActivitiesEmpty() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertTrue(trip.getUpcomingActivities().isEmpty());
    }

    // Test next activity
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

    public void testGetNextActivityEmpty() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertNull(trip.getNextActivity());
    }

    // Test past activities
    public void testGetPastActivity() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        Activity activity1 = createActivity("Museum");
        Activity activity2 = createActivity("Restaurant");

        trip.addActivity(activity1);
        trip.addActivity(activity2);

        trip.completeNextAcitivity();
        trip.completeNextAcitivity();

        List<Activity> past = trip.getPastActivity();

        assertEquals(2, past.size());
        assertSame(activity1, past.get(0));
        assertSame(activity2, past.get(1));
    }

    public void testGetPastActivityEmpty() {
        Trip trip = new Trip("Toronto", startDate, endDate);

        assertTrue(trip.getPastActivity().isEmpty());
    }
}
