import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Entry point for the doe chatbot application.
 */
public class doe {

    // enums defined for each menu, reflected in the switch cases

    private enum MainMenu {
        NEIGH, MEOW, LIST, TODO, BYE, UNKNOWN;

        public static MainMenu fromString(String input) {
            switch (input.toLowerCase().trim()) { // ensures program is case insensitive and accepts whitespace
                case "1":
                case "neigh":
                    return NEIGH;
                case "2":
                case "meow":
                    return MEOW;
                case "3":
                case "list":
                    return LIST;
                case "4":
                case "todo":
                    return TODO;
                case "bye":
                    return BYE;
                default:
                    return UNKNOWN;
            }
        }
    }

    private enum TodoMenu {
        ADD, REMOVE, VIEW, MARK, UNMARK, EXIT, UNKNOWN;

        public static TodoMenu fromString(String input) {
            switch (input.toLowerCase().trim()) {
                case "1":
                case "add":
                    return ADD;
                case "2":
                case "remove":
                    return REMOVE;
                case "3":
                case "view":
                    return VIEW;
                case "4":
                case "mark":
                    return MARK;
                case "5":
                case "unmark":
                    return UNMARK;
                case "6":
                case "exit":
                    return EXIT;
                default:
                    return UNKNOWN;
            }
        }
    }

    private enum TaskMenu {
        TODO, DEADLINE, EVENT, EXIT, UNKNOWN;

