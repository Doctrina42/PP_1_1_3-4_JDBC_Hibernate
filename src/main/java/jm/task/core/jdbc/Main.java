package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import javax.transaction.SystemException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Util.getConnection();
        UserServiceImpl userDao = new UserServiceImpl();

        userDao.createUsersTable();
        userDao.createUsersTable();

        userDao.saveUser("Igor", "Nemo", (byte) 20);
        userDao.saveUser("Petr", "Olegovna", (byte) 25);
        userDao.saveUser("Ivan", "Petrova", (byte) 31);
        userDao.saveUser("Marina", "Ivanova", (byte) 38);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
