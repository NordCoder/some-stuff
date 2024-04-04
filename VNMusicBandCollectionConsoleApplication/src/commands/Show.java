package commands;

import entity.MusicBand;
import shell.Shell;

public class Show implements Command {
    @Override
    public void execute(Shell shell) {
        if (shell.getManager().getCollection().isEmpty()) {
            System.out.println("Collection's empty");
        }
        System.out.println(shell.getManager().getCollection());
    }
}
