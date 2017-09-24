package ua.kozak.solowork.dao;

import ua.kozak.solowork.domain.Auditory;

import java.util.List;
import java.util.Set;

public interface AuditoryDAO {

    Auditory add(Auditory auditory);

    void update(Auditory auditory);

    void delete(Auditory auditory);

    List<Auditory> getAll();

    List<Auditory> getByAllName(String name);

    Auditory getByLocation(String location);

    Auditory getById(long id);

}