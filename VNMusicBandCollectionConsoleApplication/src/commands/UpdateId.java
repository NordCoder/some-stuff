package commands;

import entity.MusicBand;
import shell.Shell;

import static util.Util.readMusicBand;

public class UpdateId implements Command {
    private final Long id;
    private final String arg;

    public UpdateId(Long id, String arg) {
        this.id = id;
        this.arg = arg;
    }

    @Override
    public void execute(Shell shell) {
//        for (MusicBand band : shell.getManager().getCollection()) {
//            if (band != null) {
//                if (band.getId().equals(id)) {
//                    MusicBand newBand = readMusicBand();
//                    newBand.setId(id);
//                    shell.getManager().getCollection().insertElementAt(newBand,
//                            shell.getManager().getCollection().indexOf(band));
//                    shell.getManager().sort();
//                }
//            }
//        }
        System.err.println("no element with this id");
    }
}
