package client;

import common.commands.Response;
import common.util.AccountCard;
import common.util.Serializer;
import server.db.DatabaseConnection;
import server.db.DatabaseOperations;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;


public class ResponseReceiver {
    private Serializer<Response> responseSerializer = new Serializer<>();
    private Client parent;

    public ResponseReceiver(Client parent) {
        this.parent = parent;
    }

    public Response getResponse() throws IOException, ClassNotFoundException {
        long timer = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - timer > 10000) {
                System.out.println("Server's unreachable. Try again later.");
                break;
            }
            InetSocketAddress responseAddress = (InetSocketAddress)
                    parent.getConnectionManager().getChannel().receive(parent.getConnectionManager().getBuffer());
            if (responseAddress != null) {
                parent.getConnectionManager().getBuffer().flip();
                Response response = responseSerializer.deserialize(parent.getConnectionManager().getBuffer().array());
                parent.getConnectionManager().getBuffer().clear();
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

    public void handleResponse(Response response) {
        if (response.getLoginVerificationFlag() == LoggingIn.EXISTS) {
            try {
                parent.getAccountCard().setUserId(DatabaseOperations.getUserIdByUsername(DatabaseConnection.getConnection(), parent.getAccountCard().getUsername()));
                parent.getAccountCard().setStatus(AccountCard.Authorization.AUTHORIZED);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (response.getLoginVerificationFlag() == LoggingIn.DOES_NOT_EXIST) {
            parent.getAccountCard().setUsername("");
            parent.getAccountCard().setStatus(AccountCard.Authorization.UNAUTHORIZED);
            parent.getAccountCard().setUserId(-1);
        }
        System.out.println(response.getText());
    }
}
