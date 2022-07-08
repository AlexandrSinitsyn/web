package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends BasicRepositoryImpl<Talk> implements TalkRepository {

    public TalkRepositoryImpl() {
        super("Talk");
    }


    @Override
    public void save(Talk talk, long sourceUserId, long targetUserId, String text) {
        save(talk, statement -> {
            statement.setLong(1, sourceUserId);
            statement.setLong(2, targetUserId);
            statement.setString(3, text);
        }, "sourceUserId", "targetUserId", "text");
    }


    @Override
    protected Talk parse(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return talk;
    }
}
