
/**
 * Starts and runs the Trip Planner application.
 *
 * @author Jonathan Yu
 * @version 2026.11.12
 */
public class TripPlannerApp
{
    /**
     * Main method that starts the Trip Planner.
     *
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args)
    {
        TripView view = new TripView();
        TripPlanner planner = new TripPlanner(view);

        boolean running = true;

        while (running)
        {
            view.displayMenu();

            int choice = view.getUserChoice();

            switch (choice)
            {
                case 1:
                    String destination = view.askTripDestination();
                    java.time.LocalDate startDate =
                        view.askTripStartDate();
                    java.time.LocalDate endDate =
                        view.askTripEndDate(startDate);

                    if (planner.createTrip(
                        destination, startDate, endDate))
                    {
                        view.displayMessage("Trip created successfully!");
                    }
                    else
                    {
                        view.displayMessage("Unable to create trip.");
                    }
                    break;

                case 2:
                    Activity newActivity = view.askActivityDetails();

                    if (planner.addActivity(
                        newActivity.getName(),
                        newActivity.getLocation(),
                        newActivity.getDate(),
                        newActivity.getTime(),
                        newActivity.getDescription()))
                    {
                        view.displayMessage("Activity added successfully!");
                    }
                    else
                    {
                        view.displayMessage(
                            "Unable to add activity. Check your trip and activity details.");
                    }
                    break;

                case 3:
                    String editName =
                        view.askRequiredActivityNameForEditing();
                    // Add the edit workflow here.
                    break;

                case 4:
                    String deleteName =
                        view.askRequiredActivityNameForDeletion();

                    if (planner.deleteActivity(deleteName))
                    {
                        view.displayMessage("Activity deleted successfully!");
                    }
                    else
                    {
                        view.displayMessage("Unable to delete activity.");
                    }
                    break;

                case 5:
                    if (planner.completeNextActivity())
                    {
                        view.displayMessage(
                            "Next activity completed successfully!");
                    }
                    else
                    {
                        view.displayMessage("No upcoming activity to complete.");
                    }
                    break;

                case 6:
                    view.displayActivity(planner.viewNextActivity());
                    break;

                case 7:
                    view.displayActivities(planner.viewUpcomingActivities());
                    break;

                case 8:
                    view.displayActivities(planner.viewPastActivities());
                    break;

                case 9:
                    view.displayMessage("Goodbye!");
                    running = false;
                    break;

                default:
                    view.displayMessage("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
