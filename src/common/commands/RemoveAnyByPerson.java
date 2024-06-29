package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.util.List;

public class RemoveAnyByPerson implements SpecialCommand {
    private Person person;
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        receiver.removeAnyByPerson(person);
        receiver.addCommandHistoryRecord(this);
        return new Response("done");
    }


    @Override
    public String getHelpText() {
        return "удалить из коллекции один элемент, значение поля person которого эквивалентно заданному";
    }

    @Override
    public String getHelpName() {
        return "remove_any_by_person person";
    }

    @Override
    public void setToAdd(Worker w) {

    }

    @Override
    public void setToAdd(Person p) {
        person = p;
    }

    @Override
    public boolean needsWorker() {
        return false;
    }
}
