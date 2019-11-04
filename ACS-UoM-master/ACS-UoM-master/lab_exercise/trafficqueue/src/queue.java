import java.util.*;

/**
 * Created by Martin on 22/09/15.
 */
public class queue<vehicle> implements Iterable<vehicle> {

//    public vehicle vehicle = new vehicle("");
    private LinkedList<vehicle> elements = new LinkedList<vehicle>();

    public void enqueue(vehicle element) {
        elements.add(element);
    }
    public vehicle dequeue() {
        return elements.removeFirst();
    }
    public vehicle peek() {
        return elements.getFirst();
    }
    public void clear() {
        elements.clear();
    }
    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
    @Override
    public Iterator<vehicle> iterator() {
        return elements.iterator();
    }
}
