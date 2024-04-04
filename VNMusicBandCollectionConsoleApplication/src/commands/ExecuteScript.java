package commands;

import shell.Shell;
import util.CommandScanner;

import java.io.File;

public class ExecuteScript implements Command {
    private String path;

    public ExecuteScript(String path) {
        this.path = path;
    }

    @Override
    public void execute(Shell shell) {
        try {
            File file = new File(path);
            CommandScanner scanner = new CommandScanner(file);
            while (scanner.hasNextS()) {
                Command command = scanner.readFileCommand();
                command.execute(shell);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
