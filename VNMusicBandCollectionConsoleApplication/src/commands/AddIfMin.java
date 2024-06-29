package commands;

import entity.MusicBand;
import shell.Shell;

import static util.Util.readMusicBand;

public class AddIfMin implements Command {
    private final String arg;

    public AddIfMin(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(Shell shell) {
        MusicBand res = readMusicBand();
//        if (res.getValue() < shell.getManager().getMinCollectionValue()) {
//            shell.getManager().add(res, arg);
//        }
    }
}
