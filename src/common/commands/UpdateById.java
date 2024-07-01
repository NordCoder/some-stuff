package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;
import server.db.DatabaseConnection;

import java.util.List;

import static common.util.Util.allowedToChangeById;
import static common.util.Util.checkAuthorization;

public class UpdateById implements SpecialCommand {
    private Worker worker;
    @Override
    public Response execute(Request request) {
        if (!checkAuthorization(request.getCard())) {
            return new Response("Log in to update records");
        }
        try {
            int id = Integer.parseInt(request.getArgs().get(0));
            if (!allowedToChangeById(request.getCard(), id)) {
                return new Response("you are not allowed to update this element");
            }
            request.getReceiver().replaceWorkerById(id, worker, request.getCard().getUserId());
            request.getReceiver().addCommandHistoryRecord(this);
            return new Response("done");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    @Override
    public String getHelpText() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String getHelpName() {
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
