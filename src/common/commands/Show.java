package common.commands;

import common.util.AccountCard;
import server.Receiver;

import java.util.List;

public class Show extends AbstractCommand{
    @Override
    public Response executeCommand(Request request) {
        String result = "You are " + (request.getCard().getStatus() == AccountCard.Authorization.AUTHORIZED ?
                request.getCard().getUsername() :
                "unauthorized") + System.lineSeparator();
        result += request.getReceiver().show();
        return new Response(result);
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String getCommandName() {
        return "show";
    }
}
