import exceptions.WrongInputException;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task {
    private String title;
    private String description;
    private TaskType taskType;
    private LocalDateTime firstDate;
    private static Integer counter = 1;
    private final Integer id;
    private boolean archived;

    public Task(String title, String description, TaskType taskType, LocalDateTime localDateTime) throws WrongInputException {
        this.title = ValidateUtils.checkString(title);
        this.description = ValidateUtils.checkString(description);
        this.taskType = taskType;
        this.firstDate = localDateTime;
        this.archived = false;
        id = counter++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getFirstDate() {
        return firstDate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public static Integer getCounter() {
        return counter;
    }

    public static void setCounter(Integer counter) {
        Task.counter = counter;
    }

    public Integer getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public abstract boolean checkAppearsDate(LocalDateTime localDateTime); // проверяет должна ли задача выполнятся на указанную в параметрах дату

    public void setFirstDate(LocalDateTime firstDate) {
        this.firstDate = firstDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash
                (title, description, taskType, firstDate, archived, id);
    }

    @Override
    public String toString() {
        return super.toString(); /// уточнить код
    }
}
