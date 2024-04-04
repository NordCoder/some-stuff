package commands;

import entity.MusicBand;
import shell.Shell;

public class CountLessThanGenre implements Command {
    private final int arg;

    public CountLessThanGenre(int arg) {
        this.arg = arg;
    }

    @Override
    public void execute(Shell shell) {
        long count = 0L;
//        for (MusicBand band : shell.getManager().getCollection()) {
//            if (band != null) {
//                if (band.getGenre().ordinal() < arg) {
//                    count++;
//                }
//            }
//        }
        System.out.println(count);
    }
}
