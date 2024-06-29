package common.commands;

import server.Receiver;

import java.util.List;

public class RemoveById implements Command {
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        try {
            long id = Long.parseLong(args.get(0));
            receiver.removeWorkerById(id);
            receiver.addCommandHistoryRecord(this);
            return new Response("done!");
        } catch (Exception e) {
            return new Response("id must be a number");
        }
    }

    @Override
    public String getHelpText() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public String getHelpName() {
        return "remove_by_id id";
    }
}
