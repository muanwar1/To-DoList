import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoList {
    private List<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\u001B[33m🟡 No tasks added yet!\u001B[0m"); // أصفر
            return;
        }
        System.out.println("\n📋 \u001B[34mYour Tasks:\u001B[0m"); // أزرق
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            String color = t.getStatus() == TaskStatus.COMPLETED ? "\u001B[32m" : "\u001B[33m"; // أخضر/أصفر
            System.out.println(color + (i + 1) + ". " + t + "\u001B[0m");
        }
    }

    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (task.getStatus() == TaskStatus.COMPLETED) {
                System.out.println("\u001B[33m⚠️ Task is already completed!\u001B[0m");
            } else {
                task.markAsCompleted();
                saveTasksToFile();
                System.out.println("\u001B[32m✅ Task \"" + task.getTitle() + "\" marked as completed!\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31m❌ Invalid task number!\u001B[0m");
        }
    }

    public void editTask(int index, Scanner in) {
        if (index < 0 || index >= tasks.size()) {
            System.out.println("\u001B[31m❌ Invalid task number!\u001B[0m");
            return;
        }
        Task task = tasks.get(index);
        System.out.println("✏️ Editing Task " + (index + 1) + ": " + task);

        System.out.print("Enter new title (leave empty to keep current): ");
        String newTitle = in.nextLine().trim();
        System.out.print("Enter new description (leave empty to keep current): ");
        String newDesc = in.nextLine().trim();

        boolean changed = false;
        if (!newTitle.isEmpty()) {
            task.setTitle(newTitle);
            changed = true;
        }
        if (!newDesc.isEmpty()) {
            task.setDescription(newDesc);
            changed = true;
        }

        if (changed) {
            saveTasksToFile();
            System.out.println("\u001B[32m✅ Task updated: " + task + "\u001B[0m");
        } else {
            System.out.println("ℹ️ No changes made.");
        }
    }

    public void deleteTask(int index, Scanner in) {
        if (index < 0 || index >= tasks.size()) {
            System.out.println("\u001B[31m❌ Invalid task number!\u001B[0m");
            return;
        }
        Task task = tasks.get(index);
        System.out.print("Are you sure you want to delete \"" + task.getTitle() + "\"? (y/n): ");
        String confirm = in.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            tasks.remove(index);
            saveTasksToFile();
            System.out.println("\u001B[31m🗑️ Task deleted successfully!\u001B[0m");
        } else {
            System.out.println("❎ Deletion canceled.");
        }
    }

    public void viewTasksByStatus(TaskStatus status) {
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getStatus() == status) {
                String color = status == TaskStatus.COMPLETED ? "\u001B[32m" : "\u001B[33m";
                System.out.println(color + (i + 1) + ". " + t + "\u001B[0m");
                found = true;
            }
        }
        if (!found) {
            String color = status == TaskStatus.COMPLETED ? "\u001B[32m" : "\u001B[33m";
            System.out.println(color + "⚠️ No tasks with status: " + status + "\u001B[0m");
        }
    }

    public void searchTasks(String keyword) {
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                String color = t.getStatus() == TaskStatus.COMPLETED ? "\u001B[32m" : "\u001B[33m";
                System.out.println(color + (i + 1) + ". " + t + "\u001B[0m");
                found = true;
            }
        }
        if (!found) {
            System.out.println("\u001B[31m⚠️ No tasks matching keyword: " + keyword + "\u001B[0m");
        }
    }

    public void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.getTitle() + ";" + task.getDescription() + ";" + task.getStatus());
            }
        } catch (IOException e) {
            System.out.println("\u001B[31m⚠️ Error saving tasks: " + e.getMessage() + "\u001B[0m");
        }
    }

    public void loadTasksFromFile() {
        tasks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length == 3) {
                    Task task = new Task(parts[0], parts[1]);
                    if (parts[2].equals("COMPLETED")) task.markAsCompleted();
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
        }
    }

    public int getTasksCount() {
        return tasks.size();
    }
}