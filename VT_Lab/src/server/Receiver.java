package server;

import common.commands.Command;
import common.entity.Person;
import common.entity.Worker;
import common.input.ConsoleInputGetter;
import common.input.FileInputGetter;
import common.input.InputParser;
import common.util.CustomPair;

import java.time.ZonedDateTime;
import java.util.*;

import static common.util.Util.getClientCommands;
import static server.Saving.saveToJsonStatic;
import static common.util.Util.getServerCommands;

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
            return new InputParser(new ConsoleInputGetter(), getClientCommands()).readWorker();  // never throws
        } catch (Exception e) {
            return null;
        }
    }

    public Person readPerson() {
        try {
            if (scriptParser != null) {
                return scriptParser.readPerson();
            }
            return new InputParser(new ConsoleInputGetter(), getClientCommands()).readPerson();  // never throws
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addWorker(Worker worker) {
        if (worker.getId() == null) {
            long id = generateId();
            worker.setId(id);
        }
        return collection.add(worker);
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

    public String getHistory() {
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        for (Command command : commandsHistory) {
            sb.append(command.getHelpName()).append(System.lineSeparator());
            counter++;
            if (counter == 15) {
                break;
            }
        }
        return sb.toString();
    }

    public String getAscending() {
        Comparator<Worker> countBasedComparator = Comparator.comparingDouble(Worker::countToCompare);
        StringBuilder sb = new StringBuilder();
        for (Worker w : collection.stream().sorted(countBasedComparator).toList()) {
            sb.append(w).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String getInfo() {
        return "type: " + collection.getClass().getName() +
                "size: " + collection.size() +
                "creation date: " + creationDate;
    }

    public String getFieldAscendingPerson() {
        Comparator<Worker> personBasedComparator = Comparator.comparingDouble(Worker::getPersonCompareValue);
        StringBuilder sb = new StringBuilder();
        for (Worker w : collection.stream().sorted(personBasedComparator).toList()) {
            sb.append(w.getPerson()).append(System.lineSeparator());
        }
        return sb.toString();
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
        scriptParser = new InputParser(new FileInputGetter(filePath), getClientCommands());
        while (scriptParser.hasNextLine()) {
            try {
                CustomPair<Command, String[]> command = scriptParser.nextCommand();
                command.getFirst().execute(this, Arrays.asList(command.getSecond()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        scriptParser = null;
    }

    public void replaceWorkerById(long id, Worker toAdd) {
        removeWorkerById(id);
        toAdd.setId(id);
        addWorker(toAdd);
    }

    public String getHelp() {
        Map<String, Command> allCommands = getServerCommands();
        StringBuilder sb = new StringBuilder();
        for (Command command : allCommands.values()) {
            sb.append(command.getHelpName()).append(": ").append(command.getHelpText()).append(System.lineSeparator());
        }
        return sb.toString();
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