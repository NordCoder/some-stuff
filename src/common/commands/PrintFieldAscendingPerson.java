package common.commands;

import server.Receiver;

import java.util.List;

public class PrintFieldAscendingPerson implements Command {
    @Override
    public Response execute(Request request) {
        String persons = request.getReceiver().getFieldAscendingPerson();
        request.getReceiver().addCommandHistoryRecord(this);
        return new Response(persons);
    }

    @Override
    public String getHelpText() {
        return "вывести значения поля person всех элементов в порядке возрастания";
    }

    @Override
    public String getHelpName() {
        return "print_field_ascending_person";
    }
}
