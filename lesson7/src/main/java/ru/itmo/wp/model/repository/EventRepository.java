package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface EventRepository {
    void save(Event event, long userId, Event.EventType type);
    List<Event> findAll();
    Event find(long id);
}
