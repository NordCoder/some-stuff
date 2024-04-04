package commands;

import shell.Shell;
import util.Util;

public class Save implements Command {
    @Override
    public void execute(Shell shell) {
        Util.saveJson(shell.getManager().getCollection());
    }
}
