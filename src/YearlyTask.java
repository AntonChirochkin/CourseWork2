import exceptions.WrongInputException;

import java.time.LocalDateTime;

public class YearlyTask extends Task {
    public YearlyTask(String title, String description, TaskType taskType, LocalDateTime date) throws WrongInputException {
        super(title, description, taskType, date);
    }

    @Override
    public boolean checkAppearsDate(LocalDateTime requestedDate) {
        return (getFirstDate().getDayOfMonth() == requestedDate.getDayOfMonth() && getFirstDate().getMonth() == requestedDate.getMonth());
    }
}
