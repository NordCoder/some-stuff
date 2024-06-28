package commands;

import entity.Worker;
import shell.Receiver;

import java.util.List;

public class AddIfMax implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        Worker toAdd = receiver.readWorker();
        if (toAdd.countToCompare() > receiver.getMaximumValue()) {
            receiver.addWorker(toAdd);
        }
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String get_help_name() {
        return "add_if_max {element}";
    }
}
