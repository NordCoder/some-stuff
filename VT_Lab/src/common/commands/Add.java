package common.commands;

import common.entity.Person;
import common.entity.Worker;

import static common.util.Util.checkAuthorization;

public class Add implements SpecialCommand {
    private Worker worker = null;
    @Override
    public Response execute(Request request) {
        if (!checkAuthorization(request.getCard())) {
            return new Response("Log in to create records");
        }
        request.getReceiver().addCommandHistoryRecord(this);
        try {
            request.getReceiver().addWorker(worker, request.getCard().getUserId());
            return new Response("done");
        } catch (Exception e) {
            return new Response("error: " + e.getMessage());
        }
    }

    @Override
    public String getHelpText() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public String getHelpName() {
        return "add {element}";
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
