package common.commands;

import server.Receiver;

import java.util.List;

public class Show implements Command{
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        String workers = receiver.getAscending();
        receiver.addCommandHistoryRecord(this);
        return new Response(workers);
    }

    @Override
    public String getHelpText() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String getHelpName() {
        return "show";
    }
}
