package common.commands;

import server.Receiver;

import java.util.List;

import static common.util.Util.allowedToChangeById;
import static common.util.Util.checkAuthorization;

public class RemoveById extends AbstractNeedToRegisterCommand {
    @Override
    public Response executeCommand(Request request) {
        try {
            int id = Integer.parseInt(request.getArgs().get(0));
            if (!allowedToChangeById(request.getCard(), id)) {
                return new Response("you are not allowed to delete this record");
            }
            boolean check = request.getReceiver().removeWorkerById(id);
            return new Response(check ? "done!" : "there's no worker with id " + id);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public String getCommandDescription() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public String getCommandName() {
        return "remove_by_id id";
    }
}
