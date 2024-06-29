package common.commands;

import server.Receiver;

import java.util.List;

public class Info implements Command {
    @Override
    public Response execute(Receiver receiver, List<String> args) {
        String info = receiver.getInfo();
        receiver.addCommandHistoryRecord(this);
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
