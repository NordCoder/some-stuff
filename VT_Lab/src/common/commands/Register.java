package common.commands;

import server.db.DatabaseConnection;
import server.db.DatabaseOperations;

import java.sql.Connection;
import java.sql.SQLException;

import static common.util.Util.generateSHA512Hash;

public class Register implements Command {
    @Override
    public Response execute(Request request) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String passwordHash = generateSHA512Hash(request.getArgs().get(1));
            DatabaseOperations.addUser(connection, request.getArgs().get(0), passwordHash);
        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response("ok");
    }

    @Override
    public String getHelpText() {
        return "create an account in system";
    }

    @Override
    public String getHelpName() {
        return "register";
    }
}
