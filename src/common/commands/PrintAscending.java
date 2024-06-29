package common.commands;

import server.Receiver;

import java.util.List;

public class PrintAscending implements Command {
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        String workers = receiver.getAscending();
        receiver.addCommandHistoryRecord(this);
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
