import java.util.Scanner;
import java.util.ArrayList;

/**
 * Entry point for the doe chatbot application.
 */
public class doe {

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
                + "eurhggghhhhh!\n"
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
                + "6. exit\n"
                + "____________________________________________________________\n";

        String bye = "____________________________________________________________\n"
                + "bye. hope to see you again soon!\n"
                + "____________________________________________________________\n";

        System.out.println(banner);
        while(true){
            String input = scanner.nextLine().toLowerCase();
            switch (input) {
                case "1":
                case "neigh":
                    System.out.println(neigh);
                    break;
                case "2":
                case "meow":
                    System.out.println(meow);
                    break;
                case "3":
                case "list":
                    System.out.println(list);
                    break;
                case "4":
                case "todo":
                    todoMenu: // naming for the todo menu loop
                    while (true) {
                        System.out.println(todo);
                        String todo_input = scanner.nextLine().toLowerCase();
                        switch (todo_input) {
                        case "1":
                        case "add":
                            boolean taskAdded = false;
                            addTask:
                            while (true) {
                                System.out.println("____________________________________________________________\n"
                                        + "what type of task would you like to add?\n"
                                        + "1. todo\n"
                                        + "2. deadline\n"
                                        + "3. event\n"
                                        + "4. exit\n"
                                        + "____________________________________________________________\n");
                                String taskType = scanner.nextLine().toLowerCase();
                                switch (taskType) {
                                case "1":
                                case "todo":
                                    System.out.println("____________________________________________________________\n"
                                            + "what you want to add?\n"
                                            + "____________________________________________________________\n");
                                    TODO.add(new Task(scanner.nextLine()));
                                    taskAdded = true;
                                    break addTask;
                                case "2":
                                case "deadline":
                                    System.out.println("____________________________________________________________\n"
                                            + "what you want to add?\n"
                                            + "____________________________________________________________\n");
                                    String deadlineTask = scanner.nextLine();
                                    System.out.println("____________________________________________________________\n"
                                            + "type a sentence to describe its deadline (e.g. by friday 5pm):\n"
                                            + "____________________________________________________________\n");
                                    TODO.add(new Deadline(deadlineTask, scanner.nextLine()));
                                    taskAdded = true;
                                    break addTask;
                                case "3":
                                case "event":
                                    System.out.println("____________________________________________________________\n"
                                            + "what you want to add?\n"
                                            + "____________________________________________________________\n");
                                    String eventTask = scanner.nextLine();
                                    System.out.println("____________________________________________________________\n"
                                            + "type a sentence to describe its timing (e.g. from 2pm to 4pm):\n"
                                            + "____________________________________________________________\n");
                                    TODO.add(new Event(eventTask, scanner.nextLine()));
                                    taskAdded = true;
                                    break addTask;
                                case "4":
                                case "exit":
                                    break addTask;
                                default:
                                    printUnexpectedInputMessage(
                                            "that option is incorrect. please choose 1, 2, 3, todo, deadline, or event.");
                                }
                            }
                            if (taskAdded) {
                                System.out.println(banner);
                                break todoMenu;
                            }
                            break;
                        case "2":
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
                        case "3":
                        case "view":
                            printTodoList(TODO);
                            System.out.println(banner);
                            break todoMenu;
                        case "4":
                        case "mark":
                            System.out.println("____________________________________________________________");
                            System.out.println("to mark a task as done, enter its number from the list.");
                            System.out.println("____________________________________________________________");
                            printTodoList(TODO);
                            int markIndex = getTaskIndex(scanner, TODO.size());
                            if (markIndex >= 0) {
                                TODO.get(markIndex).markAsDone();
                                System.out.println("____________________________________________________________");
                                System.out.println("nice! i've marked this task as done:");
                                System.out.println("[x] " + TODO.get(markIndex).getDescription());
                                System.out.println("____________________________________________________________");
                                printTodoList(TODO);
                                System.out.println(banner);
                                break todoMenu;
                            }
                            break;
                        case "5":
                        case "unmark":
                            System.out.println("____________________________________________________________");
                            System.out.println("to mark a task as not done, enter its number from the list.");
                            System.out.println("____________________________________________________________");
                            printTodoList(TODO);
                            int unmarkIndex = getTaskIndex(scanner, TODO.size());
                            if (unmarkIndex >= 0) {
                                TODO.get(unmarkIndex).markAsNotDone();
                                System.out.println("____________________________________________________________");
                                System.out.println("ok, i've marked this task as not done yet:");
                                System.out.println("[ ] " + TODO.get(unmarkIndex).getDescription());
                                System.out.println("____________________________________________________________");
                                printTodoList(TODO);
                                System.out.println(banner);
                                break todoMenu;
                            }
                            break;
                        case "6":
                        case "exit":
                            System.out.println(banner);
                            break todoMenu;
                        default:
                            printUnexpectedInputMessage(
                                    "that option is incorrect. please choose one of the todo options below.");
                        }
                    }
                    break;
                case "bye":
                    System.out.println(bye);
                    scanner.close();
                    return;
                default:
                    printUnexpectedInputMessage("horh");

            }
        }




    }

    /** Prints the Todo items with their current completion status.  */
    private static void printTodoList(ArrayList<Task> todoItems) {
        System.out.println("____________________________________________________________");
        System.out.println("here are the tasks in your list:");
        for (int i = 0; i < todoItems.size(); i++) {
            Task task = todoItems.get(i);
            System.out.println((i + 1) + ". " + task);
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads a task number and returns its zero-based index, or -1 when it is invalid.
     */
    private static int getTaskIndex(Scanner scanner, int numberOfTasks) {
        if (numberOfTasks == 0) {
            System.out.println("there are no tasks to mark yet. add a task first.");
            return -1;
        }
        System.out.println("enter the task number:");
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine().trim());
            if (taskNumber >= 1 && taskNumber <= numberOfTasks) {
                return taskNumber - 1;
            }
        } catch (NumberFormatException exception) {
            // The invalid-input message below explains how to correct the entry.
        }
        printUnexpectedInputMessage(
                "that task number is incorrect. please enter a number from 1 to " + numberOfTasks + ".");
        return -1;
    }

    /**
     * Displays an existing invalid-input message through the application's input exception.
     */
    private static void printUnexpectedInputMessage(String message) {
        try {
            throw new UnexpectedInputException(message);
        } catch (UnexpectedInputException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
