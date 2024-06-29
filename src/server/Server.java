package server;

import common.commands.Command;
import common.commands.Response;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.BadInputException;
import common.util.CustomPair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static common.util.Util.getServerCommands;
import static common.util.Util.handleCommandsWithAdditionalInfo;
import static server.QueryHandler.executeCommandFromBuffer;
import static server.QueryHandler.getClientDataAndFillBuffer;
import static server.Responder.respondToClient;
import static server.Saving.loadFromJson;

public class Server {
    private static ConnectionManager connectionManager;
    private static ByteBuffer buffer;
    private static Receiver receiver;


    private static void init() throws IOException {
        initCollection();
        connectionManager = new ConnectionManager();
        buffer = ByteBuffer.allocate(8192);
    }

    private static void runServer() throws IOException, ClassNotFoundException {
        while (true) {
            SelectionKey key = connectionManager.getNextSelectionKey();
            CustomPair<DatagramChannel, InetSocketAddress> clientData = getClientDataAndFillBuffer(key);
            Response response = executeCommandFromBuffer();
            respondToClient(clientData, response);
        }
    }

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> receiver.saveToJson()));

        try {
            init();
        } catch (IOException e) {
            System.out.println("unable to start server: " + e.getMessage());
        }

        new Thread(Server::runServerConsole).start();

        try {
            runServer();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void initCollection() {
        receiver = new Receiver();
        loadFromJson(receiver);
    }

    private static void runServerConsole() {
        InputParser commandParser = new InputParser(new ConsoleInputGetter(), getServerCommands());
        while (true) {
            System.out.print(">>>");
            try {
                CustomPair<Command, String[]> command = commandParser.nextCommand();
                handleCommandsWithAdditionalInfo(command.getFirst(), new InputParser(new ConsoleInputGetter(), getServerCommands()));
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

    public static ByteBuffer getBuffer() {
        return buffer;
    }

    public static Receiver getReceiver() {
        return receiver;
    }
}
