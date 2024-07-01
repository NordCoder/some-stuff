package common.commands;

import common.entity.Person;
import common.entity.Worker;

public class Add extends AbstractNeedToRegisterCommand implements SpecialCommand {
    private Worker worker = null;
    @Override
    protected Response executeCommand(Request request) {
        return request.getReceiver().addWorker(worker, request.getCard().getUserId());
    }

    @Override
    public String getCommandDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public String getCommandName() {
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
