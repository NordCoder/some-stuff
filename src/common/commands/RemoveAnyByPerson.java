package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.lang.module.ResolutionException;
import java.sql.SQLException;
import java.util.List;

import static common.util.Util.checkAuthorization;

public class RemoveAnyByPerson extends AbstractNeedToRegisterCommand implements SpecialCommand {
    private Person person;

    @Override
    public Response executeCommand(Request request) {
        return request.getReceiver().removeAnyByPerson(person, request.getCard());
    }

    @Override
    public String getCommandDescription() {
        return "удалить из коллекции один элемент, значение поля person которого эквивалентно заданному";
    }

    @Override
    public String getCommandName() {
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
