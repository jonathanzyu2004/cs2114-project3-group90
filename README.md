## Reflection

### What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?
A couple of the methods in trip and triptest were spelled incorrectly which were corrected.
Trip stores destination, start date, and end date.
Trip owns an upcomingActivities queue and a List<Activity> pastActivities.

### Did the model really stand alone? Was there anything you had to move across the model/view line to make it run, or anything in the model that still knows about the view?

### What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?
Some of the unknowns had to do with adding more complexity with the model for example allowing mutiple different planners. For now we have attempted to keep the model simple for the MVP. We could still in the actual model include additions that may improve the model.

### What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.
testCompleteNextActivityEmptyQueue() does not work inside the trip class. Also aren't activities autumotaically marked as completed.

## Next Steps

### 1. Remaining MVP Work

Our current implementation supports trip creation, activity management, completion tracking, and viewing upcoming and past activities.

We will focus on the following remaining work before completing the MVP:

1. **Test trip management:** Verify that users can create trips with valid destinations and start/end dates, and that invalid dates or destinations are rejected.
2. **Test activity management:** Verify that users can successfully add, edit, and delete activities, including handling invalid inputs and duplicate activity names.
3. **Verify completion tracking:** Ensure that completed activities are moved from the upcoming activities queue into the past activities list.
4. **Test itinerary views:** Verify that users can view the next activity, upcoming activities, and completed activities correctly.

For chronological ordering, we will continue with our current design assumption that users enter activities in chronological order rather than implementing automatic sorting.

### 2. New Non-Trivial Feature: Activity Duration and Schedule-Conflict Detection

For Deliverable 4, we plan to extend activities to include an end time in addition to their existing start time. The application will use this information to calculate activity duration and detect scheduling conflicts.

Users will be able to:
- Enter an activity's start and end times.
- View the duration of each activity.
- Receive a warning or rejection when an activity overlaps with another activity on the same day.
- Schedule activities back-to-back without triggering a false conflict.

We predict the following classes will need changes:

- **Activity (Model):** Add an end-time field and corresponding getters and setters.
- **Trip (Model):** Implement logic to calculate activity durations and detect overlapping activities.
- **TripPlanner (Controller):** Validate activity time ranges and coordinate conflict checking when activities are added or edited.
- **TripView (View):** Allow users to enter end times and display activity durations and scheduling conflicts.
- **TripPlannerApp:** Update the add and edit workflows to collect end-time information.

We predict that **Queue.java will not need to change**, because the existing queue operations can still manage the activities. The new scheduling logic will belong to the Trip model.

### 3. Stretch Goals

We plan to attempt the following two stretch goals:

**1. Activities with Start and End Times**

As part of our new feature, we will allow activities to have both start and end times. This will provide users with more detailed schedules and allow the application to identify overlapping activities.

**2. Automatically Check Whether Activities Are Finished**

We plan to reduce the need for users to manually mark activities as completed. The application will check the current time against each activity's scheduled end time and automatically move finished activities into the past activities list.

To support this functionality, we will need to:
- Check whether an activity's scheduled end time has passed.
- Update its completion status.
- Move completed activities into the past activities list.
- Ensure upcoming activities remain available to the user.

We will not pursue the following stretch goals during Deliverable 4:
- Saving and restoring trips and activities.
- Merging trips or itineraries.
- Separating queues by day.
- Supporting multiple queues by activity type.
- Developing a graphical user interface.

### 4. New Tests

We will add tests to verify the new functionality and prevent regressions.

**Activity duration and conflict-detection tests:**
- Creating an activity with valid start and end times succeeds.
- An activity with a null end time is rejected.
- An activity with an end time earlier than or equal to its start time is rejected.
- Adding an overlapping activity is rejected.
- Adding back-to-back activities is allowed.
- Activities on different dates do not create false conflicts.
- Editing an activity into a conflicting time slot is rejected.
- Editing an activity without creating a conflict succeeds.

**Automatic completion tests:**
- An activity whose end time has passed is automatically marked completed.
- An activity whose end time is in the future remains upcoming.
- Completed activities are moved into the past activities list.
- Multiple finished activities are handled correctly.
- Activities are not completed more than once.
- Automatic completion works correctly when the application checks the itinerary again.

We will also test invalid activity information, including missing names, locations, dates, and times.

### 5. Team Responsibilities

- **[Team Member 1]:** Implement activity duration and end-time information in the Activity model.
- **[Leo]:** Implement schedule-conflict detection and automatic completion logic in the Trip model.
- **[Angelina]:** Update the TripPlanner controller and TripView to support the new functionality and validate user inputs.
- **[Jonathan]:** Update TripPlannerApp, write and execute tests, and help integrate the changes.

All team members will participate in integration testing and bug fixing.

At the end of Deliverable 4, we will compare our predicted class changes with the actual classes modified to evaluate our design decisions and separation of responsibilities.

## Attribution table

Activity
Queue
Trip - Leo Chenoweth wrote w/o Ai
TripPlanner
TripPlannerApp
TripPlannerTest
TripTest - Leo Chenoweth wrote w/o Ai
TripView - Angelina Gust wrote w/ Ai assistance
TripViewTest - Angelina Gust wrote w/ Ai assistance
