package silver.input;

import java.util.Scanner;
/**
 * Handles terminal input from the user.
 */
public class TerminalInput extends Input {

    private Scanner scanner;

    public TerminalInput() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
