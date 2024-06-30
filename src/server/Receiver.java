package server;

import common.commands.Command;
import common.commands.Request;
import common.entity.Person;
import common.entity.Worker;
import common.input.FileInputGetter;
import common.input.InputParser;
import common.util.AccountCard;
import common.util.CustomPair;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static common.util.Util.*;
import static server.Saving.saveToJsonStatic;

// treeset comparing value is id, worker comparator is also id-based
// but to compare workers for commands function countToCompare is used

// todo fix execute script

public class Receiver { // used for collection management and command execution
    private TreeSet<Worker> collection;
    private ZonedDateTime creationDate;
    private ArrayList<Command> commandsHistory;
    private InputParser scriptParser = null;
    private AccountCard accountCard;

    public Receiver() {
        this.collection = new TreeSet<>(Comparator.comparingDouble(Worker::getId));
        this.commandsHistory = new ArrayList<>();
        creationDate = ZonedDateTime.now();
    }

    public boolean addWorker(Worker worker) {
        if (worker.getId() == null) {
            long id = generateId();
            worker.setId(id);
        }
        return collection.add(worker);
    }

    public double getMaximumValue() {
        Optional<Worker> maxWorker = collection.stream().max(Comparator.comparingDouble(Worker::getId));
        return maxWorker.map(Worker::countToCompare).orElse(Double.MIN_VALUE);
    }

    public double getMinimumValue() {
        Optional<Worker> minWorker = collection.stream().min(Comparator.comparingDouble(Worker::getId));
        return minWorker.map(Worker::countToCompare).orElse(Double.MAX_VALUE);
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
        return commandsHistory.stream()
                .limit(14)
                .map(Command::getHelpName)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getAscending() {
        return collection
                .stream()
                .sorted(Comparator.comparingDouble(Worker::countToCompare))
                .map(Worker::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String show() {
        return collection
                .stream()
                .sorted(Comparator.comparingDouble(Worker::getCoordinatesCompareValue))
                .map(Worker::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }

    public String getInfo() {
        return "type: " + collection.getClass().getName() + System.lineSeparator() +
                "size: " + collection.size() + System.lineSeparator() +
                "creation date: " + creationDate + System.lineSeparator();
    }

    public String getFieldAscendingPerson() {
        return collection.stream()
                .sorted(Comparator.comparingDouble(Worker::getPersonCompareValue))
                .map(worker -> worker.getPerson().toString())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public boolean removeAnyByPerson(Person person) {
        for (Worker w : collection) {
            if (w.getPerson().compareTo(person) == 0) {
                collection.remove(w);
                return true;
            }
        }
        return false;
    }

    public boolean removeWorkerById(long id) {
        long size = collection.size();
        collection = collection
                .stream()
                .filter(worker -> worker.getId() != id)
                .collect(Collectors.toCollection(TreeSet::new));
        return size != collection.size();
    }

    public boolean saveToJson() {
        return saveToJsonStatic(this);
    }

    public boolean executeScript(String filePath, AccountCard card) {
        scriptParser = new InputParser(new FileInputGetter(filePath), getClientCommands());
        while (scriptParser.hasNextLine()) {
            try {
                CustomPair<Command, Request> command = readHandleCommand(scriptParser, card);
                command.getSecond().setReceiver(this);
                command.getFirst().execute(command.getSecond());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        scriptParser = null;
        return true;
    }

    public void replaceWorkerById(long id, Worker toAdd) {
        removeWorkerById(id);
        toAdd.setId(id);
        addWorker(toAdd);
    }

    public String getHelp() {
        return getClientCommands()
                .values()
                .stream()
                .map(command -> (command.getHelpName() + ": " + command.getHelpText()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private long generateId() {
        return System.currentTimeMillis();
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