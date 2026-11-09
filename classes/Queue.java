import java.util.List;

public class Queue {
    private Node front;
    private Node rear;
    private int size;

    public Queue() {
    }

    public void enqueue(Activity activity) {
    }

    public Activity dequeue() {
        return null;
    }

    public Activity peek() {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }

    public boolean remove(String name) {
        return false;
    }

    public boolean contains(String name) {
        return false;
    }

    public List<Activity> toList() {
        return null;
    }

    private class Node {
        private Activity activity;
        private Node next;
    }
}
