package ua.kozak.solowork.dao.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.SeatDAO;
import ua.kozak.solowork.dao.mapper.TicketRowMapper;
import ua.kozak.solowork.domain.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TicketRowMapperImpl implements TicketRowMapper {

    @Autowired
    private SeatDAO seatDAO;

    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        final Ticket ticket = new Ticket();
        ticket.setId(resultSet.getInt("ticket_id"));
        ticket.setEventId(resultSet.getInt("ticket_event_id"));
        ticket.setUserId(resultSet.getInt("ticket_user_id"));
        ticket.setSeat(seatDAO.getById(resultSet.getInt("ticket_seat_id")));
        return ticket;
    }
}
