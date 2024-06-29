package common.commands;

import common.entity.Person;
import common.entity.Worker;

public interface SpecialCommand extends Command {
    void setToAdd(Worker w);
    void setToAdd(Person p);
    boolean needsWorker();
}
