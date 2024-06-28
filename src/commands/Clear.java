package commands;

import entity.Worker;
import shell.Receiver;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Clear implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.setCollection(new TreeSet<>(Comparator.comparingDouble(Worker::countToCompare)));
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "очистить коллекцию";
    }

    @Override
    public String get_help_name() {
        return "clear";
    }
}
