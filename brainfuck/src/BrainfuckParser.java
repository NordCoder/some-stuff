import java.util.Scanner;

public class BrainfuckParser {
    private int commandP = 0;
    private final String commands;
    private final int[] array = new int[30000];
    private int pointer = 0;
    private final char EOF = 0;
    private char cur;
    private final String ALLOWED = "><+-.,[]";
    private final Scanner scanner;

    public BrainfuckParser(String commands) {
        this.commands = commands;
        scanner = new Scanner(System.in);
        start();
    }

    private boolean hasNext() {
        return commandP < commands.length();
    }

    private char nextChar() {
        return commands.charAt(commandP++);
    }

    private char take() {
        cur = hasNext() ? nextChar() : EOF;
        if (ALLOWED.indexOf(cur) == -1 && cur != 0) {
            throw new RuntimeException("illegal syntax");
        }
        return cur;
    }

    private boolean takeExp(char expected) {
        if (cur == expected) {
            take();
            return true;
        } else {
            return false;
        }
    }

    private void skipWhitespace() {
        while (takeExp(' ') || takeExp('\n') || takeExp('\t')) {
            take();
        }
    }

    private String parseWhile() throws RuntimeException {
        StringBuilder sb = new StringBuilder();
        take();
        while (!takeExp(']')) {
            sb.append(cur);
            take();
            if (cur == 0) {
                throw new RuntimeException("no while close (file ended)");
            }
        }
        return sb.toString();
    }

    private void executeWhile(String whileCommands) {
        int startPointer = pointer;
        int endPointer = pointer + whileCommands.length();
        while (array[pointer] != 0) {
            System.err.println(array[pointer]);
            if (pointer < endPointer) {
                processCommand(whileCommands.charAt(pointer));
            } else {
                pointer = startPointer;
            }
        }
        commandP--;
    }

    private void processCommand(char c) {
        System.err.println(c);
        if (c == '[') {
            executeWhile(parseWhile());
        } else {
            executeCommand(c);
        }
    }

    private void executeCommand(char c) {
        if (c == '>') {
            pointer++;
        } else if (c == '<') {
            if (pointer != 0) {
                pointer--;
            } else {
                System.err.println("pointer is at 0 position");
            }
        } else if (c == '+') {
            array[pointer]++;
        } else if (c == '-') {
            array[pointer]--;
        } else if (c == '.') {
            System.out.println((char) array[pointer]);
        } else if (c == ',') {
            array[pointer] = scanner.nextInt();
            System.err.println(array[pointer]);
            scanner.nextLine();
        } else {
            System.err.println("what do you mean?");
        }
        System.err.println("executed");
    }

    private void start() {
        while(hasNext()) {
            execute();
        }
    }

    private void execute() {
        skipWhitespace();
        processCommand(take());
        skipWhitespace();
    }
}
