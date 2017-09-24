package ua.kozak.solowork.dao.mapper.impl;

import org.springframework.stereotype.Service;
import ua.kozak.solowork.dao.mapper.SeatRowMapper;
import ua.kozak.solowork.domain.Seat;
import ua.kozak.solowork.domain.SeatType;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SeatRowMapperImpl implements SeatRowMapper {

    @Override
    public Seat mapRow(ResultSet resultSet, int i) throws SQLException {
        final Seat seat = new Seat();
        seat.setId(resultSet.getInt("seat_id"));
        seat.setAuditoryId(resultSet.getInt("seat_auditory_id"));
        seat.setNumber(resultSet.getInt("seat_number"));
        seat.setType(SeatType.regural); //fixme
        return seat;
    }
}
