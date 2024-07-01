package common.commands;

import server.Receiver;

import java.util.List;

public class Help implements Command {
    @Override
    public Response execute(Request request) {
        String help = request.getReceiver().getHelp();
        request.getReceiver().addCommandHistoryRecord(this);
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
