import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The controller responsible for coordinating interactions between
 * the TripView and Trip model.
 *
 * TripPlanner handles user actions, validates input, and delegates
 * operations to the appropriate model objects.
 *
 * @author Jonathan Yu
 * @version 2026.11.10
 */

public class TripPlanner {
    private Trip trip;
    private TripView view;

    /**
     * Constructs a TripPlanner with the specified view.
     *
     * @param view the view used to interact with the user
     */
    public TripPlanner(TripView view) {
        this.view = view;
        this.trip = null;
    }
    
    /**
     * Creates a new trip after validating the provided information.
     *
     * @param destination the destination of the trip
     * @param startDate the starting date of the trip
     * @param endDate the ending date of the trip
     * @return true if the trip was successfully created, false otherwise
     */
    public boolean createTrip(String destination, LocalDate startDate,
            LocalDate endDate) {

        return false;
    }

    /**
     * Creates a new activity and adds it to the current trip.
     *
     * @param name the name of the activity
     * @param location the location where the activity takes place
     * @param date the date of the activity
     * @param time the starting time of the activity
     * @param description a description of the activity
     * @return true if the activity was successfully added, false otherwise
     */
    public boolean addActivity(String name, String location, LocalDate date,
            LocalTime time, String description) {

        return false;
    }

    /**
     * Updates an existing activity in the current trip.
     *
     * @param name the name of the activity to be updated
     * @param updatedActivity the activity containing the updated information
     * @return true if the activity was successfully updated, false otherwise
     */
    public boolean editActivity(String name, Activity updatedActivity) {

        return false;
    }

    /**
     * Removes an upcoming activity from the current trip.
     *
     * @param name the name of the activity to be deleted
     * @return true if the activity was successfully deleted, false otherwise
     */
    public boolean deleteActivity(String name) {

        return false;
    }

    /**
     * Completes the next upcoming activity and moves it to the past
     * activities list.
     *
     * @return true if an activity was successfully completed,
     *         false otherwise
     */
    public boolean completeNextActivity() {

        return false;
    }

    /**
     * Retrieves the next upcoming activity in the current trip.
     *
     * @return the next upcoming Activity, or null if none exists
     */
    public Activity viewNextActivity() {

        return null;
    }

    /**
     * Retrieves all upcoming activities in the current trip.
     *
     * @return a list containing all upcoming activities
     */
    public List<Activity> viewUpcomingActivities() {

        return null;
    }

    /**
     * Retrieves all completed activities in the current trip.
     *
     * @return a list containing all past activities
     */
    public List<Activity> viewPastActivities() {

        return null;
    }
}