import exceptions.TaskNotFoundException;
import exceptions.WrongInputException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MyFaseBook {
    private static final Map<Integer, Task> actualTasks = new HashMap<>();
    private static final Map<Integer, Task> archivedTasks = new HashMap<>();

    public static void addTask(Scanner scanner) {
        try {
            scanner.nextLine();
            System.out.println("Введите название задачи:");
            String title = ValidateUtils.checkString(scanner.nextLine());
            System.out.println("Введите описание задачи:");
            String description = ValidateUtils.checkString(scanner.nextLine());
            System.out.println("Введите тип задачи: 0 - Рабочая, 1 - Личная:");
            TaskType taskType = TaskType.values()[scanner.nextInt()];
            System.out.println("Введите повторяемость задачи: 0 - однократная, 1 - Ежедневная, 2 - Еженедельная, 3 - Ежемесячная, 4 - Ежегодная");
            int appearsDate = scanner.nextInt();
            System.out.println("Введите дату: dd.MM.yyyy HH:mm");
            scanner.nextLine();
            createEvent(scanner, title, description, taskType, appearsDate);
            System.out.println("Для выхода нажмите Enter\n");
            scanner.nextLine();
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createEvent(Scanner scanner, String title, String description, TaskType taskType, int appearsDate) throws WrongInputException {
        try {
            LocalDateTime eventDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Task task;
            task = createTask(appearsDate, title, description, taskType, eventDate);
            System.out.println("Создана задача " + task);

        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат dd.MM.yyyy HH:mm и попробуйте еще раз");
            createEvent(scanner, title, description, taskType, appearsDate);
        }
    }

    public static void editTask(Scanner scanner) {
        try {
            System.out.println("Для редактирования задачи: введите id");
            printActualTasks();
            int id = scanner.nextInt();
            if (!actualTasks.containsKey(id)) {
                throw new TaskNotFoundException("Задача не найдена");
            }
            System.out.println("Редактирование: 0 - заголовок, 1- описание, 2 - тип, 3 - дата");
            int menuCase = scanner.nextInt();
            switch (menuCase) {
                case 0 -> {
                    scanner.nextLine();
                    System.out.println("Введите название задачи:");
                    String title = scanner.nextLine();
                    Task task = actualTasks.get(id);
                    task.setTitle(title);
                }
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("Введите описание задачи:");
                    String description = scanner.nextLine();
                    Task task = actualTasks.get(id);
                    task.setTitle(description);
                }
                case 2 -> {
                    scanner.nextLine();
                    System.out.println("Введите тип задачи:");
                    TaskType taskType = TaskType.valueOf(scanner.nextLine());
                    Task task = actualTasks.get(id);
                    task.setTaskType(taskType);
                }
                case 3 -> {
                    scanner.nextLine();
                    System.out.println("Введите дату задачи:");
                    LocalDateTime appearsDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                    Task task = actualTasks.get(id);
                    task.setFirstDate(appearsDate);
                }
            }
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTask(Scanner scanner) {
        System.out.println("Удалить текущую задачу\n");
        printActualTasks();
        try {
            System.out.println("Для удаления введите id задачи\n");
            int id = scanner.nextInt();
            if (actualTasks.containsKey(id)) {
                Task removedTask = actualTasks.get(id);
                removedTask.setArchived(true);
                archivedTasks.put(id, removedTask);
                System.out.println("Задача " + id + " удалена\n");
            } else {
                throw new TaskNotFoundException();
            }
        } catch (TaskNotFoundException e) {
            e.printStackTrace();
            System.out.println("Такой задачи не существует\n");
        }
    }


    private static List<Task> findTasksByDate(LocalDate date) {
        List<Task> tasks = new ArrayList<>();
        for (Task task : actualTasks.values()) {
            if (task.checkAppearsDate(date.atStartOfDay())) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    private static Task createTask(int appearsDate, String title, String description, TaskType
            taskType, LocalDateTime localDateTime) throws WrongInputException {
        return switch (appearsDate) {
            case 0 -> {
                OncelyTask oncelyTask = new OncelyTask(title, description, taskType, localDateTime);
                actualTasks.put(oncelyTask.getId(), oncelyTask);
                yield oncelyTask;
            }
            case 1 -> {
                DailyTask task = new DailyTask(title, description, taskType, localDateTime);
                actualTasks.put(task.getId(), task);
                yield task;
            }
            case 2 -> {
                WeeklyTask task = new WeeklyTask(title, description, taskType, localDateTime);
                actualTasks.put(task.getId(), task);
                yield task;
            }
            case 3 -> {
                MonthlyTask task = new MonthlyTask(title, description, taskType, localDateTime);
                actualTasks.put(task.getId(), task);
                yield task;
            }
            case 4 -> {
                YearlyTask task = new YearlyTask(title, description, taskType, localDateTime);
                actualTasks.put(task.getId(), task);
                yield task;
            }
            default -> null;
        };
    }

    public static void getTasksByDay(Scanner scanner) {
        System.out.println("Введите дату: dd.MM.yyyy");
        try {
            String date = scanner.next();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate requestedDate = LocalDate.parse(date, dateFormatter);
            List<Task> foundEvents = findTasksByDate(requestedDate);
            System.out.println("События на " + requestedDate + " : ");
            for (Task task : foundEvents) {
                System.out.println(task);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат даты dd.MM.yyyy и попробуйте еще раз.");
        }
        scanner.nextLine();
        System.out.println("Для выхода нажмите Enter\n");
    }

    public static void printActualTasks() {
        for (Task task : actualTasks.values()) {
            System.out.println(task);
        }
    }

    public static void printArchivedTasks() {
        for (Task task : archivedTasks.values()) {
            System.out.println(task);
        }
    }

    public static void getGroupedByDate() {
        Map<LocalDate, ArrayList<Task>> taskMap = new HashMap<>();
        for (Map.Entry<Integer, Task> entry : actualTasks.entrySet()) {
            Task task = entry.getValue();
            LocalDate localDate = task.getFirstDate().toLocalDate();
            if (taskMap.containsKey(localDate)) {
                ArrayList<Task> tasks = taskMap.get(localDate);
                tasks.add(task);
            } else {
                taskMap.put(localDate, new ArrayList<>(Collections.singletonList(task)));
            }
        }
        for (Map.Entry<LocalDate, ArrayList<Task>> taskEntry : taskMap.entrySet()) {
            System.out.println(taskEntry.getKey() + " : " + taskEntry.getValue());
        }
    }
}


