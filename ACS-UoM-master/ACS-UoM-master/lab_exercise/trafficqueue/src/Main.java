public class Main {

    public static void main(String[] args) {
        queue<vehicle> queue = new queue<vehicle>();
        vehicle v1 = new vehicle("blue");
        vehicle v2 = new vehicle("red");
        vehicle v3 = new vehicle("Ford");
        vehicle v4 = new vehicle("Benz");
        queue.enqueue(v1);
        queue.enqueue(v2);
        queue.enqueue(v3);
        queue.enqueue(v4);
        //Testing
        System.out.println(queue.size());
        loopOverAllElementsOf(queue);
    }
    private static <vehicle> void loopOverAllElementsOf(queue<vehicle> queue) {
        for (vehicle element : queue) {
            System.out.println(element.toString() + " ");
        }
    }
}
