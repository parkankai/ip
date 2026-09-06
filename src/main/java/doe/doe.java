package doe;

import java.time.format.DateTimeParseException;
import java.util.List;


/**
 * Entry point for the doe chatbot application.
 */

public class doe {
    private enum GuiState {
        MAIN, TODO_MENU, ADD_TYPE, ADD_DESCRIPTION, ADD_DATE, REMOVE, MARK, UNMARK, FIND
    }

    private Storage storage;
    private TaskList tasks;
    private UserInterface ui;
    private GuiState guiState = GuiState.MAIN;
    private Parser.TaskMenu pendingTaskType;
    private String pendingDescription;

    /**
     * Initialises the required chatbot components such as UI, storage, and tasks.
     *
     * @param filePath The path to the next file used for storing tasks.
     */
    public doe(String filePath) {
        ui = new UserInterface();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /**
     * Returns the first message shown when the graphical interface opens.
     *
     * @return Doe's greeting and the available main-menu commands.
     */
    public String getWelcomeMessage() {
        return "hello! i'm doe :).\nwhat can i do for you?\n"
                + "1. neigh\n2. meow\n3. list\n4. todo\n\ntype \"bye\" to exit";
    }

    /**
     * Processes one GUI submission while retaining the same multi-step menus as the console interface.
     * The GUI controller only presents this returned text; task parsing, mutation, and storage remain here.
     *
     * @param input One line submitted in the graphical interface.
     * @return Doe's response and, when needed, the next prompt.
     */
    public String getResponse(String input) {
        String cleanedInput = input.trim();
        switch (guiState) {
            case MAIN:
                return respondToMainMenu(cleanedInput);
            case TODO_MENU:
                return respondToTodoMenu(cleanedInput);
            case ADD_TYPE:
                return respondToAddType(cleanedInput);
            case ADD_DESCRIPTION:
                return acceptTaskDescription(cleanedInput);
            case ADD_DATE:
                return acceptTaskDate(cleanedInput);
            case REMOVE:
                return removeTask(cleanedInput);
            case MARK:
                return changeTaskStatus(cleanedInput, true);
            case UNMARK:
                return changeTaskStatus(cleanedInput, false);
            case FIND:
                guiState = GuiState.MAIN;
                return matchingTasksText(tasks.findTasks(cleanedInput)) + "\n\n" + getWelcomeMessage();
            default:
                throw new IllegalStateException("Unknown GUI state: " + guiState);
        }
    }

    private String respondToMainMenu(String input) {
        switch (Parser.MainMenu.fromString(input)) {
            case NEIGH:
                return "eurhggghhhhh!";
            case MEOW:
                return "meow!";
            case LIST:
                return "roles and responsibilities\n1. survive nus cs\n2. get a few internships\n"
                        + "3. work at mcdonalds\n4. retire as a manager (hopefully)";
            case TODO:
                guiState = GuiState.TODO_MENU;
                return todoMenuText();
            case BYE:
                return "bye. hope to see you again soon!";
            case UNKNOWN:
            default:
                return "horh\n\n" + getWelcomeMessage();
        }
    }

    private String respondToTodoMenu(String input) {
        switch (Parser.TodoMenu.fromString(input)) {
            case ADD:
                guiState = GuiState.ADD_TYPE;
                return "what type of task would you like to add?\n1. todo\n2. deadline\n3. event\n4. exit";
            case REMOVE:
                guiState = GuiState.REMOVE;
                return "what you want to remove?\n\n" + taskListText(tasks.getTasks());
            case VIEW:
                guiState = GuiState.MAIN;
                return taskListText(tasks.getTasks()) + "\n\n" + getWelcomeMessage();
            case MARK:
                guiState = GuiState.MARK;
                return "to mark a task as done, enter its number from the list.\n\n"
                        + taskListText(tasks.getTasks());
            case UNMARK:
                guiState = GuiState.UNMARK;
                return "to mark a task as not done, enter its number from the list.\n\n"
                        + taskListText(tasks.getTasks());
            case FIND:
                guiState = GuiState.FIND;
                return "enter a keyword to search for:";
            case EXIT:
                guiState = GuiState.MAIN;
                return getWelcomeMessage();
            case UNKNOWN:
            default:
                return "that option is incorrect. please choose one of the todo options below.\n\n" + todoMenuText();
        }
    }

    private String respondToAddType(String input) {
        pendingTaskType = Parser.TaskMenu.fromString(input);
        switch (pendingTaskType) {
            case TODO:
            case DEADLINE:
            case EVENT:
                guiState = GuiState.ADD_DESCRIPTION;
                return "what you want to add?";
            case EXIT:
                guiState = GuiState.TODO_MENU;
                return todoMenuText();
            case UNKNOWN:
            default:
                return "that option is incorrect. please choose 1, 2, 3, todo, deadline, or event.";
        }
    }

    private String acceptTaskDescription(String input) {
        assert pendingTaskType == Parser.TaskMenu.TODO
                || pendingTaskType == Parser.TaskMenu.DEADLINE
                || pendingTaskType == Parser.TaskMenu.EVENT
                : "A task type must be selected before its description is accepted";
        if (containsStorageDelimiter(input)) {
            return "error: input cannot contain '|' character.\n\nwhat you want to add?";
        }
        pendingDescription = input;
        if (pendingTaskType == Parser.TaskMenu.TODO) {
            tasks.addTask(new Todo(pendingDescription));
            return finishAddingTask();
        }

        guiState = GuiState.ADD_DATE;
        if (pendingTaskType == Parser.TaskMenu.DEADLINE) {
            return "input deadline in format dd-mm-yyyy hhmm (e.g. 17-07-2004 1800):";
        }
        return "input event time in format dd-mm-yyyy hhmm (e.g. 17-07-2004 1100):";
    }

    private String acceptTaskDate(String input) {
        assert pendingTaskType == Parser.TaskMenu.DEADLINE || pendingTaskType == Parser.TaskMenu.EVENT
                : "Only dated task types may request a date";
        assert pendingDescription != null : "A task description must be accepted before its date";
        if (containsStorageDelimiter(input)) {
            return "error: input cannot contain '|' character.";
        }
        try {
            if (pendingTaskType == Parser.TaskMenu.DEADLINE) {
                tasks.addTask(new Deadline(pendingDescription, input));
            } else {
                tasks.addTask(new Event(pendingDescription, input));
            }
            return finishAddingTask();
        } catch (DateTimeParseException exception) {
            return "invalid date format. please use dd-mm-yyyy (e.g. 17-07-2004 1800).";
        }
    }

    private String finishAddingTask() {
        assert pendingTaskType != null : "A task type must remain available until the add operation finishes";
        assert pendingDescription != null : "A task description must remain available until the add operation finishes";
        storage.save(tasks.getTasks());
        guiState = GuiState.MAIN;
        pendingTaskType = null;
        pendingDescription = null;
        return "item saved successfully!\n\n" + getWelcomeMessage();
    }

    private String removeTask(String input) {
        int index = tasks.findTaskIndex(input);
        guiState = GuiState.MAIN;
        if (index < 0) {
            return "could not find a task with that name or number. please try again.\n\n"
                    + getWelcomeMessage();
        }
        assert index < tasks.size() : "A successful task lookup must return an existing index";
        Task removedTask = tasks.removeTask(index);
        storage.save(tasks.getTasks());
        return "item removed successfully!\n" + removedTask + "\n\n" + getWelcomeMessage();
    }

    private String changeTaskStatus(String input, boolean markAsDone) {
        guiState = GuiState.MAIN;
        if (tasks.size() == 0) {
            return "there are no tasks to mark yet. add a task first.\n\n" + getWelcomeMessage();
        }

        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException exception) {
            index = -1;
        }
        if (index < 0 || index >= tasks.size()) {
            return "that task number is incorrect. please enter a number from 1 to " + tasks.size()
                    + ".\n\n" + getWelcomeMessage();
        }

        assert index >= 0 && index < tasks.size() : "A validated task number must map to an existing task";
        Task task = tasks.getTask(index);
        if (markAsDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        storage.save(tasks.getTasks());
        String message = markAsDone ? "nice! i've marked this task as done:\n[x] "
                : "ok i've marked this task as not done yet:\n[] ";
        return message + task.getDescription() + "\n\n" + taskListText(tasks.getTasks())
                + "\n\n" + getWelcomeMessage();
    }

    private static boolean containsStorageDelimiter(String input) {
        return input.contains("|");
    }

    private static String todoMenuText() {
        return "modify todo list\n1. add\n2. remove\n3. view\n4. mark\n5. unmark\n6. find\n7. exit";
    }

    private static String taskListText(List<Task> taskItems) {
        StringBuilder result = new StringBuilder("here are the tasks in your list:");
        for (int i = 0; i < taskItems.size(); i++) {
            result.append('\n').append(i + 1).append(". ").append(taskItems.get(i));
        }
        return result.toString();
    }

    private static String matchingTasksText(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            return "no matching tasks found.";
        }
        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            result.append('\n').append(i + 1).append(". ").append(matchingTasks.get(i));
        }
        return result.toString();
    }

    /**
     * Starts the main logical loop of doe bot to continuously read and execute commands.
     */
    public void run() {
        ui.printBanner(); // starting point

        // main loop
        while (true) {
            String input = ui.readCommand();

            Parser.MainMenu command = Parser.MainMenu.fromString(input); // parser interprets input using enums
            switch (command) {
                case NEIGH:
                    ui.printNeigh();
                    break;
                case MEOW:
                    ui.printMeow();
                    break;
                case LIST:
                    ui.printList();
                    break;
                case TODO:
                    todoMenu: //name for todo's menu loop
                    while (true) {
                        ui.printTodoMenu();
                        String todoInputStr = ui.readCommand();
                        Parser.TodoMenu todoInput = Parser.TodoMenu.fromString(todoInputStr);

                        switch (todoInput) {
                            case ADD:
                                boolean isTaskAdded = false;

                                addTask: // Menu loop for adding task
                                while (true) {
                                    ui.printAddMenu();
                                    String taskType = ui.readCommand();
                                    Parser.TaskMenu type = Parser.TaskMenu.fromString(taskType);

                                    switch (type) {
                                        case TODO:
                                            ui.printAddPrompt();
                                            String newInput = ui.readCommand();
                                            if (UserInterface.preventCorrupt(newInput)) {
                                                break;
                                            }
                                            tasks.addTask(new Todo(newInput));
                                            isTaskAdded = true;
                                            break addTask;

                                        case DEADLINE:
                                            ui.printAddPrompt();
                                            String deadlineTask = ui.readCommand();
                                            if (UserInterface.preventCorrupt(deadlineTask)) {
                                                break;
                                            }
                                            ui.printDeadlineDate();
                                            String deadlineDate = ui.readCommand();
                                            if (UserInterface.preventCorrupt(deadlineDate)) {
                                                break;
                                            }
                                            try {
                                                tasks.addTask(new Deadline(deadlineTask, deadlineDate));
                                                isTaskAdded = true;
                                                break addTask;
                                            } catch (java.time.format.DateTimeParseException e) {
                                                UserInterface.printUnexpectedInputMessage("\n"
                                                        + "invalid date format. please use dd-mm-yyyy "
                                                        + "(e.g. 17-07-2004 1800).\n");
                                            }
                                            break;
                                        case EVENT:
                                            ui.printAddPrompt();
                                            String eventTask = ui.readCommand();
                                            if (UserInterface.preventCorrupt(eventTask)) {
                                                break;
                                            }
                                            ui.printEventDate();
                                            String eventDate = ui.readCommand();
                                            if (UserInterface.preventCorrupt(eventDate)) {
                                                break;
                                            }
                                            try {
                                                tasks.addTask(new Event(eventTask, eventDate));
                                                isTaskAdded = true;
                                                break addTask;
                                            } catch (java.time.format.DateTimeParseException e) {
                                                UserInterface.printUnexpectedInputMessage("\n"
                                                        + "invalid date format. please use dd-mm-yyyy "
                                                        + "(e.g. 17-07-2004 1800).\n");
                                            }
                                            break;
                                        case EXIT:
                                            break addTask;

                                        case UNKNOWN:
                                            UserInterface.printUnexpectedInputMessage("\n"
                                                    + "that option is incorrect. "
                                                    + "please choose 1, 2, 3, todo, deadline, or event.");
                                    }
                                }

                                if (isTaskAdded) {
                                    storage.save(tasks.getTasks());
                                    ui.printSaved();
                                    ui.printBanner(); // shows main menu instructions again
                                    break todoMenu; // returns to main menu banner
                                }
                                break;

                            case REMOVE:
                                ui.printRemovePrompt();
                                ui.printTodoList(tasks.getTasks());
                                String removeInput = ui.readCommand();

                                int removeIndex = tasks.findTaskIndex(removeInput); // find index

                                if (removeIndex >= 0) {
                                    tasks.removeTask(removeIndex);
                                    storage.save(tasks.getTasks());
                                    ui.printRemoved();
                                } else {
                                    UserInterface.printUnexpectedInputMessage(
                                            "could not find a task with that name or number. please try again.");
                                }
                                ui.printBanner();
                                break todoMenu;

                            case VIEW:
                                ui.printTodoList(tasks.getTasks());
                                ui.printBanner();
                                break todoMenu;

                            case MARK:
                                ui.printMarkPrompt();
                                ui.printTodoList(tasks.getTasks());

                                int markIndex = ui.getTaskIndex(tasks.size());
                                if (markIndex >= 0) {
                                    tasks.getTask(markIndex).markAsDone();
                                    System.out.println("____________________________________________________________");
                                    System.out.println("nice! i've marked this task as done:");
                                    System.out.println("[x] " + tasks.getTask(markIndex).getDescription());
                                    System.out.println("____________________________________________________________");
                                    ui.printTodoList(tasks.getTasks());
                                    storage.save(tasks.getTasks());
                                    ui.printBanner();
                                    break todoMenu;
                                }
                                break;

                            case UNMARK:
                                ui.printUnmarkPrompt();
                                ui.printTodoList(tasks.getTasks());

                                int unmarkIndex = ui.getTaskIndex(tasks.size());
                                if (unmarkIndex >= 0) {
                                    tasks.getTask(unmarkIndex).markAsNotDone();
                                    System.out.println("____________________________________________________________");
                                    System.out.println("ok i've marked this task as not done yet:");
                                    System.out.println("[] " + tasks.getTask(unmarkIndex).getDescription());
                                    System.out.println("____________________________________________________________");
                                    ui.printTodoList(tasks.getTasks());
                                    storage.save(tasks.getTasks());
                                    ui.printBanner();
                                    break todoMenu;
                                }
                                break;

                            case FIND:
                                ui.printFindPrompt();
                                String keyword = ui.readCommand();
                                ui.printMatchingTasks(tasks.findTasks(keyword));
                                ui.printBanner();
                                break todoMenu;

                            case EXIT:
                                ui.printBanner();
                                break todoMenu;

                            case UNKNOWN:
                                UserInterface.printUnexpectedInputMessage("\n|"
                                        + "that option is incorrect. please choose one of the todo options below.");
                                break;
                        }
                    }
                    break;
                case BYE:
                    ui.printBye();
                    ui.closeScanner();
                    return; // terminates application by exiting run

                case UNKNOWN:
                    UserInterface.printUnexpectedInputMessage("horh");
            }
        }
    }

    /***
     * Starts the doe chatbot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // initialises doe with target file and starts the main loop
        new doe("todo.txt").run();
    }
}
