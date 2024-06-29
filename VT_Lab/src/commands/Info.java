package commands;

import shell.Receiver;

import java.util.List;

public class Info implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.printInfo();
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public String get_help_name() {
        return "info";
    }
}