        public static TaskMenu fromString(String input) {
            switch (input.toLowerCase().trim()) {
                case "1":
                case "todo":
                    return TODO;
                case "2":
                case "deadline":
                    return DEADLINE;
                case "3":
                case "event":
                    return EVENT;
                case "4":
                case "exit":
                    return EXIT;
                default:
                    return UNKNOWN;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ToDoManager todoManager = new ToDoManager();
        List<Task> currentTodo = todoManager.getTasks();
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

        String saved = "____________________________________________________________\n"
                + "item saved successfully!\n"
                + "____________________________________________________________\n";

        String removed = "____________________________________________________________\n"
                + "item removed successfully!\n"
                + "____________________________________________________________\n";

        System.out.println(banner);
        while (true) {
            String input = scanner.nextLine();
            MainMenu command = MainMenu.fromString(input);
            switch (command) {
                case NEIGH:
                    System.out.println(neigh);
                    break;
                case MEOW:
                    System.out.println(meow);
                    break;
                case LIST:
                    System.out.println(list);
                    break;
                case TODO:
                    todoMenu:
                    // naming for the todo menu loop
                    while (true) {
                        System.out.println(todo);
                        String todo_input = scanner.nextLine();
                        TodoMenu todoInput = TodoMenu.fromString(todo_input);
                        switch (todoInput) {
                            case ADD:
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
                                    String taskType = scanner.nextLine();
                                    TaskMenu type = TaskMenu.fromString(taskType);
                                    switch (type) {
                                        case TODO:
                                            System.out.println("____________________________________________________________\n"
                                                    + "what you want to add?\n"
                                                    + "____________________________________________________________\n");
                                            String newInput = scanner.nextLine();
                                            if (preventCorrupt(newInput)) {
                                                break;
                                            }
                                            currentTodo.add(new Todo(newInput));
                                            taskAdded = true;
                                            break addTask;
                                        case DEADLINE:
                                            System.out.println("____________________________________________________________\n"
                                                    + "what you want to add?\n"
                                                    + "____________________________________________________________\n");
                                            String deadlineTask = scanner.nextLine();
                                            if (preventCorrupt(deadlineTask)) {
                                                break;
                                            }
                                            System.out.println("____________________________________________________________\n"
                                                    + "input deadline in format dd-mm-yyyy hhmm (e.g. 17-07-2004 1800):\n"
                                                    + "____________________________________________________________\n");
                                            String deadlineTiming = scanner.nextLine();
                                            if (preventCorrupt(deadlineTiming)) {
                                                break;
                                            }
                                            try {
                                                currentTodo.add(new Deadline(deadlineTask, deadlineTiming));
                                                taskAdded = true;
                                                break addTask;
                                            } catch (java.time.format.DateTimeParseException e) {
                                                printUnexpectedInputMessage("invalid date format. please use dd-mm-yyyy (e.g. 17-07-2004 1800).");
                                            }
                                            break;
                                        case EVENT:
                                            System.out.println("____________________________________________________________\n"
                                                    + "what you want to add?\n"
                                                    + "____________________________________________________________\n");
                                            String eventTask = scanner.nextLine();
                                            if (preventCorrupt(eventTask)) {
                                                break;
                                            }
                                            System.out.println("____________________________________________________________\n"
                                                    + "input event time in format dd-mm-yyyy hhmm (e.g. 17-07-2004 1100):\n"
                                                    + "____________________________________________________________\n");
                                            String eventTiming = scanner.nextLine();
                                            if (preventCorrupt(eventTiming)) {
                                                break;
                                            }
                                            try {
                                                currentTodo.add(new Event(eventTask, eventTiming));
                                                taskAdded = true;
                                                break addTask;
                                            } catch (java.time.format.DateTimeParseException e) {
                                                printUnexpectedInputMessage("invalid date format. please use dd-mm-yyyy (e.g. 17-07-2004 1800).");
                                            }
                                            break;
                                        case EXIT:
                                            break addTask;
                                        case UNKNOWN:
                                            printUnexpectedInputMessage(
                                                    "that option is incorrect. please choose 1, 2, 3, todo, deadline, or event.");
                                    }
                                }
                                if (taskAdded) {
                                    todoManager.saveTodo(currentTodo);
                                    System.out.println(saved);
                                    System.out.println(banner);
                                    break todoMenu;
                                }
                                break;
                            case REMOVE:
                                System.out.println("____________________________________________________________\n"
                                        + "what you want to remove?\n"
                                        + "____________________________________________________________\n");
                                printTodoList(currentTodo);
                                String removeInput = scanner.nextLine();
                                int removeIndex = -1;
                                // Step 1: Try treating the input as an index number
                                try {
                                    int taskNumber = Integer.parseInt(removeInput);
                                    // Check if the number is within the valid list range
                                    if (taskNumber >= 1 && taskNumber <= currentTodo.size()) {
                                        removeIndex = taskNumber - 1; // Convert to 0-based index
                                    }
                                } catch (NumberFormatException e) {
                                    // If it throws an exception, the user typed text instead of a number.
                                    // We catch it and silently move to Step 2.
                                }

                                // Step 2: If it wasn't a valid number, search for a matching task name
                                if (removeIndex == -1) {
                                    for (int i = 0; i < currentTodo.size(); i++) {
                                        if (currentTodo.get(i).getDescription().equals(removeInput)) {
                                            removeIndex = i;
                                            break;
                                        }
                                    }
                                }

                                // Step 3: Remove the item if a match was found
                                if (removeIndex >= 0) {
                                    currentTodo.remove(removeIndex);
                                    todoManager.saveTodo(currentTodo);
                                    System.out.println(removed);
                                } else {
                                    printUnexpectedInputMessage("could not find a task with that name or number. please try again.");
                                }
                                System.out.println(banner);
                                break todoMenu;
                            case VIEW:
                                printTodoList(currentTodo);
                                System.out.println(banner);
                                break todoMenu;
                            case MARK:
                                System.out.println("____________________________________________________________");
                                System.out.println("to mark a task as done, enter its number from the list.");
                                System.out.println("____________________________________________________________");
                                printTodoList(currentTodo);
                                int markIndex = getTaskIndex(scanner, currentTodo.size());
                                if (markIndex >= 0) {
                                    currentTodo.get(markIndex).markAsDone();
                                    System.out.println("____________________________________________________________");
                                    System.out.println("nice! i've marked this task as done:");
                                    System.out.println("[x] " + currentTodo.get(markIndex).getDescription());
                                    System.out.println("____________________________________________________________");
                                    printTodoList(currentTodo);
                                    todoManager.saveTodo(currentTodo);
                                    System.out.println(banner);
                                    break todoMenu;
                                }
                                break;
                            case UNMARK:
                                System.out.println("____________________________________________________________");
                                System.out.println("to mark a task as not done, enter its number from the list.");
                                System.out.println("____________________________________________________________");
                                printTodoList(currentTodo);
                                int unmarkIndex = getTaskIndex(scanner, currentTodo.size());
                                if (unmarkIndex >= 0) {
                                    currentTodo.get(unmarkIndex).markAsNotDone();
                                    System.out.println("____________________________________________________________");
                                    System.out.println("ok, i've marked this task as not done yet:");
                                    System.out.println("[ ] " + currentTodo.get(unmarkIndex).getDescription());
                                    System.out.println("____________________________________________________________");
                                    printTodoList(currentTodo);
                                    todoManager.saveTodo(currentTodo);
                                    System.out.println(banner);
                                    break todoMenu;
                                }
                                break;
                            case EXIT:
                                System.out.println(banner);
                                break todoMenu;
                            case UNKNOWN:
                                printUnexpectedInputMessage(
                                        "that option is incorrect. please choose one of the todo options below.");
                        }
                    }
                    break;
                case BYE:
                    System.out.println(bye);
                    scanner.close();
                    return;
                case UNKNOWN:
                    printUnexpectedInputMessage("horh");

            }
        }


    }

    /**
     * Prints the Todo items with their current completion status.
     */
    private static void printTodoList(List<Task> todoItems) {
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

    /**
     * Ensures that "|" is not inputted by user, prevent corruption of .txt file
     */
    private static boolean preventCorrupt(String input) {
        if (input.contains("|")) {
            printUnexpectedInputMessage("error: input cannot contain '|' character.");
            return true;
        } return false;
    }
}
