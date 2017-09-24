package ua.kozak.solowork.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.EventDAO;
import ua.kozak.solowork.dao.TicketDAO;
import ua.kozak.solowork.dao.mapper.EventRowMapper;
import ua.kozak.solowork.domain.Date;
import ua.kozak.solowork.domain.Event;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventDAOJdbcImpl implements EventDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EventRowMapper eventRowMapper;

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public Event add(Event event) {
        String sql = "INSERT INTO ticket_seller.public.event_table(" +
                "event_name, event_description, event_auditory_id, event_start_date, event_end_date, event_base_price" +
                ") VALUES(?,?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement preparedStatement = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(1, event.getDescription());
            preparedStatement.setInt(1, (int) event.getAuditoryId());
            preparedStatement.setTimestamp(1, new Timestamp(event.getStartDate().getTime()));
            preparedStatement.setTimestamp(1, new Timestamp(event.getEndDate().getTime()));
            preparedStatement.setFloat(1, event.getBasePrice());
            return preparedStatement;
        }, keyHolder);

        event.getTickets().forEach(ticketDAO::add);//todo not add events but modify

        Optional<List<Map<String, Object>>> keyList = Optional.ofNullable(keyHolder.getKeyList());
        keyList.map(item -> item.iterator().next().get("event_id")).map(id -> (long) id).ifPresent(event::setId);
        return event;
    }

    @Override
    public void update(Event event) {
        String sql = "UPDATE ticket_seller.public.event_table SET (" +
                "event_name, event_description, event_auditory_id, event_start_date, event_end_date, event_base_price" +
                ") = (?,?) WHERE event_id = ?";
        jdbcTemplate.update(sql, event.getName(), event.getDescription(), event.getAuditoryId(),
                event.getStartDate(), event.getEndDate(), event.getBasePrice(), event.getId());
        ticketDAO.deleteAllByEventId(event.getId());
        event.getTickets().forEach(ticketDAO::add);//todo not add events but modify
    }

    @Override
    public void delete(Event event) {
        String sql = "DELETE FROM ticket_seller.public.event_table WHERE event_id = ?";
        jdbcTemplate.update(sql, event.getId());
        ticketDAO.deleteAllByEventId(event.getId());
    }

    @Override
    public Event getById(int id) {
        String sql = "SELECT * FROM ticket_seller.public.event_table WHERE event_id = ?";
        return jdbcTemplate.query(sql, eventRowMapper, id).iterator().next();
    }

    @Override
    public Event getByName(String name) {
        String sql = "SELECT * FROM ticket_seller.public.event_table WHERE event_name = ?";
        return jdbcTemplate.query(sql, eventRowMapper, name).iterator().next();
    }

    @Override
    public List<Event> getAll() {
        String sql = "SELECT * FROM ticket_seller.public.event_table";
        return jdbcTemplate.query(sql, eventRowMapper);
    }

    @Override
    public List<Event> getForDateRange(Date from, Date to) {
        String sql = "SELECT * FROM ticket_seller.public.event_table WHERE event_start_date >= ? AND event_start_date <= ?";
        return jdbcTemplate.query(sql, eventRowMapper, from, to);
    }

    @Override
    public List<Event> getNextEvents(Date to) {
        String sql = "SELECT * FROM ticket_seller.public.event_table WHERE event_start_date >= ? AND event_start_date <= ?";
        return jdbcTemplate.query(sql, eventRowMapper, new Date(), to); // fixme wtf
    }

    @Override
    public List<Event> getByAuditoryId(long auditoryId) {
        String sql = "SELECT * FROM ticket_seller.public.event_table WHERE event_auditory_id = ?";
        return jdbcTemplate.query(sql, eventRowMapper, auditoryId);
    }

    @Override
    public void deleteAllByAuditoryId(long auditoryId) {
        String sql = "DELETE FROM ticket_seller.public.event_table WHERE event_auditory_id = ?";
        jdbcTemplate.update(sql, auditoryId);
//        ticketDAO.deleteAllByEventId(event.getId()); //fixme
    }
}
