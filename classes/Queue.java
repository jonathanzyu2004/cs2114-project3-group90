import java.util.ArrayList;
import java.util.List;

/**
 * Model class implementing a Singly Linked List Queue (FIFO) to store Activity
 * objects
 */
public class Queue {

    /**
     * Inner class representing a node in the singly linked list
     */
    private static class Node {
        private Activity data;
        private Node next;

        public Node(Activity data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    /**
     * Constructs an empty Queue
     */
    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Adds an activity to the end of the queue
     * 
     * @param activity
     *                 The activity to add
     */
    public void enqueue(Activity activity) {
        if (activity == null) {
            return;
        }
        Node newNode = new Node(activity);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the activity at the front of the queue
     * 
     * @return the first Activity
     */
    public Activity dequeue() {
        if (isEmpty()) {
            return null;
        }
        Activity data = front.data;
        front = front.next;
        size--;

        if (front == null) {
            rear = null;
        }
        return data;
    }

    /**
     * Returns the activity at the front of the queue without removing it
     * 
     * @return The first Activity
     */
    public Activity peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    /**
     * Checks if the queue is empty
     * 
     * @return true if size is 0, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of activities in the queue
     * 
     * @return int size of the queue
     */
    public int size() {
        return size;
    }

    /**
     * Removes an activity by name from the queue
     * 
     * @param name
     *             Name of the activity to remove
     * @return true if activity was found and removed, false otherwise
     */
    public boolean remove(String name) {
        if (isEmpty() || name == null) {
            return false;
        }

        if (front.data.getName().equals(name)) {
            dequeue();
            return true;
        }

        Node current = front;
        while (current.next != null) {
            if (current.next.data.getName().equals(name)) {
                if (current.next == rear) {
                    rear = current;
                }
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Checks if an activity with the matching name exists in the queue
     * 
     * @param name
     *             Name to check
     * @return true if present
     */
    public boolean contains(String name) {
        if (name == null) {
            return false;
        }
        Node current = front;
        while (current != null) {
            if (current.data.getName().equals(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns a List version of upcoming activities in queue order
     * 
     * @return List of Activity objects
     */
    public List<Activity> toList() {
        List<Activity> list = new ArrayList<>();
        Node current = front;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
}
