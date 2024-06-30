package common.commands;

import server.Receiver;

import java.util.List;

public class Save implements Command{
    @Override
    public Response execute(Request request) {
        boolean check = request.getReceiver().saveToJson();
        request.getReceiver().addCommandHistoryRecord(this);
        return new Response(check ? "saved!" : "not saved :(");
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
