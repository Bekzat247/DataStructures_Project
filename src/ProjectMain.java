import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class ProjectMain {
    private static ArrayDeque<String> queue = new ArrayDeque<>();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while(true){
            System.out.println();
            System.out.println();
            System.out.println("Current line :[" );
            for (String student : queue) {
                System.out.println(" ".repeat(5) + student);
            }
            System.out.println("]");

            System.out.println("List of commands:");
            System.out.println("ENQUEUE <name> - Add student to the end");
            System.out.println("SERVE          - Remove student from the front");
            System.out.println("LEAVE <name>   - Remove specific student");
            System.out.println("BENCH          - Run empirical timing test");
            System.out.println("EXIT           - Close program");
            System.out.println();
            System.out.print("Write your action : ");


            String action = input.nextLine();
            while(action.isEmpty()){
                System.out.print("Input is empty. Write again : ");
                String again = input.nextLine();
                action = again;
            }
            String[] parts = action.split(" ", 2);
            switch (parts[0].toUpperCase()){
                case "ENQUEUE":
                    if (parts.length < 2) System.out.println("Error: Name required.");
                    else addToQueue(parts[1]);
                    break;
                case "SERVE":
                    serve();
                    break;
                case "LEAVE":
                    if (parts.length < 2) System.out.println("Error: Name required.");
                    else leave(parts[1]);
                    break;

                case "BENCH":
                    runBenchmark();
                    break;
                case "EXIT":
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Unknown command. Type HELP for list.");
            }
            System.out.println();
            System.out.println();

        }

    }
    private static void addToQueue(String n ){
        System.out.println();
        queue.addLast(n);
        System.out.println(n + " joined the line.");
        System.out.println();
    }
    private static void serve() {
        System.out.println();
        if (queue.isEmpty()) {
            System.out.println("The line is empty.");
        } else {
            String student = queue.getFirst();
            queue.removeFirst();
            System.out.println("Served: " + student);
        }
        System.out.println();
    }
    private static void leave(String n){
        if (!queue.contains(n)){
            System.out.println("There is no " + n + "in the list");
        }else {
            queue.remove(n);
        }
    }
    private static void runBenchmark() {
        System.out.println();
        System.out.println();
        int n = 100000;
        System.out.println("Running benchmark for N = " + n + " operations (remove from front)...");

        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < n; i++) ad.add(i);
        long startAD = System.nanoTime();
        while (!ad.isEmpty()) ad.removeFirst();
        long endAD = System.nanoTime();

        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 0; i < n; i++) al.add(i);
        long startAL = System.nanoTime();
        while (!al.isEmpty()) al.remove(0);
        long endAL = System.nanoTime();

        System.out.println("Average ArrayDeque time: " + (endAD - startAD) / 1_000_000.0 + " ms per op");
        System.out.println("Average ArrayList time: " + (endAL - startAL) / 1_000_000.0 + " ms per op");
        System.out.println("Interpretation: ArrayDeque is O(1), ArrayList is O(n).");
        System.out.println();
        System.out.println();
    }
}