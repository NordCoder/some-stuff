package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.util.List;

public class Add implements SpecialCommand {
    private Worker worker = null;
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        boolean check = receiver.addWorker(worker);
        receiver.addCommandHistoryRecord(this);
        return new Response(check ? "done" : "already contains");
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
