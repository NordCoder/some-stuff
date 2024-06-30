package common.commands;

import common.entity.Worker;
import server.Receiver;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Clear implements Command {
    @Override
    public Response execute(Request request) {
        request.getReceiver().setCollection(new TreeSet<>(Comparator.comparingDouble(Worker::countToCompare)));
        request.getReceiver().addCommandHistoryRecord(this);
        return new Response("Cleared!");
    }

    @Override
    public String getHelpText() {
        return "очистить коллекцию";
    }

    @Override
    public String getHelpName() {
        return "clear";
    }
}
