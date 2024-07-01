package common.commands;

import common.entity.Person;
import common.entity.Worker;

import static common.util.Util.checkAuthorization;

public class AddIfMax extends AbstractNeedToRegisterCommand implements SpecialCommand {
    private Worker worker;

    @Override
    public Response executeCommand(Request request) {
        if (worker.countToCompare() > request.getReceiver().getMaximumValue()) {
            return request.getReceiver().addWorker(worker, request.getCard().getUserId());
        }
        return new Response("too small :(");
    }

    @Override
    public String getCommandDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String getCommandName() {
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
