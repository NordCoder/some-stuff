package commands;

import shell.Receiver;

import java.util.List;

public class PrintAscending implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.printAscending();
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "вывести элементы коллекции в порядке возрастания";
    }

    @Override
    public String get_help_name() {
        return "print_ascending";
    }
}
