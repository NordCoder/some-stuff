package common.commands;

import common.entity.Worker;
import server.db.DatabaseConnection;

import java.util.Comparator;
import java.util.TreeSet;

import static common.util.Util.checkAuthorization;
import static server.db.DatabaseOperations.isSuperuser;

public class Clear implements Command {
    @Override
    public Response execute(Request request) {
        try {
            if (!checkAuthorization(request.getCard()) || !isSuperuser(DatabaseConnection.getConnection(), request.getCard().getUserId())) {
                return new Response("Log in to create records");
            }
            request.getReceiver().clear();
            request.getReceiver().setCollection(new TreeSet<>(Comparator.comparingDouble(Worker::countToCompare)));
            request.getReceiver().addCommandHistoryRecord(this);
            return new Response("Cleared!");
        } catch (Exception e) {
            return new Response("failed to clear database" + e.getMessage());
        }
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
