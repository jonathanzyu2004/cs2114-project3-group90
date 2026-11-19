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

    public boolean addActivity(Activity activity) {
        if (activity == null || !activity.hasValidTimeRange()) {
            return false;
        }

        if (hasScheduleConflict(activity)) {
            return false;
        }

        upcomingActivities.enqueue(activity);
        return true;
    }

    public boolean removeActivity(String name) {
        return upcomingActivities.remove(name);
    }

    public boolean editActivity(String name, Activity updated) {
        if (name == null || updated == null
            || !updated.hasValidTimeRange()) {
            return false;
        }

        if (!upcomingActivities.contains(name)) {
            return false;
        }

        boolean conflict = false;
        Queue tempActivities = new Queue();

        while (!upcomingActivities.isEmpty()) {
            Activity current = upcomingActivities.dequeue();

            if (!current.getName().equals(name)
                && current.getDate().equals(updated.getDate())) {

                boolean overlaps =
                    updated.getTime().isBefore(current.getEndTime())
                    && updated.getEndTime().isAfter(current.getTime());

                if (overlaps) {
                    conflict = true;
                }
            }

            tempActivities.enqueue(current);
        }

        while (!tempActivities.isEmpty()) {
            upcomingActivities.enqueue(tempActivities.dequeue());
        }

        if (conflict) {
            return false;
        }

        Queue replacementQueue = new Queue();
        boolean replaced = false;

        while (!upcomingActivities.isEmpty()) {
            Activity current = upcomingActivities.dequeue();

            if (!replaced && current.getName().equals(name)) {
                replacementQueue.enqueue(updated);
                replaced = true;
            } else {
                replacementQueue.enqueue(current);
            }
        }

        while (!replacementQueue.isEmpty()) {
            upcomingActivities.enqueue(replacementQueue.dequeue());
        }

        return replaced;
    }
    public Activity completeNextActivity() {
        Activity activity = upcomingActivities.dequeue();

        if (activity == null) {
            return null;
        }

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
    
    public boolean hasScheduleConflict(Activity activity) {
        if (activity == null || !activity.hasValidTimeRange()) {
            return true;
        }

        for (Activity existing : upcomingActivities.toList()) {
            if (existing.getDate().equals(activity.getDate())) {

                boolean overlaps =
                    activity.getTime().isBefore(existing.getEndTime())
                    && activity.getEndTime().isAfter(existing.getTime());

                if (overlaps) {
                    return true;
                }
            }
        }

        return false;
    }
    
}
