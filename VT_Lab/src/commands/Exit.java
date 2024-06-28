package commands;

import shell.Receiver;

import java.util.List;

public class Exit implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.addCommandHistoryRecord(this);
        System.exit(0);
    }

    @Override
    public String get_help_text() {
        return "завершить программу (без сохранения в файл)";
    }

    @Override
    public String get_help_name() {
        return "exit";
    }
}
