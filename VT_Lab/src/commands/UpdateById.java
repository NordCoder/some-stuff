package commands;

import entity.Worker;
import shell.Receiver;

import java.util.List;

public class UpdateById implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        try {
            long id = Long.parseLong(args.get(0));
            Worker toAdd = receiver.readWorker();
            receiver.replaceWorkerById(id, toAdd);
            receiver.addCommandHistoryRecord(this);
        } catch (Exception e) {
            System.err.println("id must be a number");
        }
    }

    @Override
    public String get_help_text() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String get_help_name() {
        return "update id {element}";
    }
}
