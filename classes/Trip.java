import java.time.LocalDate;
import java.util.List;

public class Trip {
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Queue upcomingActivities;
    private List<Activity> pastActivities;

    public Trip(String destination, LocalDate startDate, LocalDate endDate) {
    }

    public String getDestination() {
        return null;
    }

    public LocalDate getStartDate() {
        return null;
    }

    public LocalDate getEndDate() {
        return null;
    }

    public void setDestination(String destination) {
    }

    public void setStartDate(LocalDate startDate) {
    }

    public void setEndDate(LocalDate endDate) {
    }

    public void addActivity(Activity activity) {
    }

    public boolean removeActivity(String name) {
        return false;
    }

    public boolean editActivity(String name, Activity updated) {
        return false;
    }

    public Activity completeNextAcitivity() {
        return null;
    }

    public List<Activity> getUpcomingActivities() {
        return null;
    }

    public Activity getNextActivity() {
        return null;
    }

    public List<Activity> getPastActivity() {
        return null;
    }
}
