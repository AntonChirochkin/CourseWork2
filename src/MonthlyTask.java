import exceptions.WrongInputException;

import java.time.LocalDateTime;

public class MonthlyTask extends Task {
    public MonthlyTask(String title, String description, TaskType taskType, LocalDateTime date) throws WrongInputException {
        super(title, description, taskType, date);
    }

    @Override
    public boolean checkAppearsDate(LocalDateTime requestedDate) {
        return getFirstDate().getDayOfMonth() == (requestedDate.getDayOfMonth());
    }
}
