package common.util;

import common.commands.*;
import common.input.ConsoleInputGetter;
import common.input.InputParser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static Map<String, Command> getServerCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("add", new Add());
        commands.put("update", new UpdateById());
        commands.put("remove_by_id", new RemoveById());
        commands.put("clear", new Clear());
        commands.put("save", new Save());
        commands.put("execute_script", new ExecuteScript());
        commands.put("add_if_max", new AddIfMax());
        commands.put("add_if_min", new AddIfMin());
        commands.put("history", new History());
        commands.put("remove_any_by_person", new RemoveAnyByPerson());
        commands.put("print_ascending", new PrintAscending());
        commands.put("print_field_ascending_person", new PrintFieldAscendingPerson());
        commands.put("register", new Register());
        return commands;
    }

    public static Map<String, Command> getClientCommands() {
        Map<String, Command> commands = getServerCommands();
        commands.remove("save");
        commands.remove("execute_script");
        return commands;
    }


    public static void handleCommandsWithAdditionalInfo(CustomPair<Command, Request> command, InputParser parser) throws Exception {
        if (command.getFirst() instanceof SpecialCommand) {
            if (((SpecialCommand) command.getFirst()).needsWorker()) {
                ((SpecialCommand) command.getFirst()).setToAdd(parser.readWorker());
            } else {
                ((SpecialCommand) command.getFirst()).setToAdd(parser.readPerson());
            }
        } else if (command.getFirst() instanceof Register) {
            command.getSecond().setArgs(parser.readUsernamePassword());
        } else if (command.getFirst() instanceof Login) {
            List<String> userPassword = parser.readUsernamePassword();
            command.getSecond().getCard().setUsername(userPassword.get(0));
            command.getSecond().setArgs(userPassword);
        }
    }

    public static String generateSHA512Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, hashBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 128) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static CustomPair<Command, Request> readHandleCommand(InputParser parser, AccountCard card) throws Exception {
        CustomPair<Command, List<String>> command = parser.nextCommand();
        CustomPair<Command, Request> requestedCommand = new CustomPair<>(command.getFirst(), new Request(card, command.getSecond()));
        handleCommandsWithAdditionalInfo(requestedCommand, new InputParser(new ConsoleInputGetter(), getServerCommands()));
        return requestedCommand;
    }

}
