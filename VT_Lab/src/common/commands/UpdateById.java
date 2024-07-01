package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;
import server.db.DatabaseConnection;

import java.util.List;

import static common.util.Util.allowedToChangeById;
import static common.util.Util.checkAuthorization;

public class UpdateById extends AbstractNeedToRegisterCommand implements SpecialCommand {
    private Worker worker;
    @Override
    public Response executeCommand(Request request) {
        try {
            int id = Integer.parseInt(request.getArgs().get(0));
            if (!allowedToChangeById(request.getCard(), id)) {
                return new Response("you are not allowed to update this element");
            }
            request.getReceiver().replaceWorkerById(id, worker, request.getCard().getUserId());
            return new Response("done");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public String getCommandDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String getCommandName() {
        return "update id {element}";
    }

    @Override
    public void setToAdd(Worker w) {
        worker = w;
    }

    @Override
    public void setToAdd(Person p) {

    }

    @Override
    public boolean needsWorker() {
        return true;
    }
}
