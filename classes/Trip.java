import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Queue upcomingActivities;
    private List<Activity> pastActivities;

    public Trip(String destination, LocalDate startDate, LocalDate endDate) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.upcomingActivities = new Queue();
        this.pastActivities = new ArrayList<>();
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setDestination(String destination) {
        if (destination != null) {
            this.destination = destination;
        }
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        }
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate != null) {
            this.endDate = endDate;
        }
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            upcomingActivities.enqueue(activity);
        }
    }

    public boolean removeActivity(String name) {
        return upcomingActivities.remove(name);
    }

    public boolean editActivity(String name, Activity updated) {
        if (!upcomingActivities.contains(name)) {
            return false;
        }
        boolean changed = false;
        Queue tempActivities = new Queue();
        while (!upcomingActivities.isEmpty()) {
            if (changed || !upcomingActivities.peek().getName().equals(name)) {
                tempActivities.enqueue(upcomingActivities.dequeue());
            } else {
                upcomingActivities.dequeue();
                tempActivities.enqueue(updated);
                changed = true;
            }
        }
        while (!tempActivities.isEmpty()) {
            upcomingActivities.enqueue(tempActivities.dequeue());
        }
        return changed;
    }

    //
    public Activity completeNextActivity() {
        Activity activity = upcomingActivities.dequeue();
        activity.setCompleted();
        pastActivities.add(activity);
        return activity;
    }

    public List<Activity> getUpcomingActivities() {
        return upcomingActivities.toList();
    }

    public Activity getNextActivity() {
        return upcomingActivities.peek();
    }

    public List<Activity> getPastActivity() {
        return pastActivities;
    }
}
