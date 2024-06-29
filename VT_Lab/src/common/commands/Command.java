package common.commands;

import server.Receiver;

import java.io.Serializable;
import java.util.List;

public interface Command extends Serializable {
    Response execute(Receiver receiver, List<String> args);
    String getHelpText();
    String getHelpName();
}
