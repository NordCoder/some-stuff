package util;

import commands.Command;
import commands.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandScanner {
    private Scanner in;
    private Scanner fileIn;

    public CommandScanner() {
        in = new Scanner(System.in);
    }

    public CommandScanner(File file) throws FileNotFoundException {
        fileIn = new Scanner(file);
    }

    public Command readFileCommand() throws Exception {
        try {
            String line = fileIn.nextLine();
            Command command = parseCommand(line);
            if (command == null) {
                throw new Exception();
            } else {
                return command;
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public boolean hasNextS() {
        return fileIn.hasNext();
    }

    public Command readCommand() throws Exception {
        try {
            String line = in.nextLine();
            Command command = parseCommand(line);
            if (command == null) {
                throw new BadInputException(line + " is not a command, type 'help' for a list of commands");
            } else {
                return command;
            }
        } catch (BadInputException e) {
            throw new BadInputException(e.getMessage());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private Command parseCommand(String line) {
        Scanner commandReader = new Scanner(line);
        ArrayList<String> words = new ArrayList<>();
        while (commandReader.hasNext()) {
            words.add(commandReader.next());
        }
        if (words.size() == 1) {
            String comm = words.get(0);
            switch (comm) {
                case "help":
                    return new Help();
                case "show":
                    return new Show();
                case "info":
                    return new Info();
                case "clear":
                    return new Clear();
                case "save":
                    return new Save();
                case "exit":
                    return new Exit();
                case "print_descending":
                    return new PrintDescending();
            }
        } else if (words.size() == 2) {
            String comm = words.get(0);
            String arg = words.get(1);
            switch (comm) {
                case "add":
                    return new Add(arg);
                case "remove_by_id":
                    return new RemoveById(Long.parseLong(arg));
                case "execute_script":
                    return new ExecuteScript(arg);
                case "add_if_max":
                    return new AddIfMax(arg);
                case "add_if_min":
                    return new AddIfMin(arg);
                case "count_less_than_genre":
                    return new CountLessThanGenre(Integer.parseInt(arg));
                case "filter_starts_with_description":
                    return new FilterStartsWithDescription(arg);
            }
        } else if (words.size() == 3) {
            String comm = words.get(0);
            String arg1 = words.get(1);
            String arg2 = words.get(2);
            switch (comm) {
                case "update":
                    return new UpdateId(Long.parseLong(arg1), arg2);
                case "insert_at":
                    return new InsertAt(Integer.parseInt(arg1), arg2);
            }
        }
        return null;
    }
}
