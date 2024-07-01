package common.commands;

import common.entity.Worker;
import common.util.NotAllowedToUseCommandException;
import server.db.DatabaseConnection;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.TreeSet;

import static common.util.Util.checkAuthorization;
import static server.db.DatabaseOperations.isSuperuser;

public class Clear extends AbstractCommand {
    @Override
    protected Response executeCommand(Request request) {
        return request.getReceiver().clear();
    }

    @Override
    protected void allowedToExecute(Request request) throws NotAllowedToUseCommandException {
        try {
            if (!(checkAuthorization(request.getCard()) || !isSuperuser(DatabaseConnection.getConnection(), request.getCard().getUserId()))) {
                throw new NotAllowedToUseCommandException("Only superusers can execute this command");
            }
        } catch (SQLException e) {
            throw new NotAllowedToUseCommandException("unable to check allowance: " + e.getMessage());
        }
    }

    @Override
    public String getCommandDescription() {
        return "очистить коллекцию";
    }

    @Override
    public String getCommandName() {
        return "clear";
    }
}
