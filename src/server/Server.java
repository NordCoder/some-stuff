package server;

import common.commands.Command;
import common.commands.Response;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.BadInputException;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static common.util.Util.getServerCommands;
import static common.util.Util.handleCommandsWithAdditionalInfo;
import static server.Saving.loadFromJson;

public class Server { // todo handle exceptions
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 8192;
    private static Selector selector;
    private static DatagramChannel channel;
    private static ByteBuffer buffer;
    private static Receiver receiver;
    private static Serializer<CustomPair<Command, String[]>> commandSerializer = new Serializer<>();
    private static Serializer<Response> responseSerializer = new Serializer<>();

    private static void init() throws IOException {
        initCollection();
        initConnection();
    }

    private static void runMainLoop() throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handleQuery(key);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        new Thread(Server::runServerConsole).start();
        runMainLoop();
    }

    private static void handleQuery(SelectionKey key) throws IOException {
        if (key.isReadable()) {
            CustomPair<DatagramChannel, InetSocketAddress> clientData = getClientData(key);
            try {
                Response response = executeCommandFromBuffer();
                respondToClient(clientData, response);
            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private static void initCollection() {
        receiver = new Receiver();
        loadFromJson(receiver);
    }

    private static void initConnection() throws IOException {
        selector = Selector.open();
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(PORT));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    private static CustomPair<DatagramChannel, InetSocketAddress> getClientData(SelectionKey key) throws IOException {
        DatagramChannel dataChannel = (DatagramChannel) key.channel();
        buffer.clear();
        InetSocketAddress clientAddress = (InetSocketAddress) dataChannel.receive(buffer);
        return new CustomPair<>(dataChannel, clientAddress);
    }

    private static Response executeCommandFromBuffer() throws IOException, ClassNotFoundException {
        buffer.flip();
        CustomPair<Command, String[]> command = commandSerializer.deserialize(buffer.array());
        Response response = command.getFirst().execute(receiver, Arrays.asList(command.getSecond()));
        System.out.println("executed command: " + command.getFirst());
        buffer.clear();
        return response;
    }

    private static void respondToClient(CustomPair<DatagramChannel, InetSocketAddress> clientData, Response response) throws IOException {
        buffer.put(responseSerializer.serialize(response));
        buffer.flip();
        clientData.getFirst().send(buffer, clientData.getSecond());
        buffer.clear();
    }

    private static void runServerConsole() {
        InputParser commandParser = new InputParser(new ConsoleInputGetter(), getServerCommands());
        while (true) {
            System.out.print(">>>");
            try {
                CustomPair<Command, String[]> command = commandParser.nextCommand();
                handleCommandsWithAdditionalInfo(command.getFirst());
                Response response = command.getFirst().execute(receiver, Arrays.asList(command.getSecond()));
                System.out.println(response.getText());
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
