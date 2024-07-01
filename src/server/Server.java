package server;

import common.commands.Command;
import common.commands.Request;
import common.commands.Response;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.AccountCard;
import common.util.BadInputException;
import common.util.CustomPair;
import server.db.DatabaseConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

import static common.util.Util.*;
import static server.db.DatabaseOperations.getAllWorkersFromDB;

public class Server {
    private ConnectionManager connectionManager;
    private QueryHandler queryHandler;
    private Responder responder;


    private Receiver receiver;
    private AccountCard accountCard;


    private ExecutorService connectionThreadPool;
    private ExecutorService queryThreadPool;
    private ExecutorService responderThreadPool;

    public Server() {
        init();
        startSeverConsoleThread();
        runServer();
    }

    private void init() {
        initCollection();
        connectionManager = new ConnectionManager();
        queryHandler = new QueryHandler(this);
        responder = new Responder(this);
        accountCard = new AccountCard();

        connectionThreadPool = Executors.newCachedThreadPool();
        queryThreadPool = Executors.newCachedThreadPool();
        responderThreadPool = Executors.newFixedThreadPool(5);
    }

    private void runServer() {
        while (true) {
            Future<SelectionKey> futureKey = connectionThreadPool.submit(new Callable<SelectionKey>() {
                @Override
                public SelectionKey call() throws IOException {
                    return connectionManager.getNextSelectionKey();
                }
            });
            Future<CustomPair<CustomPair<DatagramChannel, InetSocketAddress>, Response>> futureData =
                    queryThreadPool.submit(new Callable<CustomPair<CustomPair<DatagramChannel, InetSocketAddress>, Response>>() {
                        @Override
                        public CustomPair<CustomPair<DatagramChannel, InetSocketAddress>, Response> call() {
                            SelectionKey key = null;
                            try {
                                key = futureKey.get();
                            } catch (InterruptedException e) {
                                System.out.println("interrupted exception, can't get key");
                            } catch (ExecutionException e) {
                                System.out.println("execution exception, can't get key");
                            }
                            CustomPair<DatagramChannel, InetSocketAddress> clientData = queryHandler.getClientDataAndFillBuffer(key);
                            Response response = queryHandler.executeCommandFromBuffer();
                            return new CustomPair<>(clientData, response);
                        }
                    });
            CustomPair<CustomPair<DatagramChannel, InetSocketAddress>, Response> queryExecutorData = null;
            try {
                queryExecutorData = futureData.get();
            } catch (InterruptedException e) {
                System.out.println("interrupted exception, can't get key");
            } catch (ExecutionException e) {
                System.out.println("execution exception, can't get key");
            }
            if (queryExecutorData != null) {
                CustomPair<CustomPair<DatagramChannel, InetSocketAddress>, Response> finalQueryExecutorData = queryExecutorData;
                responderThreadPool.submit(() -> responder.respondToClient(finalQueryExecutorData.getFirst(), finalQueryExecutorData.getSecond()));
            }
        }
    }

    private void initCollection() {
        receiver = new Receiver();
        try {
            receiver.setCollection(getAllWorkersFromDB(DatabaseConnection.getConnection()));
        } catch (SQLException e) {
            System.out.println("failed to load data from database: " + e.getMessage());
        }
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
                CustomPair<Command, Request> command = readHandleCommand(commandParser, accountCard);
                command.getSecond().setReceiver(receiver);
                queryHandler.handleServerConsoleCommand(command);
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

    public AccountCard getAccountCard() {
        return accountCard;
    }
}
