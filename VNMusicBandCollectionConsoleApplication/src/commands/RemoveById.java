package commands;

import entity.MusicBand;
import shell.Shell;

import java.util.Objects;

public class RemoveById implements Command {
    private final Long id;

    public RemoveById(Long id) {
        this.id = id;
    }

    @Override
    public void execute(Shell shell) {
//        for (MusicBand band : shell.getManager().getCollection()) {
//            if (band != null) {
//                if (Objects.equals(band.getId(), id)) {
//                    shell.getManager().getCollection().remove(band);
//                }
//            }
//        }
    }
}
