package commands;

import shell.Shell;

public class Info implements Command {
    @Override
    public void execute(Shell shell) {
        System.out.println("type = MusicBand");
        System.out.println("creation time = " + shell.getManager().getCreationDate().toString());
        System.out.println("size = " + shell.getManager().getCollection().size());
    }
}
