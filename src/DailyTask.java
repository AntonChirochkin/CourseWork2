import exceptions.WrongInputException;

import java.time.LocalDateTime;

public class DailyTask extends Task {
    public DailyTask(String title, String description, TaskType taskType, LocalDateTime date) throws WrongInputException {
        super(title, description, taskType, date);
    }
    @Override
    public boolean checkAppearsDate(LocalDateTime requestedDate) {
        return true;
    }
}
