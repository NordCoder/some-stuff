package commands;

import shell.Receiver;

import java.util.List;

public interface Command {
    void execute(Receiver receiver, List<String> args);
    String get_help_text();
    String get_help_name();
}
