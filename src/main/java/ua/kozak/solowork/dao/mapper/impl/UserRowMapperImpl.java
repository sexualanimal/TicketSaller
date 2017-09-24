package ua.kozak.solowork.dao.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.TicketDAO;
import ua.kozak.solowork.dao.mapper.UserRowMapper;
import ua.kozak.solowork.domain.Ticket;
import ua.kozak.solowork.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserRowMapperImpl implements UserRowMapper {

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        final User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("user_email"));
        user.setFirstName(resultSet.getString("user_first_name"));
        user.setLastName(resultSet.getString("user_last_name"));
        List<Ticket> tickets = new ArrayList<>();
        tickets.addAll(ticketDAO.getByUserId(resultSet.getInt("user_id")));
        user.setTickets(tickets);
        return user;
    }
}
