import java.util.*;

class Student {
    String id;
    String name;
    double marks;
    String rank;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = assignRank(marks);
    }

    // Phương thức này giữ nguyên là private và chỉ được gọi bên trong lớp Student
    private String assignRank(double marks) {
        if (marks < 5.0) return "Fail";
        if (marks < 6.5) return "Medium";
        if (marks < 7.5) return "Good";
        if (marks < 9.0) return "Very Good";
        return "Excellent";
    }

    // Phương thức cập nhật thông tin sinh viên, gọi lại assignRank
    public void updateStudent(String name, double marks) {
        this.name = name;
        this.marks = marks;
        this.rank = assignRank(marks); // Tự động cập nhật rank
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Marks: %.2f, Rank: %s", id, name, marks, rank);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Student> studentStack = new Stack<>();
        Queue<Student> studentQueue = new LinkedList<>();

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View All Students");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Sort Students by Marks");
            System.out.println("7. Undo Last Action (Stack)");
            System.out.println("8. Redo Last Action (Queue)");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Student Marks: ");
                    double marks = scanner.nextDouble();
                    Student student = new Student(id, name, marks);
                    studentStack.push(student);
                    studentQueue.add(student);
                    System.out.println("Student added successfully.");
                }
                case 2 -> {
                    System.out.print("Enter Student ID to edit: ");
                    String id = scanner.nextLine();
                    boolean found = false;
                    for (Student student : studentStack) {
                        if (student.id.equals(id)) {
                            System.out.print("Enter new name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter new marks: ");
                            double marks = scanner.nextDouble();
                            student.updateStudent(name, marks);
                            found = true;
                            System.out.println("Student updated successfully.");
                            break;
                        }
                    }
                    if (!found) System.out.println("Student not found.");
                }
                case 3 -> {
                    System.out.print("Enter Student ID to delete: ");
                    String id = scanner.nextLine();
                    boolean found = false;
                    Iterator<Student> iterator = studentStack.iterator();
                    while (iterator.hasNext()) {
                        Student student = iterator.next();
                        if (student.id.equals(id)) {
                            iterator.remove();
                            studentQueue.remove(student);
                            found = true;
                            System.out.println("Student deleted successfully.");
                            break;
                        }
                    }
                    if (!found) System.out.println("Student not found.");
                }
                case 4 -> {
                    System.out.println("=== Student List ===");
                    for (Student student : studentStack) {
                        System.out.println(student);
                    }
                }
                case 5 -> {
                    System.out.print("Enter Student ID to search: ");
                    String id = scanner.nextLine();
                    boolean found = false;
                    for (Student student : studentStack) {
                        if (student.id.equals(id)) {
                            System.out.println(student);
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Student not found.");
                }
                case 6 -> {
                    System.out.println("Sorting students by marks...");
                    studentStack.sort(Comparator.comparingDouble(s -> s.marks));
                    System.out.println("Students sorted successfully.");
                }
                case 7 -> {
                    if (!studentStack.isEmpty()) {
                        Student undone = studentStack.pop();
                        System.out.println("Undo last action: Removed " + undone);
                    } else {
                        System.out.println("No actions to undo.");
                    }
                }
                case 8 -> {
                    if (!studentQueue.isEmpty()) {
                        Student redone = studentQueue.poll();
                        studentStack.push(redone);
                        System.out.println("Redo last action: Restored " + redone);
                    } else {
                        System.out.println("No actions to redo.");
                    }
                }
                case 9 -> {
                    System.out.println("Exiting system...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
