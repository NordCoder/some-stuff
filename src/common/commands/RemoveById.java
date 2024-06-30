package common.commands;

import server.Receiver;

import java.util.List;

public class RemoveById implements Command {
    @Override
    public Response execute(Request request) {
        try {
            long id = Long.parseLong(request.getArgs().get(0));
            boolean check = request.getReceiver().removeWorkerById(id);
            request.getReceiver().addCommandHistoryRecord(this);
            return new Response(check ? "done!" : "there's no worker with id " + id);
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
