package doe;


/**
 * Entry point for the doe chatbot application.
 */

public class doe {
    private Storage storage;
    private TaskList tasks;
    private UserInterface ui;

    /**
     * constructor initialises required chatbot components
     *
     * @param filePath
     */
    public doe(String filePath) {
        ui = new UserInterface();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }
    // enums defined for each menu, reflected in the switch cases

    /**
     * main logical loop of doe bot
     */
    public void run() {
        ui.printBanner(); // starting point

        // main loop
        while (true) {
            String input = ui.readCommand();

            Parser.MainMenu command = Parser.MainMenu.fromString(input); // parser inteprets input using enums
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
                    todoMenu:
                    // name for todo's menu loop
                    while (true) {
                        ui.printTodoMenu();
                        String todo_input = ui.readCommand();
                        Parser.TodoMenu todoInput = Parser.TodoMenu.fromString(todo_input);

                        switch (todoInput) {
                            case ADD:
                                boolean taskAdded = false;

                                addTask:
                                // loop for adding task
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
                                            taskAdded = true;
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
                                                taskAdded = true;
                                                break addTask;
                                            } catch (java.time.format.DateTimeParseException e) {
                                                UserInterface.printUnexpectedInputMessage("\n"
                                                        + "invalid date format. please use dd-mm-yyyy (e.g. 17-07-2004 1800).\n");
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
                                                taskAdded = true;
                                                break addTask;
                                            } catch (java.time.format.DateTimeParseException e) {
                                                UserInterface.printUnexpectedInputMessage("\n"
                                                        + "invalid date format. please use dd-mm-yyyy (e.g. 17-07-2004 1800).\n");
                                            }
                                            break;
                                        case EXIT:
                                            break addTask;

                                        case UNKNOWN:
                                            UserInterface.printUnexpectedInputMessage("\n"
                                                    + "that option is incorrect. please choose 1, 2, 3, todo, deadline, or event.");
                                    }
                                }

                                if (taskAdded) {
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
                                    tasks.getTask(unmarkIndex).markAsDone();
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

                            case EXIT:
                                ui.printBanner();
                                break todoMenu;

                            case UNKNOWN:
                                UserInterface.printUnexpectedInputMessage("\n|"
                                        + "that option is incorrect. please choose one of the todo options below.");
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
     * entry point for doe chatbot
     * @param args
     */

    public static void main(String[] args) {
        // initialises doe with target file and starts the main loop
        new doe("todo.txt").run();
    }
}