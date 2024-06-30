package common.commands;

import server.Receiver;

import java.util.List;

public class ExecuteScript implements Command {
    @Override
    public Response execute(Request request) {
        boolean check = request.getReceiver().executeScript(request.getArgs().get(0));
        request.getReceiver().addCommandHistoryRecord(this);
        return new Response(check ? "Executed!" : "bad script");
    }

    @Override
    public String getHelpText() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public String getHelpName() {
        return "execute_script file_name";
    }
}
