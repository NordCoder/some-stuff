package common.commands;

import server.Receiver;

import java.util.List;

public class Help implements Command {
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        String help = receiver.getHelp();
        receiver.addCommandHistoryRecord(this);
        return new Response(help);
    }

    @Override
    public String getHelpText() {
        return "вывести справку по доступным командам";
    }

    @Override
    public String getHelpName() {
        return "help";
    }
}
