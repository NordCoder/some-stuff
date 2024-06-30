package client;

import common.util.AccountCard;
import common.util.BadInputException;

import java.util.NoSuchElementException;

public class Client {
    private AccountCard accountCard;
    private ClientConnectionManager connectionManager;
    private CommandSender sender;
    private ResponseReceiver responseReceiver;

    public Client() {
        this.accountCard = new AccountCard();
        this.connectionManager = new ClientConnectionManager(this);
        this.sender = new CommandSender(this);
        this.responseReceiver = new ResponseReceiver(this);

        runMainLoop();
    }

    public void runMainLoop() {
        while (true) {
            System.out.print(">>>");
            try {
                sender.sendNextCommand();
                responseReceiver.handleResponse(responseReceiver.getResponse());
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public AccountCard getAccountCard() {
        return accountCard;
    }

    public ClientConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
