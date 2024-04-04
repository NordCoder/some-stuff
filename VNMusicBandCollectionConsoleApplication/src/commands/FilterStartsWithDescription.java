package commands;

import entity.MusicBand;
import shell.Shell;

public class FilterStartsWithDescription implements Command {
    private final String arg;

    public FilterStartsWithDescription(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(Shell shell) {
//        for (MusicBand band : shell.getManager().getCollection()) {
//            if (band != null) {
//                if (band.getDescription().startsWith(arg)) {
//                    System.out.println(band.toString());
//                }
//            }
//        }
    }
}
