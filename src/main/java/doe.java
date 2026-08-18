import java.util.Locale;
import java.util.Scanner;

/**
 * Entry point for the doe chatbot application.
 */
import java.util.Scanner;
public class doe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

        String bye = "____________________________________________________________\n"
                + "Bye. Hope to see you again soon!\n"
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
