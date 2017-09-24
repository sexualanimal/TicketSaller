package ua.kozak.solowork.dao.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.TicketDAO;
import ua.kozak.solowork.dao.mapper.EventRowMapper;
import ua.kozak.solowork.domain.Date;
import ua.kozak.solowork.domain.Event;
import ua.kozak.solowork.domain.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventRowMapperImpl implements EventRowMapper {

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        final Event event = new Event();
        event.setId(resultSet.getInt("event_id"));
        event.setName(resultSet.getString("event_name"));
        event.setDescription(resultSet.getString("event_description"));
        event.setAuditoryId(resultSet.getInt("event_auditory_id"));
        event.setBasePrice(resultSet.getFloat("event_base_prise"));
        event.setStartDate(new Date(resultSet.getTimestamp("event_start_date").getTime()));
        event.setEndDate(new Date(resultSet.getTimestamp("event_end_date").getTime()));
        List<Ticket> tickets = new ArrayList<>();
        tickets.addAll(ticketDAO.getAllByEventId(resultSet.getInt("event_id")));
        event.setTickets(tickets);
        return event;
    }
}
