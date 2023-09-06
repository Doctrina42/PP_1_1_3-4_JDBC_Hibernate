package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.transaction.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user" +
                    "(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(45) ," +
                    "lastname VARCHAR(45) ," +
                    "age TINYINT(10) " +
                    ")");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("При тестировании создания таблицы пользователей произошло исключение\n" + e.getMessage());
            connection.rollback();
        }

    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("При тестировании удаления таблицы произошло исключение\n" + e.getMessage());
            connection.rollback();
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES (id,?,?,?)")) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Во время тестирования сохранения пользователя произошло исключение\n" + e.getMessage());
            connection.rollback();
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE ID = ?")) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("При тестировании удаления пользователя по id произошло исключение\n" + e.getMessage());
            connection.rollback();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("При попытке достать всех пользователей из базы данных произошло исключение\n" + e.getMessage());
            connection.rollback();
        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE user");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("При тестировании очистки таблицы пользователей произошло исключение\n" + e.getMessage());
            connection.rollback();
        }

    }
}