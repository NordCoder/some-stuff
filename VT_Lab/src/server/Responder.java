package server;

import common.commands.Response;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

import static server.Server.getBuffer;

public class Responder {
    private static final Serializer<Response> responseSerializer = new Serializer<>();

    public static void respondToClient(CustomPair<DatagramChannel, InetSocketAddress> clientData, Response response) throws IOException {
        getBuffer().put(responseSerializer.serialize(response));
        getBuffer().flip();
        clientData.getFirst().send(getBuffer(), clientData.getSecond());
        getBuffer().clear();
    }


}
