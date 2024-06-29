package commands;

import shell.Shell;

import java.io.File;
import java.io.PrintWriter;

public class Clear implements Command {

    @Override
    public void execute(Shell shell) {
        shell.getManager().getCollection().clear();
        File xmlFile = new File("data.xml");
        try {
            PrintWriter writer = new PrintWriter(xmlFile);
            writer.print("");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
