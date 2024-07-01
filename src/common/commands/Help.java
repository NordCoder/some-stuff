package common.commands;

import server.Receiver;

import java.util.List;

public class Help extends AbstractCommand {
    @Override
    protected Response executeCommand(Request request) {
        String help = request.getReceiver().getHelp();
        return new Response(help);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести справку по доступным командам";
    }

    @Override
    public String getCommandName() {
        return "help";
    }
}
