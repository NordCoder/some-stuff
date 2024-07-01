package common.commands;

import common.entity.Person;
import common.entity.Worker;
import server.Receiver;

import java.lang.module.ResolutionException;
import java.sql.SQLException;
import java.util.List;

import static common.util.Util.checkAuthorization;

public class RemoveAnyByPerson implements SpecialCommand {
    private Person person;
    @Override
    public Response execute(Request request) {
        if (!checkAuthorization(request.getCard())) {
            return new Response("Log in to remove");
        }
        try {
            boolean check = request.getReceiver().removeAnyByPerson(person, request.getCard());
            request.getReceiver().addCommandHistoryRecord(this);
            return new Response(check ? "done" : "there's no worker with such person or you are not allowed to delete");
        } catch (SQLException e) {
            return new Response("failed to remove worker from database: " + e.getMessage());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
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
