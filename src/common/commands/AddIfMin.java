package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.util.List;

public class AddIfMin implements SpecialCommand {
    private Worker worker = null;
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        if (worker.countToCompare() < receiver.getMinimumValue()) {
            receiver.addWorker(worker);
        }
        receiver.addCommandHistoryRecord(this);
        return new Response("done");
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
