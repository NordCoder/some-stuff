package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.util.List;

public class AddIfMin implements SpecialCommand {
    private Worker worker = null;
    @Override
    public Response execute(Request request) {
        request.getReceiver().addCommandHistoryRecord(this);
        if (worker.countToCompare() < request.getReceiver().getMinimumValue()) {
            request.getReceiver().addWorker(worker);
            return new Response("done");
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
