import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        toDoList.loadTasksFromFile();
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== 📝 TO-DO LIST MENU =====");
            System.out.println("1. Add new task");
            System.out.println("2. View tasks");
            System.out.println("3. Mark task as completed");
            System.out.println("4. Edit a task");
            System.out.println("5. Delete task");
            System.out.println("6. View tasks by status");
            System.out.println("7. Search tasks by keyword");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = in.nextInt();
            in.nextLine(); // تنظيف البافر

            switch (choice) {
                case 1:
                    System.out.print("Enter task title: ");
                    String title = in.nextLine();
                    System.out.print("Enter task description: ");
                    String description = in.nextLine();
                    toDoList.addTask(new Task(title, description));
                    toDoList.saveTasksToFile();
                    System.out.println("\u001B[32m✅ Task added successfully!\u001B[0m");
                    break;

                case 2:
                    toDoList.viewTasks();
                    break;

                case 3:
                    System.out.print("Enter task number to mark as completed: ");
                    int completeIndex = in.nextInt();
                    in.nextLine();
                    toDoList.markTaskAsCompleted(completeIndex - 1);
                    break;

                case 4:
                    System.out.print("Enter task number to edit: ");
                    int editIndex = in.nextInt();
                    in.nextLine();
                    toDoList.editTask(editIndex - 1, in);
                    break;

                case 5:
                    System.out.print("Enter task number to delete: ");
                    int delIndex = in.nextInt();
                    in.nextLine();
                    toDoList.deleteTask(delIndex - 1, in);
                    break;

                case 6:
                    System.out.print("Enter status to filter (PENDING/COMPLETED): ");
                    String stat = in.nextLine().trim().toUpperCase();
                    if (stat.equals("PENDING") || stat.equals("COMPLETED")) {
                        toDoList.viewTasksByStatus(TaskStatus.valueOf(stat));
                    } else {
                        System.out.println("\u001B[31m❌ Invalid status!\u001B[0m");
                    }
                    break;

                case 7:
                    System.out.print("Enter keyword to search: ");
                    String keyword = in.nextLine().trim();
                    toDoList.searchTasks(keyword);
                    break;

                case 8:
                    System.out.println("💾 Saving and exiting...");
                    toDoList.saveTasksToFile();
                    System.out.println("👋 Goodbye!");
                    return;

                default:
                    System.out.println("\u001B[31m❌ Invalid option, try again!\u001B[0m");
            }
        }
    }
}