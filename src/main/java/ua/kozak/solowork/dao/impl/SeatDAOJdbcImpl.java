package ua.kozak.solowork.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.SeatDAO;
import ua.kozak.solowork.dao.mapper.SeatRowMapper;
import ua.kozak.solowork.domain.Seat;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SeatDAOJdbcImpl implements SeatDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SeatRowMapper seatRowMapper;

    @Override
    public Seat add(Seat seat) {
        String sql = "INSERT INTO ticket_seller.public.seat_table(seat_number, seat_auditory_id) VALUES(?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement preparedStatement = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, seat.getNumber());
            preparedStatement.setInt(2, (int) seat.getAuditoryId());
            return preparedStatement;
        }, keyHolder);

        Optional<List<Map<String, Object>>> keyList = Optional.ofNullable(keyHolder.getKeyList());
        keyList.map(item -> item.iterator().next().get("seat_id")).map(id -> new Long((Integer)id)).ifPresent(seat::setId);
        return seat;
    }

    @Override
    public void update(Seat seat) {
        String sql = "UPDATE ticket_seller.public.seat_table SET (seat_number, seat_auditory_id) = (?,?) WHERE seat_id = ?";
        jdbcTemplate.update(sql, seat.getNumber(), seat.getAuditoryId(), seat.getId());
    }

    @Override
    public void delete(Seat seat) {
        String sql = "DELETE FROM ticket_seller.public.seat_table WHERE seat_id = ?";
        jdbcTemplate.update(sql, seat.getId());
    }

    @Override
    public List<Seat> getAll() {
        String sql = "SELECT * FROM ticket_seller.public.seat_table";
        return jdbcTemplate.query(sql, seatRowMapper);
    }

    @Override
    public List<Seat> getByAuditoryId(long auditoryId) {
        String sql = "SELECT * FROM ticket_seller.public.seat_table WHERE seat_auditory_id = ?";
        return jdbcTemplate.query(sql, seatRowMapper, auditoryId);
    }

    @Override
    public Seat getById(long id) {
        String sql = "SELECT * FROM ticket_seller.public.seat_table WHERE seat_id = ?";
        return jdbcTemplate.query(sql, seatRowMapper, id).iterator().next();
    }

    @Override
    public void deleteAllByAuditoryId(long auditoryId) {
        String sql = "DELETE FROM ticket_seller.public.seat_table WHERE seat_auditory_id = ?";
        jdbcTemplate.update(sql, auditoryId);
    }
}
