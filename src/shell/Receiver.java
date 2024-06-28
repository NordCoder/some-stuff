package shell;

import commands.Command;
import entity.Person;
import entity.Worker;
import util.CustomPair;

import java.time.ZonedDateTime;
import java.util.*;

import static util.Saving.saveToJsonStatic;
import static util.Util.getAllCommands;

public class Receiver { // used for collection management and command execution
    private TreeSet<Worker> collection;
    private ZonedDateTime creationDate;

    public void setCommandsHistory(ArrayList<Command> commandsHistory) {
        this.commandsHistory = commandsHistory;
    }

    private ArrayList<Command> commandsHistory;
    private InputParser scriptParser = null;

    public Receiver() {
        this.collection = new TreeSet<>(Comparator.comparingDouble(Worker::getId));
        this.commandsHistory = new ArrayList<>();
        creationDate = ZonedDateTime.now();
    }

    public Worker readWorker() {
        try {
            if (scriptParser != null) {
                return scriptParser.readWorker();
            }
            return new InputParser(new ConsoleInputGetter()).readWorker();  // never throws
        } catch (Exception e) {
            return null;
        }
    }

    public Person readPerson() {
        try {
            if (scriptParser != null) {
                return scriptParser.readPerson();
            }
            return new InputParser(new ConsoleInputGetter()).readPerson();  // never throws
        } catch (Exception e) {
            return null;
        }
    }

    public void addWorker(Worker worker) {
        if (worker.getId() == null) {
            long id = generateId();
            worker.setId(id);
        }
        collection.add(worker);
    }

    public double getMaximumValue() {
        return getExtremeValue(true);
    }

    public double getMinimumValue() {
        return getExtremeValue(false);
    }

    public TreeSet<Worker> getCollection() {
        return collection;
    }

    public void setCollection(TreeSet<Worker> collection) {
        this.collection = collection;
    }

    public void addCommandHistoryRecord(Command command) {
        commandsHistory.add(command);
    }

    public void printHistory() {
        int counter = 0;
        for (Command command : commandsHistory) {
            System.out.println(command.get_help_name());
            counter++;
            if (counter == 15) {
                break;
            }
        }
    }

    public void printAscending() {
        Comparator<Worker> countBasedComparator = Comparator.comparingDouble(Worker::countToCompare);
        for (Worker w : collection.stream().sorted(countBasedComparator).toList()) {
            System.out.println(w);
        }
    }

    public void printInfo() {
        System.out.println("type: " + collection.getClass().getName());
        System.out.println("size: " + collection.size());
        System.out.println("creation date: " + creationDate);
    }

    public void printFieldAscendingPerson() {
        Comparator<Worker> personBasedComparator = Comparator.comparingDouble(Worker::getPersonCompareValue);
        for (Worker w : collection.stream().sorted(personBasedComparator).toList()) {
            System.out.println(w.getPerson());
        }
    }

    public void removeAnyByPerson(Person person) {
        for (Worker w : collection) {
            if (w.getPerson().compareTo(person) == 0) {
                collection.remove(w);
                return;
            }
        }
    }

    public void removeWorkerById(long id) {
        for (Worker w : collection) {
            if (w.getId() == id) {
                collection.remove(w);
                return;
            }
        }
    }

    public void saveToJson() {
        saveToJsonStatic(this);
    }

    public void executeScript(String filePath) {
        scriptParser = new InputParser(new FileInputGetter(filePath));
        while (scriptParser.hasNextLine()) {
            try {
                CustomPair<Command, String[]> command = scriptParser.nextCommand();
                command.getFirst().execute(this, Arrays.asList(command.getSecond()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        scriptParser = null;
    }

    public void replaceWorkerById(long id, Worker toAdd) {
        removeWorkerById(id);
        toAdd.setId(id);
        addWorker(toAdd);
    }

    public void printHelp() {
        Map<String, Command> allCommands = getAllCommands();
        for (Command command : allCommands.values()) {
            System.out.println(command.get_help_name() + ": " + command.get_help_text());
        }
    }

    private long generateId() {
        return System.currentTimeMillis();
    }

    private double getExtremeValue(boolean flag) {
        double extreme = flag ? Double.MIN_VALUE : Double.MAX_VALUE;
        if (collection.isEmpty())
            return extreme;
        for (Worker w : collection) {
            double cur = w.countToCompare();
            if (flag ? (cur > extreme) : (cur < extreme)) {
                extreme = cur;
            }
        }
        return extreme;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public ArrayList<Command> getCommandsHistory() {
        return commandsHistory;
    }
}