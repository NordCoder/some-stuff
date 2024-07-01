package server;

import common.commands.Response;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Responder {
    private final Serializer<Response> responseSerializer = new Serializer<>();
    private Server parent;

    public Responder(Server parent) {
        this.parent = parent;
    }

    public void respondToClient(CustomPair<DatagramChannel, InetSocketAddress> clientData, Response response) {
        try {
            getBuffer().put(responseSerializer.serialize(response));
            getBuffer().flip();
            clientData.getFirst().send(getBuffer(), clientData.getSecond());
            getBuffer().clear();
        } catch (IOException e) {
            System.out.println("failed to send response to client: " + e.getMessage());
        }
    }

    private ByteBuffer getBuffer() {
        return parent.getConnectionManager().getBuffer();
    }


}
