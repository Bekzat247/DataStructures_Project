import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ProjectMain {
    private static HashSet<String> myCourses = new HashSet<>();

    private static HashMap<String, String> rules = new HashMap<>();

    public static void main(String[] args) {
        rules.put("MATH2", "MATH1");
        rules.put("JAVA2", "JAVA1");
        rules.put("ALGORITHMS", "JAVA2");

        Scanner input = new Scanner(System.in);

        while(true){
            System.out.println();
            System.out.println("Your courses: [");
            for (String i : myCourses){
                System.out.println(" ".repeat(5) + i);
            }
            System.out.println("]");
            System.out.println("List of commands:");
            System.out.println("ADD <course>    - Add finished course");
            System.out.println("CHECK <course>  - Can I take this course?");
            System.out.println("BENCH           - Compare HashSet vs ArrayList");
            System.out.println("EXIT            - Close program");
            System.out.print("Action: ");

            String action = input.nextLine();
            while(action.isEmpty()){
                System.out.print("Input is empty. Write again : ");
                String again = input.nextLine();
                action = again;
            }

            String[] parts = action.split(" ", 2);

            switch (parts[0].toUpperCase()){
                case "ADD":
                    if (parts.length < 2) System.out.println("Error: Name required.");
                    else addCourse(parts[1].toUpperCase());
                    break;
                case "CHECK":
                    if (parts.length < 2) System.out.println("Error: Name required.");
                    else checkCourse(parts[1].toUpperCase());
                    break;
                case "BENCH":
                    runBenchmark();
                    break;
                case "EXIT":
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    private static void addCourse(String n) {
        myCourses.add(n);
        System.out.println("Added: " + n);
    }

    private static void checkCourse(String n) {
        String need = rules.get(n);

        if (need == null) {
            System.out.println("Yes! No prerequisites for " + n);
        } else if (myCourses.contains(need)) {
            System.out.println("Yes! You can take " + n + " because you finished " + need);
        } else {
            System.out.println("NO! You must finish " + need + " first.");
        }
    }

    private static void runBenchmark() {
        int n = 100000;
        System.out.println("Benchmarking Search (contains) for N = " + n);

        HashSet<String> set = new HashSet<>();
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            set.add("C" + i);
            list.add("C" + i);
        }

        String target = "C" + (n - 1);

        long startSet = System.nanoTime();
        set.contains(target);
        long endSet = System.nanoTime();

        long startList = System.nanoTime();
        list.contains(target);
        long endList = System.nanoTime();

        System.out.println("HashSet Search (O(1)): " + (endSet - startSet) + " ns");
        System.out.println("ArrayList Search (O(n)): " + (endList - startList) + " ns");
    }
}
