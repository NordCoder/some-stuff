package commands;

import shell.Shell;

public class PrintDescending implements Command {
    @Override
    public void execute(Shell shell) {
        for (int i = shell.getManager().getCollection().size() - 1; i >= 0; i--) {
            if (shell.getManager().getCollection().get(i) != null) {
                System.out.println(shell.getManager().getCollection().get(i));
            }
        }
    }
}
