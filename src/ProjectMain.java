import java.util.*;

public class ProjectMain {
    static HashMap<String, HashSet<String>> prereqs = new HashMap<>();
    static HashMap<String, HashSet<String>> completed = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Course enrollment planner : ");
        printHelp();
        System.out.println("---------------------------------");

        while (true) {
            showAllCourses();

            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] p = line.split(" ");
            String cmd = p[0].toUpperCase();

            if (cmd.equals("EXIT")) break;

            switch (cmd){
                case "ADD_COURSE":if (p.length> 1){
                    addCourse(p[1]);
                }
                    break;
                case "ADD_PREREQ":
                    if(p.length> 2){
                        addPrereq(p[1], p[2]);

                    }
                    break;
                case "PREREQS" : if (p.length > 1){
                    showPrereqs(p[1]);
                }break;
                case "COMPLETE" :
                    if(p.length > 2){
                        completeCourse(p[1], p[2]);

                    }break;
                case "DONE" : if (p.length> 1){
                 showDone(p[1]);
                }break;
                case "CAN_TAKE" : if (p.length > 2){
                    checkCanTake(p[1], p[2]);

                }break;
                case "HELP":
                    printHelp();
                    break;
                case "BENCH": runBenchmark(); break;
                default:
                    System.out.println("Unknown command or missing arguments.");
                    break;
            }

        }
        System.out.println("Goodbye!");
    }


    public static void showAllCourses() {
        if (prereqs.isEmpty()) {
            System.out.println("No courses created yet ");
        } else {
            System.out.println("CURRENT COURSES: " + prereqs.keySet());
        }
    }

    public static void addCourse(String course) {
        prereqs.putIfAbsent(course, new HashSet<>());
        System.out.println("Added course: " + course);
    }

    public static void addPrereq(String course, String prereq) {
        if (course.equals(prereq)) {
            System.out.println("A course cannot be its own prerequisite");
            return;
        }
        prereqs.putIfAbsent(course, new HashSet<>());
        prereqs.putIfAbsent(prereq, new HashSet<>());
        prereqs.get(course).add(prereq);
        System.out.println("Added prereq: " + prereq + " -> " + course);
    }

    public static void showPrereqs(String course) {
        if (!prereqs.containsKey(course)) {
            System.out.println("Course not found");
        } else {
            System.out.println("Prereqs for " + course + ": " + prereqs.get(course));
        }
    }

    public static void completeCourse(String student, String course) {
        completed.computeIfAbsent(student, k -> new HashSet<>()).add(course);
        System.out.println(student + " completed " + course);
    }

    public static void showDone(String student) {
        if (!completed.containsKey(student)) {
            System.out.println("No record for student: " + student);
        } else {
            System.out.println(student + "'s completed courses: " + completed.get(student));
        }
    }

    public static void checkCanTake(String student, String course) {
        if (!prereqs.containsKey(course)) {
            System.out.println("YES (Course doesn't exist, assuming no prereqs)");
            return;
        }

        HashSet<String> courseDeps = prereqs.get(course);
        if (courseDeps.isEmpty()) {
            System.out.println("YES");
            return;
        }

        HashSet<String> studentHas = completed.getOrDefault(student, new HashSet<>());
        if (studentHas.containsAll(courseDeps)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }


    public static void printHelp() {
        System.out.println("Commands: ADD_COURSE <C>, ADD_PREREQ <C> <P>, PREREQS <C>, COMPLETE <S> <C>, DONE <S>, CAN_TAKE <S> <C>, BENCH,  HELP, EXIT");
    }
    public static void runBenchmark() {
        int n = 100000;
        HashSet<String> set = new HashSet<>();
        ArrayList<String> list = new ArrayList<>();

        // Наполнение (не замеряем)
        for (int i = 0; i < n; i++) {
            set.add("C" + i);
            list.add("C" + i);
        }

        String target = "C" + (n - 1); // Худший случай (последний элемент)

        // Замер HashSet
        long t1 = System.nanoTime();
        set.contains(target);
        long t2 = System.nanoTime();

        // Замер ArrayList
        long t3 = System.nanoTime();
        list.contains(target);
        long t4 = System.nanoTime();

        // Вывод в наносекундах (ns) - так нагляднее разница
        System.out.println("N = " + n);
        System.out.println("HashSet Search: " + (t2 - t1) + " ns (O(1))");
        System.out.println("ArrayList Search: " + (t4 - t3) + " ns (O(n))");
    }
}
