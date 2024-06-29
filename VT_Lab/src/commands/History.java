package commands;

import shell.Receiver;

import java.util.List;

public class History implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.printHistory();
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "вывести последние 14 команд (без их аргументов)";
    }

    @Override
    public String get_help_name() {
        return "history";
    }
}
