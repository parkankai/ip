package doe;


import java.awt.*;
import java.util.Scanner;
import java.util.List;

public class UserInterface {
    private final Scanner scanner;

    // ui banners as previous versions
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

    String addMenu = "____________________________________________________________\n"
            + "what type of task would you like to add?\n"
            + "1. todo\n"
            + "2. deadline\n"
            + "3. event\n"
            + "4. exit\n"
            + "____________________________________________________________\n";

    String addPrompt = "____________________________________________________________\n"
            + "what you want to add?\n"
            + "____________________________________________________________\n";

    String removePrompt = "____________________________________________________________\n"
            + "what you want to remove?\n"
            + "____________________________________________________________\n";

    String deadlineDate = "____________________________________________________________\n"
            + "input deadline in format dd-mm-yyyy hhmm (e.g. 17-07-2004 1800):\n"
            + "____________________________________________________________\n";

    String eventDate = "____________________________________________________________\n"
            + "input event time in format dd-mm-yyyy hhmm (e.g. 17-07-2004 1100):\n"
            + "____________________________________________________________\n";

    String markDone = "____________________________________________________________\n"
            + "to mark a task as done, enter its number from the list.\n"
            + "____________________________________________________________";

    String markUndone = "____________________________________________________________\n"
            + "to mark a task as not done, enter its number from the list.\n"
            + "____________________________________________________________";

    String invalidDate = "invalid date format. please use dd-mm-yyyy (e.g. 17-07-2004 1800).\n";

    String invalidAddSelection = "that option is incorrect. please choose 1, 2, 3, todo, deadline, or event.";

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    // collection of printing functions for ease of showing ui

    public void printBanner() { System.out.println(banner); }
    public void printNeigh() { System.out.println(neigh); }
    public void printMeow() { System.out.println(meow); }
    public void printList() { System.out.println(list); }
    public void printTodoMenu() { System.out.println(todo); }
    public void printAddMenu() { System.out.println(addMenu); }
    public void printAddPrompt() { System.out.println(addPrompt); }
    public void printRemovePrompt() { System.out.println(removePrompt); }
    public void printDeadlineDate() { System.out.println(deadlineDate); }
    public void printEventDate() { System.out.println(eventDate); }
    public void printMarkPrompt() { System.out.println(markDone); }
    public void printUnmarkPrompt() { System.out.println(markUndone); }
    public void printInvalidDate() { System.out.println(invalidDate); }
    public void printInvalidAddSelection() { System.out.println(invalidAddSelection); }
    public void printBye() { System.out.println(bye); }
    public void printSaved() { System.out.println(saved); }
    public void printRemoved() { System.out.println(removed); }
    public void closeScanner() { scanner.close(); }

    /**
     *prints the todo list as specified
     */
    public void printTodoList(List<Task> todoItems) {
        System.out.println("____________________________________________________________");
        System.out.println("here are the tasks in your list:");
        for (int i = 0; i < todoItems.size(); i++) {
            Task task = todoItems.get(i);
            System.out.println((i + 1) + ". " + task);
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * reads task number and returns zero-based index for user convenience, returns -1 when invalid
     */

    public int getTaskIndex(int numberOfTasks) {
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
            // Handled by unexpected input message below
        }
        printUnexpectedInputMessage(
                "that task number is incorrect. please enter a number from 1 to " + numberOfTasks + ".");
        return -1;
    }

    /**
     * Displays an existing invalid-input message through the application's input exception.
     */
    public static void printUnexpectedInputMessage(String message) {
        try {
            throw new UnexpectedInputException(message);
        } catch (UnexpectedInputException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Ensures that "|" is not inputted by user, prevent corruption of .txt file
     */
    public static boolean preventCorrupt(String input) {
        if (input.contains("|")) {
            printUnexpectedInputMessage("error: input cannot contain '|' character.");
            return true;
        } return false;
    }

}


















