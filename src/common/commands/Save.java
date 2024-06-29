package common.commands;

import server.Receiver;

import java.util.List;

public class Save implements Command{
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        receiver.saveToJson();
        receiver.addCommandHistoryRecord(this);
        return new Response("saved!");
    }

    @Override
    public String getHelpText() {
        return "сохранить коллекцию в файл";
    }

    @Override
    public String getHelpName() {
        return "save";
    }
}
