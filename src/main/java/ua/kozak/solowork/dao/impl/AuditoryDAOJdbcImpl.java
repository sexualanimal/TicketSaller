package ua.kozak.solowork.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.AuditoryDAO;
import ua.kozak.solowork.dao.EventDAO;
import ua.kozak.solowork.dao.SeatDAO;
import ua.kozak.solowork.dao.mapper.AuditoryRowMapper;
import ua.kozak.solowork.domain.Auditory;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuditoryDAOJdbcImpl implements AuditoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private SeatDAO seatDAO;

    @Autowired
    private AuditoryRowMapper auditoryRowMapper;

    @Override
    public Auditory add(Auditory auditory) {
        String sql = "INSERT INTO ticket_seller.public.auditory_table(auditory_name, auditory_location) VALUES(?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement preparedStatement = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, auditory.getName());
            preparedStatement.setString(2, auditory.getLocation());
            return preparedStatement;
        }, keyHolder);

        Optional<List<Map<String, Object>>> keyList = Optional.ofNullable(keyHolder.getKeyList());
        keyList.map(item -> item.iterator().next().get("auditory_id")).map(id -> new Long((Integer) id)).ifPresent(id -> {
            auditory.setId(id);
            seatDAO.deleteAllByAuditoryId(id);
        });

        auditory.getSeats().forEach(seatDAO::add);

        getById(auditory.getId()).getEvents().forEach(e -> {
            e.setAuditoryId(0);
            eventDAO.update(e);
        });
        auditory.getEvents().stream().filter(e -> e.getId() > 0).forEach(eventDAO::update);
        auditory.getEvents().stream().filter(e -> e.getId() == 0).forEach(eventDAO::add);

        return auditory;
    }

    @Override
    public void update(Auditory auditory) {
        getById(auditory.getId()).getEvents().forEach(e -> {
            e.setAuditoryId(0);
            eventDAO.update(e);
        });
        auditory.getEvents().stream().filter(e -> e.getId() > 0).forEach(eventDAO::update);
        auditory.getEvents().stream().filter(e -> e.getId() == 0).forEach(eventDAO::add);

        seatDAO.deleteAllByAuditoryId(auditory.getId());
        auditory.getSeats().forEach(seatDAO::add);

        String sql = "UPDATE ticket_seller.public.auditory_table SET (auditory_name, auditory_location) = (?,?) WHERE auditory_id = ?";
        jdbcTemplate.update(sql, auditory.getName(), auditory.getLocation(), auditory.getId());
    }

    @Override
    public void delete(Auditory auditory) {
        getById(auditory.getId()).getEvents().forEach(e -> {
            e.setAuditoryId(0);
            eventDAO.update(e);
        });

        String sql = "DELETE FROM ticket_seller.public.auditory_table WHERE auditory_id = ?";
        jdbcTemplate.update(sql, auditory.getId());

        seatDAO.deleteAllByAuditoryId(auditory.getId());
    }

    @Override
    public List<Auditory> getAll() {
        String sql = "SELECT * FROM ticket_seller.public.auditory_table";
        return jdbcTemplate.query(sql, auditoryRowMapper);
    }

    @Override
    public List<Auditory> getByAllName(String name) {
        String sql = "SELECT * FROM ticket_seller.public.auditory_table WHERE auditory_name = ?";
        return jdbcTemplate.query(sql, auditoryRowMapper, name);
    }

    @Override
    public Auditory getByLocation(String location) {
        String sql = "SELECT * FROM ticket_seller.public.auditory_table WHERE auditory_location = ?";
        return jdbcTemplate.query(sql, auditoryRowMapper, location).iterator().next();
    }

    @Override
    public Auditory getById(long id) {
        String sql = "SELECT * FROM ticket_seller.public.auditory_table WHERE auditory_id = ?";
        return jdbcTemplate.query(sql, auditoryRowMapper, id).iterator().next();
    }
}
