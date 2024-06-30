package common.commands;

import server.Receiver;

import java.util.List;

public class Info implements Command {
    @Override
    public Response execute(Request request) {
        String info = request.getReceiver().getInfo();
        request.getReceiver().addCommandHistoryRecord(this);
        return new Response(info);
    }

    @Override
    public String getHelpText() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public String getHelpName() {
        return "info";
    }
}
