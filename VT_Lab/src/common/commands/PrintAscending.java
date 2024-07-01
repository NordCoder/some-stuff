package common.commands;

import server.Receiver;

import java.util.List;

public class PrintAscending extends AbstractCommand {
    @Override
    public Response executeCommand(Request request) {
        String workers = request.getReceiver().getAscending();
        return new Response(workers);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести элементы коллекции в порядке возрастания";
    }

    @Override
    public String getCommandName() {
        return "print_ascending";
    }
}
