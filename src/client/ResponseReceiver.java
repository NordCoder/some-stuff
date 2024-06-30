package client;

import common.commands.Response;
import common.util.AccountCard;
import common.util.Serializer;
import server.db.DatabaseConnection;
import server.db.DatabaseOperations;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import static client.ClientConnectionManager.getBuffer;
import static client.ClientConnectionManager.getChannel;

public class ResponseReceiver {
    private static Serializer<Response> responseSerializer = new Serializer<>();
    public static Response getResponse() throws IOException, ClassNotFoundException {
        long timer = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - timer > 10000) {
                System.out.println("Server's unreachable. Try again later.");
                break;
            }
            InetSocketAddress responseAddress = (InetSocketAddress) getChannel().receive(getBuffer());
            if (responseAddress != null) {
                getBuffer().flip();
                Response response = responseSerializer.deserialize(getBuffer().array());
                getBuffer().clear();
                return response;
            }
        }
        return new Response("-1");
    }

    public enum LoggingIn {
        EXISTS,
        DOES_NOT_EXIST,
        NOT_USED
    }

    public static void handleResponse(Response response) {
        if (response.getLoginVerificationFlag() == LoggingIn.EXISTS) {
            try {
                Client.getAccountCard().setUserId(DatabaseOperations.getUserIdByUsername(DatabaseConnection.getConnection(), Client.getAccountCard().getUsername()));
                Client.getAccountCard().setStatus(AccountCard.Authorization.AUTHORIZED);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (response.getLoginVerificationFlag() == LoggingIn.DOES_NOT_EXIST) {
            Client.getAccountCard().setUsername("");
            Client.getAccountCard().setStatus(AccountCard.Authorization.UNAUTHORIZED);
            Client.getAccountCard().setUserId(-1);
        }
        System.out.println(response.getText());
    }
}
