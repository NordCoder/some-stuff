package common.commands;

import server.Receiver;

import java.util.List;

public class History implements Command {
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        String history = receiver.getHistory();
        receiver.addCommandHistoryRecord(this);
        return new Response(history);
    }

    @Override
    public String getHelpText() {
        return "вывести последние 14 команд (без их аргументов)";
    }

    @Override
    public String getHelpName() {
        return "history";
    }
}
