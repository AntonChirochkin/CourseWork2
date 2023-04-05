import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            slyapa:
            while (true) {
                System.out.println("Выберете пункт меню:");
                printMeny();
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            MyFaseBook.addTask(scanner);
                            break;
                        case 2:
                            MyFaseBook.editTask(scanner);
                            break;
                        case 3:
                            MyFaseBook.deleteTask(scanner);
                            break;
                        case 4:
                            MyFaseBook.getTasksByDay(scanner);
                            break;
                        case 5:
                            MyFaseBook.printArchivedTasks();
                            break;
                        case 6:
                            MyFaseBook.getGroupedByDate();
                            break;
                        case 0:
                            break slyapa;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню!");
                }
            }
        }
    }

    private static void printMeny() {
        System.out.println("""
                1. Добавить задачу
                2. Редактировать задачу
                3. Удалить задачу
                4. Получить задачи на указанный день
                5. Получить архивные задачи
                6. Получить сгруппированные по датам задачи
                0. Выход"""
        );
    }
}