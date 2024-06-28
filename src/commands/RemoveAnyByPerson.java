package commands;

import entity.Person;
import shell.Receiver;

import java.util.List;

public class RemoveAnyByPerson implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        Person person = receiver.readPerson();
        receiver.removeAnyByPerson(person);
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "удалить из коллекции один элемент, значение поля person которого эквивалентно заданному";
    }

    @Override
    public String get_help_name() {
        return "remove_any_by_person person";
    }
}
