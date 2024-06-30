package common.commands;

import server.Receiver;

import java.util.List;

public class PrintAscending implements Command {
    @Override
    public Response execute(Request request) {
        String workers = request.getReceiver().getAscending();
        request.getReceiver().addCommandHistoryRecord(this);
        return new Response(workers);
    }

    @Override
    public String getHelpText() {
        return "вывести элементы коллекции в порядке возрастания";
    }

    @Override
    public String getHelpName() {
        return "print_ascending";
    }
}
