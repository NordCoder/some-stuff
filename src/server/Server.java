package server;

import common.commands.Command;
import common.commands.Request;
import common.commands.Response;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.AccountCard;
import common.util.BadInputException;
import common.util.CustomPair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static common.util.Util.*;
import static server.Saving.loadFromJson;

public class Server {
    private ConnectionManager connectionManager;
    private QueryHandler queryHandler;
    private Responder responder;


    private Receiver receiver;
    private AccountCard card;

    public Server() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> receiver.saveToJson()));

        init();
        startSeverConsoleThread();
        runServer();
    }

    private void init() {
        initCollection();
        connectionManager = new ConnectionManager();
        queryHandler = new QueryHandler(this);
        responder = new Responder(this);
    }

    private void runServer() {
        while (true) {
            try {
                SelectionKey key = connectionManager.getNextSelectionKey();
                CustomPair<DatagramChannel, InetSocketAddress> clientData = queryHandler.getClientDataAndFillBuffer(key);
                Response response = queryHandler.executeCommandFromBuffer();
                responder.respondToClient(clientData, response);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server error: " + e.getMessage());
            }
        }
    }

    private void initCollection() {
        receiver = new Receiver();
        loadFromJson(receiver);
    }

    private void startSeverConsoleThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runServerConsole();
            }
        }).start();
    }

    private void runServerConsole() {
        InputParser commandParser = new InputParser(new ConsoleInputGetter(), getServerCommands());
        while (true) {
            System.out.print(">>>");
            try {
                CustomPair<Command, Request> command = readHandleCommand(commandParser, card);
                command.getSecond().setReceiver(receiver);
                Response response = command.getFirst().execute(command.getSecond());
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

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
