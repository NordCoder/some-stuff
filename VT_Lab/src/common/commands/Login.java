package common.commands;

import server.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static common.util.Util.generateSHA512Hash;
import static server.db.DatabaseOperations.authenticateUserCheck;
import static client.ResponseReceiver.LoggingIn.*;
import client.ResponseReceiver.LoggingIn;

public class Login implements Command {
    @Override
    public Response execute(Request request) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            LoggingIn accountExists = authenticateUserCheck(connection, request.getArgs().get(0), generateSHA512Hash(request.getArgs().get(1)));
            Response response = new Response(accountExists == EXISTS ? "Successfully logged in" : "Invalid username or password");
            response.setLoginStatus(accountExists);
            return response;
        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public String getHelpText() {
        return "log in the system";
    }

    @Override
    public String getHelpName() {
        return "login";
    }
}
