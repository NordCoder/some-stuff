package commands;

import entity.Worker;
import shell.Receiver;

import java.util.List;

public class AddIfMin implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        Worker toAdd = receiver.readWorker();
        if (toAdd.countToCompare() < receiver.getMinimumValue()) {
            receiver.addWorker(toAdd);
        }
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String get_help_name() {
        return "add_if_min {element}";
    }
}
