package commands;

import shell.Receiver;

import java.util.List;

public class Show implements Command{
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.printAscending();
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String get_help_name() {
        return "show";
    }
}
