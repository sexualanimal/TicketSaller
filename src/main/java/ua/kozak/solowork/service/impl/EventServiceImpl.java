package ua.kozak.solowork.service.impl;

import ua.kozak.solowork.domain.Date;
import ua.kozak.solowork.domain.Event;
import ua.kozak.solowork.domain.exception.EventAlreadyExistsException;
import ua.kozak.solowork.domain.exception.EventNotExistsException;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.service.EventService;
import ua.kozak.solowork.service.StorageService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private StorageService<Set<Event>> storageService;

    public EventServiceImpl() {
        storageService = new StorageServiceImpl<>(FILENAME);
    }

    @Override
    public Event save(Event event) throws EventAlreadyExistsException {
        if (checkEventUnique(event)) {
            Set<Event> buffEvents = getAll();
            buffEvents.add(event);
            storageService.write(buffEvents);
            return getAll().stream()
                    .filter(e -> e.getId() == event.getId() && e.getName().equals(event.getName()))
                    .findFirst().orElse(event);
        } else {
            throw new EventAlreadyExistsException();
        }
    }

    @Override
    public Set<Event> saveAll(Set<Event> events, boolean overwrite) throws EventAlreadyExistsException {
        boolean eventsValid = events.stream()
                .map(this::checkEventUnique)
                .filter(b -> b.equals(false))
                .collect(Collectors.toSet())
                .isEmpty();
        if (eventsValid) {
            if (overwrite) {
                return storageService.write(events);
            } else {
                Set<Event> buffEvents = getAll();
                buffEvents.addAll(events);
                return storageService.write(buffEvents);
            }
        } else {
            throw new EventAlreadyExistsException();
        }
    }

    @Override
    public Event getById(int id) throws EventNotExistsException {
        return getAll().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(EventNotExistsException::new);
    }

    @Override
    public Event getByName(String name) throws EventNotExistsException {
        return getAll().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(EventNotExistsException::new);
    }

    @Override
    public Set<Event> getForDateRange(Date from, Date to) {
        return getAll().stream()
                .filter(e -> {
                    boolean datesEmpty = e.getDates().stream()
                            .filter(date -> date.getTime() >= from.getTime() && date.getTime() <= to.getTime())
                            .collect(Collectors.toSet())
                            .isEmpty();
                    return !datesEmpty;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getNextEvents(Date to) {
        return getForDateRange(new Date(), to);
    }

    @Override
    public Set<Event> getAll() {
        return storageService.read();
    }

    private Event buffEvent;

    @Override
    public Event remove(Event event) throws EventNotExistsException {
        Set<Event> buffEvents = getAll();
        buffEvents.forEach(e -> {
            if (e.getName().equals(event.getName()) && e.getId() == event.getId()) {
                buffEvent = e;
            }
        });
        buffEvents.remove(buffEvent);
        storageService.write(buffEvents);
        return buffEvent;
    }

    private boolean checkEventUnique(Event event) {
        return getAll().stream()
                .filter(e -> e.getId() == event.getId() && e.getName().equals(event.getName()))
                .collect(Collectors.toSet())
                .isEmpty();
    }
}
