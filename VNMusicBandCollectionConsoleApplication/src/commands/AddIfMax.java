package commands;

import entity.MusicBand;
import shell.Shell;

import static util.Util.readMusicBand;

public class AddIfMax implements Command {
    private final String arg;

    public AddIfMax(String arg) {
        this.arg = arg;
    }


    @Override
    public void execute(Shell shell) {
        MusicBand res = readMusicBand();
//        if (res.getValue() > shell.getManager().getMaxCollectionValue()) {
//            shell.getManager().add(res, arg);
//        }
    }
}
