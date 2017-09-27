package ua.kozak.solowork.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.TicketDAO;
import ua.kozak.solowork.dao.mapper.TicketRowMapper;
import ua.kozak.solowork.domain.Ticket;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketDAOJdbcImpl implements TicketDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TicketRowMapper ticketRowMapper;

    @Override
    public Ticket add(Ticket ticket) {
        String sql = "INSERT INTO ticket_seller.public.ticket_table(ticket_user_id, ticket_event_id, ticket_seat_id) VALUES(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement preparedStatement = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) ticket.getUserId());
            preparedStatement.setInt(2, (int) ticket.getEventId());
            preparedStatement.setInt(3, (int) ticket.getSeat().getId());
            return preparedStatement;
        }, keyHolder);

        Optional<List<Map<String, Object>>> keyList = Optional.ofNullable(keyHolder.getKeyList());
        long ticketId = keyList.map(item -> item.iterator().next().get("ticket_id")).map(id -> Long.valueOf((Integer)id)).orElse(0L);
        return getById(ticketId);
    }

    @Override
    public void update(Ticket ticket) {
        String sql = "UPDATE ticket_seller.public.ticket_table SET (ticket_user_id, ticket_event_id, ticket_seat_id) = (?,?,?) WHERE ticket_id = ?";
        jdbcTemplate.update(sql, ticket.getUserId(), ticket.getEventId(), ticket.getSeat().getId(), ticket.getId());
    }

    @Override
    public void delete(Ticket ticket) {
        String sql = "DELETE FROM ticket_seller.public.ticket_table WHERE ticket_id = ?";
        jdbcTemplate.update(sql, ticket.getId());
    }

    @Override
    public List<Ticket> getAll() {
        String sql = "SELECT * FROM ticket_seller.public.ticket_table";
        return jdbcTemplate.query(sql, ticketRowMapper);
    }

    @Override
    public List<Ticket> getAllByUserId(long userId) {
        String sql = "SELECT * FROM ticket_seller.public.ticket_table WHERE ticket_user_id = ?";
        return jdbcTemplate.query(sql, ticketRowMapper, userId);
    }

    @Override
    public List<Ticket> getAllByEventId(long eventId) {
        String sql = "SELECT * FROM ticket_seller.public.ticket_table WHERE ticket_event_id = ?";
        return jdbcTemplate.query(sql, ticketRowMapper, eventId);
    }

    @Override
    public Ticket getById(long ticketId) {
        String sql = "SELECT * FROM ticket_seller.public.ticket_table WHERE ticket_id = ?";
        return jdbcTemplate.query(sql, ticketRowMapper, ticketId).iterator().next();
    }

    @Override
    public void deleteAllByUserId(long userId) {
        String sql = "DELETE FROM ticket_seller.public.ticket_table WHERE ticket_user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void deleteAllByEventId(long eventId) {
        String sql = "DELETE FROM ticket_seller.public.ticket_table WHERE ticket_event_id = ?";
        jdbcTemplate.update(sql, eventId);
    }
}
