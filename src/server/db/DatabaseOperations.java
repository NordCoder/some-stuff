package server.db;

import client.ResponseReceiver;
import common.entity.Color;
import common.entity.Coordinates;
import common.entity.Person;
import common.entity.Worker;
import static client.ResponseReceiver.LoggingIn.*;

import java.sql.*;

public class DatabaseOperations {

    public static void insertWorkerSql(Connection connection, Worker w, int userId) throws SQLException {
        int coordinatesId = insertCoordinates(connection, w.getCoordinates());
        int personId = insertPerson(connection, w.getPerson());
        insertWorker(connection, w, coordinatesId, personId, userId);

    }

    public static void addUser(Connection connection, String username, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.executeUpdate();
        }
    }

    public static int getUserIdByUsername(Connection connection, String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("User not found");
                }
            }
        }
    }

    public static ResponseReceiver.LoggingIn authenticateUserCheck(Connection connection, String login, String passwordHash) throws SQLException {
            String query = "SELECT * FROM users WHERE login = ? AND password_hash = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, login);
                stmt.setString(2, passwordHash);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? EXISTS : DOES_NOT_EXIST;
                }
            }
    }

    private static int insertCoordinates(Connection connection, Coordinates coordinates) throws SQLException {
        String sql = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, coordinates.getX());
            statement.setLong(2, coordinates.getY());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        return 0;
    }

    private static int insertPerson(Connection connection, Person person) throws SQLException {
        String sql = "INSERT INTO persons (height, weight, hair_color, nationality) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, person.getHeight());
            statement.setDouble(2, person.getWeight());
            statement.setString(3, person.getHairColor() != null ? person.getHairColor().name() : null);
            statement.setString(4, person.getNationality().name());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        return 0;
    }

    private static void insertWorker(Connection connection, Worker w, int coordinatesId, int personId, int userId) throws SQLException {
        String sql = "INSERT INTO workers (name, coordinates_id, salary, end_date, position, status, person_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, w.getName());
            statement.setInt(2, coordinatesId);
            statement.setDouble(3, w.getSalary());
            statement.setDate(4, w.getEndDate() != null ? Date.valueOf(w.getEndDate()) : null);
            statement.setString(5, w.getPosition().name());
            statement.setString(6, w.getStatus() != null ? w.getStatus().name() : null);
            statement.setInt(7, personId);
            statement.setInt(8, userId);
            statement.executeUpdate();
        }
    }
}

