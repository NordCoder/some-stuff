package commands;

import shell.Shell;

import static util.Util.readMusicBand;

public class Add implements Command {
    private final String arg;

    public Add(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(Shell shell) {
        shell.getManager().add(readMusicBand(), arg);
    }
}
