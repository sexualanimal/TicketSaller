package ua.kozak.solowork.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.TicketDAO;
import ua.kozak.solowork.dao.UserDAO;
import ua.kozak.solowork.dao.mapper.UserRowMapper;
import ua.kozak.solowork.domain.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDAOJdbcImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private UserRowMapper userRowMapper;

    @Override
    public User add(User user) {
        String sql = "INSERT INTO ticket_seller.public.user_table(user_first_name, user_last_name, user_email) VALUES(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement preparedStatement = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            return preparedStatement;
        }, keyHolder);

        Optional<List<Map<String, Object>>> keyList = Optional.ofNullable(keyHolder.getKeyList());
        long userId = keyList.map(item -> item.iterator().next().get("user_id")).map(id -> Long.valueOf((Integer) id)).orElse(0L);

        user.getTickets().forEach(ticket -> ticket.setUserId(userId));
        user.getTickets().stream()
                .filter(ticket -> ticket.getId() > 0)
                .forEach(ticketDAO::update);
        user.getTickets().stream()
                .filter(ticket -> ticket.getId() == 0)
                .forEach(ticketDAO::add);

        return userId == 0 ? null : getById(userId);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE ticket_seller.public.user_table SET (user_first_name, user_last_name, user_email) = (?,?,?) WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());

        user.getTickets().forEach(ticket -> ticket.setUserId(user.getId()));
        user.getTickets().stream()
                .filter(ticket -> ticket.getId() > 0)
                .forEach(ticketDAO::update);
        user.getTickets().stream()
                .filter(ticket -> ticket.getId() == 0)
                .forEach(ticketDAO::add);
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM ticket_seller.public.user_table WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getId());

        user.getTickets().stream()
                .peek(ticket -> ticket.setUserId(0))
                .forEach(ticketDAO::update);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM ticket_seller.public.user_table";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public User getById(long id) {
        String sql = "SELECT * FROM ticket_seller.public.user_table WHERE user_id = ?";
        return jdbcTemplate.query(sql, userRowMapper, id).iterator().next();
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM ticket_seller.public.user_table WHERE user_email = ?";
        return jdbcTemplate.query(sql, userRowMapper, email).iterator().next();
    }
}
