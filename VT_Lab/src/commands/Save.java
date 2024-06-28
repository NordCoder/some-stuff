package commands;

import shell.Receiver;

import java.util.List;

public class Save implements Command{
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.saveToJson();
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "сохранить коллекцию в файл";
    }

    @Override
    public String get_help_name() {
        return "save";
    }
}
