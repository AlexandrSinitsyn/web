package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl() {
        super("User");
    }


    @Override
    public User findByLogin(String login) {
        return findBy(statement -> {
            statement.setString(1, login);
        }, "login");
    }

    @Override
    public User findByEmail(String email) {
        return findBy(statement -> {
            statement.setString(1, email);
        }, "email");
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return findBy(statement -> {
            statement.setString(1, login);
            statement.setString(2, passwordSha);
        }, "login", "passwordSha");
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return findBy(statement -> {
            statement.setString(1, email);
            statement.setString(2, passwordSha);
        }, "email", "passwordSha");
    }

    @Override
    public int findCount() {
        return findAll().size();
    }


    @Override
    public void save(User user, String email, String passwordSha) {
        save(user, statement -> {
            statement.setString(1, user.getLogin());
            statement.setString(2, email);
            statement.setString(3, passwordSha);
            statement.setBoolean(4, user.getAdmin());
        }, "login", "email", "passwordSha", "admin");
    }


    @Override
    public void updateUserAdminRoot(long id, boolean admin) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `User` SET admin=? WHERE id=?")) {
                statement.setBoolean(1, admin);
                statement.setLong(2, id);

                statement.executeQuery();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + tableName + ".", e);
        }
    }



    @Override
    protected User parse(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "admin":
                    user.setAdmin(resultSet.getBoolean(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }
}
