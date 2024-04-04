package commands;

import entity.MusicBand;
import shell.Shell;

import static util.Util.readMusicBand;

public class InsertAt implements Command {
    private int index;
    private String arg;

    public InsertAt(int index, String arg) {
        this.index = index;
        this.arg = arg;
    }

    @Override
    public void execute(Shell shell) {
        MusicBand result = readMusicBand();
        shell.getManager().insert(index, result);
    }
}
