package server;

import common.commands.Command;
import common.commands.Request;
import common.commands.Response;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;


public class QueryHandler {
    private Serializer<CustomPair<Command, Request>> commandSerializer = new Serializer<>();
    private Server parent;

    public QueryHandler(Server parent) {
        this.parent = parent;
    }

    public Response executeCommandFromBuffer() throws IOException, ClassNotFoundException {
        getBuffer().flip();
        CustomPair<Command, Request> command = commandSerializer.deserialize(getBuffer().array());
        command.getSecond().setReceiver(parent.getReceiver());
        Response response = command.getFirst().execute(command.getSecond());
        System.out.println("executed command: " + command.getFirst());
        getBuffer().clear();
        return response;
    }

    public CustomPair<DatagramChannel, InetSocketAddress> getClientDataAndFillBuffer(SelectionKey key) throws IOException {
        DatagramChannel dataChannel = (DatagramChannel) key.channel();
        getBuffer().clear();
        InetSocketAddress clientAddress = (InetSocketAddress) dataChannel.receive(getBuffer());
        return new CustomPair<>(dataChannel, clientAddress);
    }

    private ByteBuffer getBuffer() {
        return parent.getConnectionManager().getBuffer();
    }
}
