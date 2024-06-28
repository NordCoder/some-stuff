package commands;

import shell.Receiver;

import java.util.List;

public class PrintFieldAscendingPerson implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.printFieldAscendingPerson();
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "вывести значения поля person всех элементов в порядке возрастания";
    }

    @Override
    public String get_help_name() {
        return "print_field_ascending_person";
    }
}
