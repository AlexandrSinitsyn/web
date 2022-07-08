package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();


    public void save(User user, Event.EventType type) {
        final Event event = new Event();
        eventRepository.save(event, user.getId(), type);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }


    public Optional<Event> findByUserId(long userId) {
        return findAll().stream().filter((e) -> e.getUserId() == userId).findFirst();
    }
}
