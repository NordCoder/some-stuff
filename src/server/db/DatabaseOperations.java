package server.db;

import client.ResponseReceiver;
import common.entity.*;

import static client.ResponseReceiver.LoggingIn.*;
import static common.util.Util.mapRowToWorker;

import java.sql.*;
import java.util.TreeSet;


public class DatabaseOperations {
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
            String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, login);
                stmt.setString(2, passwordHash);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? EXISTS : DOES_NOT_EXIST;
                }
            }
    }

    public static TreeSet<Worker> getAllWorkersFromDB(Connection connection) throws SQLException {
        String sql = "SELECT * FROM workers";
        TreeSet<Worker> result = new TreeSet<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(mapRowToWorker(resultSet));
                }
            }
        }
        return result;
    }

    public static int insertWorkerSql(Connection connection, Worker w, int userId) throws SQLException {
        int coordinatesId = insertCoordinates(connection, w.getCoordinates());
        int personId = insertPerson(connection, w.getPerson());
        return insertWorker(connection, w, coordinatesId, personId, userId);
    }

    public static void deleteWorkerSql(Connection connection, int workerId) throws SQLException {
        String query = "DELETE FROM workers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, workerId);
            stmt.executeUpdate();
        }
    }

    public static void clearDataBase(Connection connection) throws SQLException {
        String sql = "DELETE FROM workers";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    public static Coordinates getCoordinatesById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM coordinates WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Coordinates(rs.getDouble("x"), rs.getLong("y"));
            } else {
                throw new SQLException("Coordinates not found");
            }
        }
    }

    public static Person getPersonById(Connection connection, int personId) throws SQLException {
        String sql = "SELECT * FROM persons WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, personId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Person(rs.getInt("height"),
                        rs.getDouble("weight"),
                        Color.valueOf(rs.getString("hair_color")),
                        Country.valueOf(rs.getString("nationality")));
            } else {
                throw new SQLException("Person not found");
            }
        }
    }

    public static boolean isSuperuser(Connection connection, int userId) throws SQLException {
        String sql = "SELECT superuser FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("superuser");
            } else {
                throw new SQLException("user not found");
            }
        }
    }

    public static int getUserIdByWorker(Connection connection, Worker worker) throws SQLException {
       return getUserIdByWorkerId(connection, worker.getId());
    }

    public static int getUserIdByWorkerId(Connection connection, int id) throws SQLException {
        String sql = "SELECT user_id FROM workers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            } else {
                throw new SQLException("worker not found");
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

    private static int insertWorker(Connection connection, Worker w, int coordinatesId, int personId, int userId) throws SQLException {
        String sql = "INSERT INTO workers (name, coordinates_id, salary, end_date, position, status, person_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, w.getName());
            statement.setInt(2, coordinatesId);
            statement.setDouble(3, w.getSalary());
            statement.setDate(4, w.getEndDate() != null ? Date.valueOf(w.getEndDate()) : null);
            statement.setString(5, w.getPosition().name());
            statement.setString(6, w.getStatus() != null ? w.getStatus().name() : null);
            statement.setInt(7, personId);
            statement.setInt(8, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Worker not found");
            }
        }
    }
}

