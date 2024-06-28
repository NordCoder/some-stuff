package commands;

import shell.Receiver;

import java.util.List;

public class Help implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.printHelp();
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "вывести справку по доступным командам";
    }

    @Override
    public String get_help_name() {
        return "help";
    }
}
