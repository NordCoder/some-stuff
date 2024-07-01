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

import static common.util.Util.handleLoginCommand;


public class QueryHandler {
    private final Serializer<CustomPair<Command, Request>> commandSerializer = new Serializer<>();
    private final Server parent;

    public QueryHandler(Server parent) {
        this.parent = parent;
    }

    public Response executeCommandFromBuffer() {
        try {
            getBuffer().flip();
            CustomPair<Command, Request> command = commandSerializer.deserialize(getBuffer().array());
            command.getSecond().setReceiver(parent.getReceiver());
            Response response = command.getFirst().execute(command.getSecond());
            System.out.println("executed command: " + command.getFirst());
            getBuffer().clear();
            return response;
        } catch (Exception e) {
            System.out.println("failed to execute command: " + e.getMessage());
            return new Response("failed to execute command: " + e.getMessage());
        }

    }

    public CustomPair<DatagramChannel, InetSocketAddress> getClientDataAndFillBuffer(SelectionKey key) {
        try {
            DatagramChannel dataChannel = (DatagramChannel) key.channel();
            getBuffer().clear();
            InetSocketAddress clientAddress = (InetSocketAddress) dataChannel.receive(getBuffer());
            return new CustomPair<>(dataChannel, clientAddress);
        } catch (IOException e) {
            System.out.println("failed to recieve command: " + e.getMessage());
            return null;
        }

    }

    private ByteBuffer getBuffer() {
        return parent.getConnectionManager().getBuffer();
    }

    public void handleServerConsoleCommand(CustomPair<Command, Request> command) {
        Response response = command.getFirst().execute(command.getSecond());
        handleLoginCommand(response, parent.getAccountCard());
        System.out.println(response.getText());
    }
}
