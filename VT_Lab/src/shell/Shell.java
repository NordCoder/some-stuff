package shell;

import commands.Command;
import util.BadInputException;
import util.CustomPair;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static util.Saving.loadFromJson;

public class Shell {
    private final Receiver receiver = new Receiver();

    public Shell() {
        loadFromJson(receiver);
        runReadingLoop();
    }

    private void runReadingLoop() {
        InputParser commandParser = new InputParser(new ConsoleInputGetter());
        while (true) {
            System.out.print(">>>");
            try {
                CustomPair<Command, String[]> command = commandParser.nextCommand();
                command.getFirst().execute(receiver, Arrays.asList(command.getSecond()));
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Receiver getReceiver() {
        return receiver;
    }

}
