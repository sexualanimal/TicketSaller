package ua.kozak.solowork.dao.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.EventDAO;
import ua.kozak.solowork.dao.SeatDAO;
import ua.kozak.solowork.dao.mapper.AuditoryRowMapper;
import ua.kozak.solowork.domain.Auditory;
import ua.kozak.solowork.domain.Event;
import ua.kozak.solowork.domain.Seat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditoryRowMapperImpl implements AuditoryRowMapper {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private SeatDAO seatDAO;

    @Override
    public Auditory mapRow(ResultSet resultSet, int i) throws SQLException {
        final Auditory auditory = new Auditory();
        auditory.setId(resultSet.getInt("auditory_id"));
        auditory.setName(resultSet.getString("auditory_name"));
        auditory.setLocation(resultSet.getString("auditory_location"));
        List<Event> evens = new ArrayList<>();
        evens.addAll(eventDAO.getByAuditoryId(resultSet.getInt("auditory_id")));
        auditory.setEvents(evens);
        List<Seat> seats = new ArrayList<>();
        seats.addAll(seatDAO.getByAuditoryId(resultSet.getInt("auditory_id")));
        auditory.setSeats(seats);
        return auditory;
    }
}
