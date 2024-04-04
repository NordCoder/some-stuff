package commands;

import shell.Shell;

public class Exit implements Command {
    @Override
    public void execute(Shell shell) {
        shell.setExitFlag(false);
    }
}
