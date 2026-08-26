package doe;


/**
 * Deals with making sense of the user command and inputs.
 */
public class Parser {

    public enum MainMenu {
        NEIGH, MEOW, LIST, TODO, BYE, UNKNOWN;

        public static MainMenu fromString(String input) {
            switch (input.toLowerCase().trim()) {
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

    public enum TodoMenu {
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

    public enum TaskMenu {
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
}