package client;

import common.util.AccountCard;
import common.util.BadInputException;

import java.util.NoSuchElementException;

import static client.ClientConnectionManager.createConnection;
import static client.CommandSender.sendNextCommand;
import static client.ResponseReceiver.getResponse;
import static client.ResponseReceiver.handleResponse;


public class Client {
    private static AccountCard accountCard = new AccountCard();

    public static void runMainLoop() {
        while (true) {
            System.out.print(">>>");
            try {
                sendNextCommand();
                handleResponse(getResponse());
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        createConnection();
        runMainLoop();
    }

    public static AccountCard getAccountCard() {
        return accountCard;
    }
}
