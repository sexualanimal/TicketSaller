package ua.kozak.solowork.dao;

import ua.kozak.solowork.domain.Date;
import ua.kozak.solowork.domain.Event;

import java.util.List;
import java.util.Set;

public interface EventDAO {

    Event add(Event event);

    void update(Event event);

    void delete(Event event);

    Event getById(int id);

    Event getByName(String name);

    List<Event> getAll();

    List<Event> getForDateRange(Date from, Date to);

    List<Event> getNextEvents(Date to);

    List<Event> getByAuditoryId(long auditoryId);

    void deleteAllByAuditoryId(long auditoryId);

}