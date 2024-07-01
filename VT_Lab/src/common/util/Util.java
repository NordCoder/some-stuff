package common.util;

import client.ResponseReceiver;
import common.commands.*;
import common.entity.*;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import server.db.DatabaseConnection;
import server.db.DatabaseOperations;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.db.DatabaseOperations.*;

public class Util {
    public static Map<String, Command> getServerCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("add", new Add());
        commands.put("update", new UpdateById());
        commands.put("remove_by_id", new RemoveById());
        commands.put("clear", new Clear());
        commands.put("execute_script", new ExecuteScript());
        commands.put("add_if_max", new AddIfMax());
        commands.put("add_if_min", new AddIfMin());
        commands.put("history", new History());
        commands.put("remove_any_by_person", new RemoveAnyByPerson());
        commands.put("print_ascending", new PrintAscending());
        commands.put("print_field_ascending_person", new PrintFieldAscendingPerson());
        commands.put("register", new Register());
        commands.put("login", new Login());
        return commands;
    }

    public static Map<String, Command> getClientCommands() {
        Map<String, Command> commands = getServerCommands();
        commands.remove("execute_script");
        return commands;
    }


    public static void handleCommandsWithAdditionalInfo(CustomPair<Command, Request> command, InputParser parser) throws Exception {
        if (command.getFirst() instanceof SpecialCommand) {
            if (((SpecialCommand) command.getFirst()).needsWorker()) {
                ((SpecialCommand) command.getFirst()).setToAdd(parser.readWorker());
            } else {
                ((SpecialCommand) command.getFirst()).setToAdd(parser.readPerson());
            }
        } else if (command.getFirst() instanceof Register) {
            command.getSecond().setArgs(parser.readUsernamePassword());
        } else if (command.getFirst() instanceof Login) {
            List<String> userPassword = parser.readUsernamePassword();
            command.getSecond().getCard().setUsername(userPassword.get(0));
            command.getSecond().setArgs(userPassword);
        }
    }

    public static String generateSHA512Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, hashBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 128) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static CustomPair<Command, Request> readHandleCommand(InputParser parser, AccountCard card) throws Exception {
        CustomPair<Command, List<String>> command = parser.nextCommand();
        CustomPair<Command, Request> requestedCommand = new CustomPair<>(command.getFirst(), new Request(card, command.getSecond()));
        handleCommandsWithAdditionalInfo(requestedCommand, new InputParser(new ConsoleInputGetter(), getServerCommands()));
        return requestedCommand;
    }

    public static void handleLoginCommand(Response response, AccountCard card) {
        if (response.getLoginVerificationFlag() == ResponseReceiver.LoggingIn.EXISTS) {
            try {
                card.setUserId(DatabaseOperations.getUserIdByUsername(DatabaseConnection.getConnection(), card.getUsername()));
                card.setStatus(AccountCard.Authorization.AUTHORIZED);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (response.getLoginVerificationFlag() == ResponseReceiver.LoggingIn.DOES_NOT_EXIST) {
            card.setUsername("");
            card.setStatus(AccountCard.Authorization.UNAUTHORIZED);
            card.setUserId(-1);
        }
    }

    public static Worker mapRowToWorker(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int coordinatesId = resultSet.getInt("coordinates_id");
        LocalDateTime creationDate = resultSet.getDate("creation_date").toLocalDate().atStartOfDay();
        double salary = resultSet.getDouble("salary");
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
        Position position = Position.valueOf(resultSet.getString("position"));
        Status status = Status.valueOf(resultSet.getString("status"));
        int personId = resultSet.getInt("person_id");

        Coordinates coordinates = getCoordinatesById(DatabaseConnection.getConnection(), coordinatesId);
        Person person = getPersonById(DatabaseConnection.getConnection(), personId);

        Worker toRet = new Worker(name, coordinates, creationDate, salary, endDate, position, status, person);
        toRet.setId(id);
        return toRet;
    }

    public static boolean checkAuthorization(AccountCard card) {
        return card.getStatus() == AccountCard.Authorization.AUTHORIZED;
    }

    public static boolean allowedToChangeByWorker(AccountCard card, Worker w) throws Exception {
        return allowedToChangeById(card, w.getId());
    }

    public static boolean allowedToChangeById(AccountCard card, int id) throws Exception {
        try {
            if (isSuperuser(DatabaseConnection.getConnection(), card.getUserId())) {
                return true;
            } else {
                return card.getUserId() == getUserIdByWorkerId(DatabaseConnection.getConnection(), id);
            }
        } catch (SQLException e) {
            throw new Exception("failed to check if change is allowed");
        }
    }

}
