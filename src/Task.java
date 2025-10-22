enum TaskStatus {
    PENDING, COMPLETED;
}

public class Task {
    private String title;
    private String description;
    private TaskStatus status;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void markAsCompleted() {
        this.status = TaskStatus.COMPLETED;
    }

    @Override
    public String toString() {
        return "[" + status + "] " + title + " - " + description;
    }
}