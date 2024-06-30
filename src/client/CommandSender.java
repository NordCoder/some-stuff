package client;

import common.commands.Command;
import common.commands.Request;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.CustomPair;
import common.util.Serializer;

import static client.ClientConnectionManager.*;
import static common.util.Util.getClientCommands;
import static common.util.Util.handleCommandsWithAdditionalInfo;

public class CommandSender {
    private static InputParser inputParser = new InputParser(new ConsoleInputGetter(), getClientCommands());
    private static Serializer<CustomPair<Command, Request>> commandSerializer = new Serializer<>();

    public static CustomPair<Command, Request> readHandleCommand() throws Exception {
        CustomPair<Command, Request> command = inputParser.nextCommand();
        handleCommandsWithAdditionalInfo(command, new InputParser(new ConsoleInputGetter(), getClientCommands()));
        return command;
    }

    public static void sendCommand(CustomPair<Command, Request> command) throws Exception {
        getBuffer().put(commandSerializer.serialize(command));
        getBuffer().flip();
        getChannel().send(getBuffer(), getAddress());
        getBuffer().clear();
    }

    public static void sendNextCommand() throws Exception {
        CustomPair<Command, Request> command = readHandleCommand();
        sendCommand(command);
    }


}
