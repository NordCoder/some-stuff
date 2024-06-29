package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.util.List;

public class UpdateById implements SpecialCommand {
    private Worker worker;
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        try {
            long id = Long.parseLong(args.get(0));
            receiver.replaceWorkerById(id, worker);
            receiver.addCommandHistoryRecord(this);
            return new Response("done");
        } catch (Exception e) {
            return new Response("id must be a number");
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
