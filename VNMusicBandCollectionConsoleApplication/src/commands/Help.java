package commands;

import shell.Shell;

import static util.Consts.CommandList;

public class Help implements Command {
    @Override
    public void execute(Shell shell) {
        System.out.println(CommandList);
    }
}
