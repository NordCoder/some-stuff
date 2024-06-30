package client;

import common.commands.Command;
import common.commands.Request;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.CustomPair;
import common.util.Serializer;

import java.util.List;

import static common.util.Util.*;

public class CommandSender {
    private InputParser inputParser = new InputParser(new ConsoleInputGetter(), getClientCommands());
    private Serializer<CustomPair<Command, Request>> commandSerializer = new Serializer<>();
    private Client parent;

    public CommandSender(Client parent) {
        this.parent = parent;
    }

    public void sendCommand(CustomPair<Command, Request> command) throws Exception {
        parent.getConnectionManager().getBuffer().put(commandSerializer.serialize(command));
        parent.getConnectionManager().getBuffer().flip();
        parent.getConnectionManager().getChannel().send(parent.getConnectionManager().getBuffer(), parent.getConnectionManager().getAddress());
        parent.getConnectionManager().getBuffer().clear();
    }

    public void sendNextCommand() throws Exception {
        sendCommand(readHandleCommand(inputParser, parent.getAccountCard()));
    }


}
