import java.util.Locale;
/**
 * Entry point for the doe chatbot application.
 */
import java.util.Scanner;
import java.util.ArrayList;
public class doe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> TODO = new ArrayList<>();
        String banner = "____________________________________________________________\n"
                + "     _          \n"
                + "  __| | ___  ___\n"
                + " / _` |/ _ \\ / _ \\\n"
                + "| (_| | (_) |  __/\n"
                + " \\__,_|\\___/ \\___|\n"

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
                    System.out.println(todo);
                    String todo_input = scanner.nextLine().toLowerCase();
                    switch (todo_input) {
                        case "add":
                            System.out.println("____________________________________________________________\n"
                                    + "what you want to add?\n"
                                    + "____________________________________________________________\n");
                            String to_add = scanner.nextLine();
                            TODO.add(to_add);
                            System.out.println(banner);
                            break;
                        case "remove":
                            System.out.println("____________________________________________________________\n"
                                    + "what you want to remove?\n"
                                    + "____________________________________________________________\n");
                            String to_remove = scanner.nextLine();
                            TODO.remove(to_remove);
                            System.out.println(banner);
                            break;
                        case "view":
                            for (int i = 0; i < TODO.size(); i++) {
                                System.out.println((i + 1) + ". " + TODO.get(i));
                            }
                            System.out.println(banner);
                            break;
                        default:
                            System.out.println("wat");
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
