package common.commands;

import common.util.AccountCard;
import server.Receiver;

import java.util.List;

public class Show implements Command{
    @Override
    public Response execute(Request request) {
        String result = "You are " + (request.getCard().getStatus() == AccountCard.Authorization.AUTHORIZED ?
                request.getCard().getUsername() :
                "unauthorized") + System.lineSeparator();
        result += request.getReceiver().show();
        request.getReceiver().addCommandHistoryRecord(this);
        return new Response(result);
    }

    @Override
    public String getHelpText() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String getHelpName() {
        return "show";
    }
}
