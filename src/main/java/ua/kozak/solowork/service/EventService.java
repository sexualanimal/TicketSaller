package ua.kozak.solowork.service;

import ua.kozak.solowork.domain.Date;
import ua.kozak.solowork.domain.Event;
import ua.kozak.solowork.domain.exception.EventAlreadyExistsException;
import ua.kozak.solowork.domain.exception.EventNotExistsException;

import java.util.Set;

public interface EventService {
    String FILENAME = "event_data";

    Event save(Event event) throws EventAlreadyExistsException;

    Set<Event> saveAll(Set<Event> events, boolean overwrite) throws EventAlreadyExistsException;

    Event getById(int id) throws EventNotExistsException;

    Event getByName(String name) throws EventNotExistsException;

    Set<Event> getForDateRange(Date from, Date to);

    Set<Event> getNextEvents(Date to);

    Set<Event> getAll();

    Event remove(Event user) throws EventNotExistsException;

    boolean checkEventUnique(Event event);

    long getNextEmptyId();
}
