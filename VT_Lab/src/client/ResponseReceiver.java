package client;

import common.commands.Response;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static common.util.Util.handleLoginCommand;


public class ResponseReceiver {
    private final Serializer<Response> responseSerializer = new Serializer<>();
    private final Client parent;

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
        handleLoginCommand(response, parent.getAccountCard());
        System.out.println(response.getText());
    }
}
