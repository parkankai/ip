import java.util.Scanner;
import java.util.ArrayList;

/**
 * Entry point for the doe chatbot application.
 */
public class doe {
    /** Prints the Todo items with their current completion status. */
    private static void printTodoList(ArrayList<Task> todoItems) {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < todoItems.size(); i++) {
            Task task = todoItems.get(i);
            System.out.println((i + 1) + ". [" + task.getStatusIcon() + "] " + task.getDescription());
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads a task number and returns its zero-based index, or -1 when it is invalid.
     */
    private static int getTaskIndex(Scanner scanner, int numberOfTasks) {
        if (numberOfTasks == 0) {
            System.out.println("There are no tasks to mark yet. Add a task first.");
            return -1;
        }
        System.out.println("Enter the task number:");
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine().trim());
            if (taskNumber >= 1 && taskNumber <= numberOfTasks) {
                return taskNumber - 1;
            }
        } catch (NumberFormatException exception) {
            // The invalid-input message below explains how to correct the entry.
        }
        System.out.println("That task number is incorrect. Please enter a number from 1 to " + numberOfTasks + ".");
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> TODO = new ArrayList<>();
        String banner = "____________________________________________________________\n"
                + "     _          \n"
                + "  __| | ___  ___\n"
                + " / _` |/ _ \\ / _ \\\n"
                + "| (_| | (_) |  __/\n"
                + " \\__,_|\\___/ \\___|\n\n"
                + "hello! i'm doe :).\n"
                + "what can i do for you?\n"
                + "____________________________________________________________\n"
                + "1. neigh\n"
                + "2. meow\n"
                + "3. list\n"
                + "4. todo\n"
                + "____________________________________________________________\n"
                + "type \"bye\" to exit\n"
                + "____________________________________________________________\n";

        String neigh = "____________________________________________________________\n"
                + "EURHGGGHHHHH!\n"
                + "____________________________________________________________\n";

        String meow = "____________________________________________________________\n"
                + "meow!\n"
                + "____________________________________________________________\n";

        String list = "____________________________________________________________\n"
                + "roles and responsiblities\n"
                + "1. survive nus cs\n"
                + "2. get a few internships\n"
                + "3. work at mcdonalds\n"
                + "4. retire as a manager(hopefully)\n"
                + "____________________________________________________________\n";

        String todo = "____________________________________________________________\n"
                + "modify todo list\n"
                + "1. add\n"
                + "2. remove\n"
                + "3. view\n"
                + "4. mark\n"
                + "5. unmark\n"
                + "____________________________________________________________\n";

        String bye = "____________________________________________________________\n"
                + "bye. Hope to see you again soon!\n"
                + "____________________________________________________________\n";

        System.out.println(banner);
        while(true){
            String input = scanner.nextLine().toLowerCase();
            switch (input) {
                case "neigh":
                    System.out.println(neigh);
                    break;
                case "meow":
                    System.out.println(meow);
                    break;
                case "list":
                    System.out.println(list);
                    break;
                case "todo":
                    todoMenu:
                    while (true) {
                        System.out.println(todo);
                        String todo_input = scanner.nextLine().toLowerCase();
                        switch (todo_input) {
                        case "add":
                            System.out.println("____________________________________________________________\n"
                                    + "what you want to add?\n"
                                    + "____________________________________________________________\n");
                            String to_add = scanner.nextLine();
                            TODO.add(new Task(to_add));
                            System.out.println(banner);
                            break todoMenu;
                        case "remove":
                            System.out.println("____________________________________________________________\n"
                                    + "what you want to remove?\n"
                                    + "____________________________________________________________\n");
                            String to_remove = scanner.nextLine();
                            int removeIndex = -1;
                            for (int i = 0; i < TODO.size(); i++) {
                                if (TODO.get(i).getDescription().equals(to_remove)) {
                                    removeIndex = i;
                                    break;
                                }
                            }
                            if (removeIndex >= 0) {
                                TODO.remove(removeIndex);
                            }
                            System.out.println(banner);
                            break todoMenu;
                        case "view":
                            printTodoList(TODO);
                            System.out.println(banner);
                            break todoMenu;
                        case "mark":
                            System.out.println("____________________________________________________________");
                            System.out.println("To mark a task as done, enter its number from the list.");
                            System.out.println("____________________________________________________________");
                            printTodoList(TODO);
                            int markIndex = getTaskIndex(scanner, TODO.size());
                            if (markIndex >= 0) {
                                TODO.get(markIndex).markAsDone();
                                System.out.println("____________________________________________________________");
                                System.out.println("Nice! I've marked this task as done:");
                                System.out.println("[X] " + TODO.get(markIndex).getDescription());
                                System.out.println("____________________________________________________________");
                                printTodoList(TODO);
                                System.out.println(banner);
                                break todoMenu;
                            }
                            break;
                        case "unmark":
                            System.out.println("____________________________________________________________");
                            System.out.println("To mark a task as not done, enter its number from the list.");
                            System.out.println("____________________________________________________________");
                            printTodoList(TODO);
                            int unmarkIndex = getTaskIndex(scanner, TODO.size());
                            if (unmarkIndex >= 0) {
                                TODO.get(unmarkIndex).markAsNotDone();
                                System.out.println("____________________________________________________________");
                                System.out.println("OK, I've marked this task as not done yet:");
                                System.out.println("[ ] " + TODO.get(unmarkIndex).getDescription());
                                System.out.println("____________________________________________________________");
                                printTodoList(TODO);
                                System.out.println(banner);
                                break todoMenu;
                            }
                            break;
                        default:
                            System.out.println("That option is incorrect. Please choose one of the Todo options below.");
                        }
                    }
                    break;
                case "bye":
                    System.out.println(bye);
                    scanner.close();
                    return;
                default:
                    System.out.println("horh");

            }
        }




    }
}
