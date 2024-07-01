package server;

import common.commands.Command;
import common.commands.Request;
import common.entity.Person;
import common.entity.Worker;
import common.input.FileInputGetter;
import common.input.InputParser;
import common.util.AccountCard;
import common.util.CustomPair;
import server.db.DatabaseConnection;
import server.db.DatabaseOperations;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static common.util.Util.*;
import static server.db.DatabaseOperations.insertWorkerSql;

// treeset comparing value is id, worker comparator is also id-based
// but to compare workers for commands function countToCompare is used

// todo fix execute script

public class Receiver { // used for collection management and command execution
    private Set<Worker> collection;
    private final ZonedDateTime creationDate;
    private final ArrayList<Command> commandsHistory;

    public Receiver() {
        this.collection = Collections.synchronizedSet(new TreeSet<>(Comparator.comparingDouble(Worker::getId)));
        this.commandsHistory = new ArrayList<>();
        creationDate = ZonedDateTime.now();
    }

    public boolean addWorker(Worker worker, int userId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        int id = insertWorkerSql(connection, worker, userId);
        worker.setId(id);
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

    public Set<Worker> getCollection() {
        return collection;
    }

    public void setCollection(Set<Worker> collection) {
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
        return collection.isEmpty() ? "collection's empty" : collection
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

    public boolean removeAnyByPerson(Person person, AccountCard card) throws Exception {
        for (Worker w : collection) {
            if (w.getPerson().compareTo(person) == 0) {
                if (allowedToChangeById(card, w.getId())) {
                    DatabaseOperations.deleteWorkerSql(DatabaseConnection.getConnection(), w.getId());
                    collection.remove(w);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeWorkerById(Integer id) throws SQLException {
        DatabaseOperations.deleteWorkerSql(DatabaseConnection.getConnection(), id);
        long size = collection.size();
        collection = collection
                .stream()
                .filter(worker -> !Objects.equals(worker.getId(), id))
                .collect(Collectors.toCollection(TreeSet::new));
        return size != collection.size();
    }

    public boolean executeScript(String filePath, AccountCard card) {
        InputParser scriptParser = new InputParser(new FileInputGetter(filePath), getClientCommands());
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
        return true;
    }

    public void replaceWorkerById(Integer id, Worker toAdd, int userId) throws SQLException {
        removeWorkerById(id);
        toAdd.setId(id);
        addWorker(toAdd, userId);
    }

    public String getHelp() {
        return getClientCommands()
                .values()
                .stream()
                .map(command -> (command.getHelpName() + ": " + command.getHelpText()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void clear() throws SQLException {
        DatabaseOperations.clearDataBase(DatabaseConnection.getConnection());
    }
}