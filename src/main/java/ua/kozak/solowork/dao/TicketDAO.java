package ua.kozak.solowork.dao;

import ua.kozak.solowork.domain.Ticket;

import java.util.List;

public interface TicketDAO {

    Ticket add(Ticket ticket);

    void update(Ticket ticket);

    void delete(Ticket ticket);

    List<Ticket> getAll();

    List<Ticket> getAllByUserId(long userId);

    List<Ticket> getAllByEventId(long eventId);

    Ticket getById(long ticketId);

    void deleteAllByUserId(long userId);

    void deleteAllByEventId(long eventId);

}
