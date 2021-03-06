package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public abstract class BasicRepositoryImpl<T> {

    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    public final String tableName;

    public BasicRepositoryImpl(String tableName) {
        this.tableName = tableName;
    }


    public List<T> findAll() {
        List<T> res = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + tableName + "` ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    T t;
                    while ((t = parse(statement.getMetaData(), resultSet)) != null) {
                        res.add(t);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + tableName + ".", e);
        }
        return res;
    }

    public T find(long id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + tableName + "` WHERE id=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return parse(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + tableName + ".", e);
        }
    }


    protected T findBy(StatementSetter statementSetter, String... fields) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            StringBuilder sqlRequest = new StringBuilder();

            final String sep = " AND ";

            for (String field : fields) {
                sqlRequest.append(field).append("=?").append(sep);
            }

            final int len = sqlRequest.length();
            sqlRequest.delete(len - sep.length(), len);

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + tableName + "` WHERE " + sqlRequest)) {
                statementSetter.set(statement);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return parse(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + tableName + ".", e);
        }
    }


    protected void save(T t, StatementSetter statementSetter, SavingTool<T> savingTool, String... fields) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            StringBuilder sqlRequest = new StringBuilder();

            final String sep = ", ";

            for (String field : fields) {
                sqlRequest.append("`").append(field).append("`").append(sep);
            }

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `" + tableName + "` (" + sqlRequest + "`creationTime`) VALUES (" +
                            "?, ".repeat(fields.length) +  "NOW())", Statement.RETURN_GENERATED_KEYS)) {

                statementSetter.set(statement);

                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + tableName + ".");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        savingTool.updateSettings(t, generatedKeys);
                    } else {
                        throw new RepositoryException("Can't save " + tableName + " [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + tableName + ".", e);
        }
    }


    abstract protected T parse(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;


    protected interface StatementSetter {
        void set(PreparedStatement statement) throws SQLException;
    }

    protected interface SavingTool<E> {
        void updateSettings(E e, ResultSet generatedKeys) throws SQLException;
    }
}
