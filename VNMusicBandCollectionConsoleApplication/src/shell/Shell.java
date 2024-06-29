package shell;

import commands.Command;
import util.BadInputException;
import util.CommandScanner;

import java.util.NoSuchElementException;

public class Shell {
    private final CollectionManager manager = new CollectionManager();
    private boolean exitFlag = true;

    public Shell() {
        init();
    }

    private void init() {
        CommandScanner CS = new CommandScanner();
        while (exitFlag) {
            System.out.print(">>>");
            try {
                Command command = CS.readCommand();
                command.execute(this);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public CollectionManager getManager() {
        return manager;
    }

    public void setExitFlag(boolean exitFlag) {
        this.exitFlag = exitFlag;
    }

}
