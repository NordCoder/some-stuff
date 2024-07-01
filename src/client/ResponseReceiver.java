package client;

import common.commands.Response;
import common.util.Serializer;
import common.util.Timer;

import java.io.IOException;


public class ResponseReceiver {
    private final Serializer<Response> responseSerializer;
    private final ClientConnectionManager connectionManager;

    public ResponseReceiver(ClientConnectionManager connectionManager) {
        this.responseSerializer = new Serializer<>();
        this.connectionManager = connectionManager;
    }

    public Response getResponse() {
        Timer timer = new Timer(10000);
        while (!timer.timeIsOut()) {
            if (connectionManager.responseReceived())
                return getAndProccessResponse();
        }
        return new Response("server's unreachable. try again later");
    }

    private Response getAndProccessResponse() {
        try {
            connectionManager.flipBuffer();
            Response response = responseSerializer.deserialize(connectionManager.getByteArray());
            connectionManager.clearBuffer();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            return new Response("unable to decode response: " + e.getMessage());
        }
    }
}
