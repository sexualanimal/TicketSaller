package ua.kozak.solowork.dao;

import ua.kozak.solowork.domain.Seat;

import java.util.List;

public interface SeatDAO {

    Seat add(Seat seat);

    void update(Seat seat);

    void delete(Seat seat);

    List<Seat> getAll();

    List<Seat> getByAuditoryId(long auditoryId);

    Seat getById(long id);

    void deleteAllByAuditoryId(long auditoryId);
}
