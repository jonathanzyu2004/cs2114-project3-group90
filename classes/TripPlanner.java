
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

/**
 * The controller responsible for coordinating interactions between the TripView
 * and Trip model. TripPlanner handles user actions, validates input, and
 * delegates operations to the appropriate model objects.
 *
 * @author Jonathan Yu
 * @version 2026.11.11
 */
public class TripPlanner
{

    private Trip trip;
    private TripView view;

    /**
     * Constructs a TripPlanner with the specified view.
     *
     * @param view
     *            the view used to interact with the user
     */
    public TripPlanner(TripView view)
    {
        this.view = view;
        this.trip = null;
    }


    /**
     * Creates a new trip after validating the provided information.
     *
     * @param destination
     *            the destination of the trip
     * @param startDate
     *            the starting date of the trip
     * @param endDate
     *            the ending date of the trip
     * @return true if the trip was successfully created, false otherwise
     */
    public
        boolean
        createTrip(String destination, LocalDate startDate, LocalDate endDate)
    {

        if (destination == null || destination.trim().isEmpty())
        {
            return false;
        }

        if (startDate == null || endDate == null)
        {
            return false;
        }

        if (endDate.isBefore(startDate))
        {
            return false;
        }

        trip = new Trip(destination.trim(), startDate, endDate);

        return true;
    }


    /**
     * Creates a new activity and adds it to the current trip.
     *
     * @param name
     *            the name of the activity
     * @param location
     *            the location where the activity takes place
     * @param date
     *            the date of the activity
     * @param time
     *            the starting time of the activity
     * @param description
     *            a description of the activity
     * @return true if the activity was successfully added, false otherwise
     */
    public boolean addActivity(
        String name,
        String location,
        LocalDate date,
        LocalTime time,
        String description)
    {

        if (trip == null)
        {
            return false;
        }

        if (name == null || name.trim().isEmpty() || location == null
            || location.trim().isEmpty() || date == null || time == null)
        {
            return false;
        }

        if (date.isBefore(trip.getStartDate())
            || date.isAfter(trip.getEndDate()))
        {
            return false;
        }

        if (activityNameExists(name))
        {
            return false;
        }

        Activity newActivity =
            new Activity(name.trim(), location.trim(), date, time, description);

        trip.addActivity(newActivity);

        return true;
    }


    /**
     * Updates an existing upcoming activity in the current trip.
     *
     * @param name
     *            the name of the activity to be updated
     * @param updatedActivity
     *            the activity containing the updated information
     * @return true if the activity was successfully updated, false otherwise
     */
    public boolean editActivity(String name, Activity updatedActivity)
    {

        if (trip == null || name == null || name.trim().isEmpty()
            || updatedActivity == null)
        {
            return false;
        }

        String originalName = findUpcomingActivityName(name);

        if (originalName == null)
        {
            return false;
        }

        if (!isValidActivity(updatedActivity))
        {
            return false;
        }

        if (updatedActivity.getDate().isBefore(trip.getStartDate())
            || updatedActivity.getDate().isAfter(trip.getEndDate()))
        {
            return false;
        }

        if (activityNameExistsExcept(updatedActivity.getName(), originalName))
        {
            return false;
        }

        return trip.editActivity(originalName, updatedActivity);
    }


    /**
     * Removes an upcoming activity from the current trip.
     *
     * @param name
     *            the name of the activity to be deleted
     * @return true if the activity was successfully deleted, false otherwise
     */
    public boolean deleteActivity(String name)
    {

        if (trip == null || name == null || name.trim().isEmpty())
        {
            return false;
        }

        String originalName = findUpcomingActivityName(name);

        if (originalName == null)
        {
            return false;
        }

        return trip.removeActivity(originalName);
    }


    /**
     * Completes the next upcoming activity and moves it to the past activities
     * list.
     *
     * @return true if an activity was successfully completed, false otherwise
     */
    public boolean completeNextActivity()
    {

        if (trip == null || trip.getUpcomingActivities().isEmpty())
        {
            return false;
        }

        Activity completed = trip.completeNextActivity();

        return completed != null;
    }


    /**
     * Retrieves the next upcoming activity in the current trip.
     *
     * @return the next upcoming Activity, or null if none exists
     */
    public Activity viewNextActivity()
    {

        if (trip == null || trip.getUpcomingActivities().isEmpty())
        {
            return null;
        }

        return trip.getNextActivity();
    }


    /**
     * Retrieves all upcoming activities in the current trip.
     *
     * @return a list containing all upcoming activities
     */
    public List<Activity> viewUpcomingActivities()
    {

        if (trip == null)
        {
            return new ArrayList<Activity>();
        }

        return trip.getUpcomingActivities();
    }


    /**
     * Retrieves all completed activities in the current trip.
     *
     * @return a list containing all past activities
     */
    public List<Activity> viewPastActivities()
    {

        if (trip == null)
        {
            return new ArrayList<Activity>();
        }

        return trip.getPastActivity();
    }


    /**
     * Checks whether an activity name already exists in either list.
     *
     * @param name
     *            the activity name to check
     * @return true if the name already exists
     */
    private boolean activityNameExists(String name)
    {

        for (Activity activity : trip.getUpcomingActivities())
        {
            if (sameName(activity.getName(), name))
            {
                return true;
            }
        }

        for (Activity activity : trip.getPastActivity())
        {
            if (sameName(activity.getName(), name))
            {
                return true;
            }
        }

        return false;
    }


    /**
     * Finds and returns the actual stored name of an upcoming activity. Name
     * matching ignores capitalization and surrounding whitespace.
     *
     * @param name
     *            the activity name to find
     * @return the exact stored name, or null if the activity does not exist
     */
    private String findUpcomingActivityName(String name)
    {

        if (name == null || trip == null)
        {
            return null;
        }

        for (Activity activity : trip.getUpcomingActivities())
        {
            if (sameName(activity.getName(), name))
            {
                return activity.getName();
            }
        }

        return null;
    }


    /**
     * Checks for duplicate names while excluding the activity being edited.
     *
     * @param newName
     *            the proposed new name
     * @param oldName
     *            the current activity name
     * @return true if another activity already has the new name
     */
    private boolean activityNameExistsExcept(String newName, String oldName)
    {

        for (Activity activity : trip.getUpcomingActivities())
        {
            if (!sameName(activity.getName(), oldName)
                && sameName(activity.getName(), newName))
            {
                return true;
            }
        }

        for (Activity activity : trip.getPastActivity())
        {
            if (sameName(activity.getName(), newName))
            {
                return true;
            }
        }

        return false;
    }


    /**
     * Checks whether two activity names are equivalent. Matching ignores
     * capitalization and surrounding whitespace.
     *
     * @param first
     *            the first name
     * @param second
     *            the second name
     * @return true if the names match
     */
    private boolean sameName(String first, String second)
    {

        return first != null && second != null
            && first.trim().equalsIgnoreCase(second.trim());
    }


    /**
     * Checks whether an activity has all required information.
     *
     * @param activity
     *            the activity to validate
     * @return true if all required fields are valid
     */
    private boolean isValidActivity(Activity activity)
    {

        return activity != null && activity.getName() != null
            && !activity.getName().trim().isEmpty()
            && activity.getLocation() != null
            && !activity.getLocation().trim().isEmpty()
            && activity.getDate() != null && activity.getTime() != null;
    }
}
