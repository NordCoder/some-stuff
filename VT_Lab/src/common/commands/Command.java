package common.commands;

import java.io.Serializable;

public interface Command extends Serializable {
    Response execute(Request request);
    String getCommandDescription();
    String getCommandName();
}
