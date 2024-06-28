package commands;

import shell.Receiver;

import java.util.List;

public class ExecuteScript implements Command {
    @Override
    public void execute(Receiver receiver, List<String> args) {
        receiver.executeScript(args.get(0));
        receiver.addCommandHistoryRecord(this);
    }

    @Override
    public String get_help_text() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public String get_help_name() {
        return "execute_script file_name";
    }
}
