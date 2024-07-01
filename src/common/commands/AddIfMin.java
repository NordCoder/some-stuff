package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.util.List;

import static common.util.Util.checkAuthorization;

public class AddIfMin implements SpecialCommand {
    private Worker worker = null;
    @Override
    public Response execute(Request request) {
        if (!checkAuthorization(request.getCard())) {
            return new Response("Log in to create records");
        }
        request.getReceiver().addCommandHistoryRecord(this);
        if (worker.countToCompare() < request.getReceiver().getMinimumValue()) {
            try {
                request.getReceiver().addWorker(worker, request.getCard().getUserId());
                return new Response("done");
            } catch (Exception e) {
                return new Response("error: " + e.getMessage());
            }
        }
        return new Response("too big");
    }


    @Override
    public String getHelpText() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String getHelpName() {
        return "add_if_min {element}";
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
