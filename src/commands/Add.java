package commands;

import entity.Worker;
import shell.Receiver;

import java.util.List;

public class Add implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        Worker toAdd = receiver.readWorker();
        receiver.addWorker(toAdd);
        receiver.addCommandHistoryRecord(this);
    }


    @Override
    public String get_help_text() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public String get_help_name() {
        return "add {element}";
    }
}
