package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.util.List;

public class AddIfMax implements SpecialCommand {
    private Worker worker;
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        if (worker.countToCompare() > receiver.getMaximumValue()) {
            receiver.addWorker(worker);
        }
        receiver.addCommandHistoryRecord(this);
        return new Response("done");
    }


    @Override
    public String getHelpText() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String getHelpName() {
        return "add_if_max {element}";
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
