package commands;

import shell.Receiver;

import java.util.List;

public class RemoveById implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        try {
            long id = Long.parseLong(args.get(0));
            receiver.removeWorkerById(id);
            receiver.addCommandHistoryRecord(this);
        } catch (Exception e) {
            System.err.println("id must be a number");
        }
    }

    @Override
    public String get_help_text() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public String get_help_name() {
        return "remove_by_id id";
    }
}
