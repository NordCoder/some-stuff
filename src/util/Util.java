package util;

import commands.*;
import shell.Receiver;

import java.util.HashMap;
import java.util.Map;

public class Util {
    public static Map<String, Command> getAllCommands() {
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
        commands.put("exit", new Exit());
        commands.put("add_if_max", new AddIfMax());
        commands.put("add_if_min", new AddIfMin());
        commands.put("history", new History());
        commands.put("remove_any_by_person", new RemoveAnyByPerson());
        commands.put("print_ascending", new PrintAscending());
        commands.put("print_field_ascending_person", new PrintFieldAscendingPerson());
        return commands;
    }

}
