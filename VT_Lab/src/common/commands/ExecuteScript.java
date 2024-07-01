package common.commands;

import server.Receiver;

import java.util.List;

import static common.util.Util.checkAuthorization;

public class ExecuteScript extends AbstractCommand {
    @Override
    protected Response executeCommand(Request request) {
        boolean check = request.getReceiver().executeScript(request.getArgs().get(0), request.getCard());
        return new Response(check ? "Executed!" : "bad script");
    }

    @Override
    protected void allowedToExecute(Request request) {
    }

    @Override
    public String getCommandDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public String getCommandName() {
        return "execute_script file_name";
    }
}
