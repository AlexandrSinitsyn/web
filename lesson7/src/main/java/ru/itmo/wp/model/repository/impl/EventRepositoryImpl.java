package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class EventRepositoryImpl extends BasicRepositoryImpl<Event> implements EventRepository {

    public EventRepositoryImpl() {
        super("Event");
    }


    @Override
    public void save(Event event, long userId, Event.EventType type) {
        save(event, statement -> {
            statement.setLong(1, userId);
            statement.setString(2, type.name());
        }, "userId", "type");
    }


    @Override
    protected Event parse(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(Event.EventType.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }
}
