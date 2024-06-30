package server;

import common.commands.Command;
import common.commands.Request;
import common.commands.Response;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.Arrays;

import static server.Server.getBuffer;
import static server.Server.getReceiver;

public class QueryHandler {
    private static Serializer<CustomPair<Command, Request>> commandSerializer = new Serializer<>();

    public static Response executeCommandFromBuffer() throws IOException, ClassNotFoundException {
        getBuffer().flip();
        CustomPair<Command, Request> command = commandSerializer.deserialize(getBuffer().array());
        command.getSecond().setReceiver(getReceiver());
        Response response = command.getFirst().execute(command.getSecond());
        System.out.println("executed command: " + command.getFirst());
        getBuffer().clear();
        return response;
    }

    public static CustomPair<DatagramChannel, InetSocketAddress> getClientDataAndFillBuffer(SelectionKey key) throws IOException {
        DatagramChannel dataChannel = (DatagramChannel) key.channel();
        getBuffer().clear();
        InetSocketAddress clientAddress = (InetSocketAddress) dataChannel.receive(getBuffer());
        return new CustomPair<>(dataChannel, clientAddress);
    }
}
