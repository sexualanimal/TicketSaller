package ua.kozak.solowork.service;

import ua.kozak.solowork.domain.Auditorium;
import ua.kozak.solowork.domain.exception.AuditoriumAlreadyExistsException;
import ua.kozak.solowork.domain.exception.AuditoriumNotExistsException;

import java.util.Set;

public interface AuditoriumService {
    String FILENAME = "auditorium_accounts_data";

    Set<Auditorium> getAll();

    Auditorium getByName(String name) throws AuditoriumNotExistsException;

    Auditorium getById(long id) throws AuditoriumNotExistsException;

    Auditorium save(Auditorium auditorium) throws AuditoriumAlreadyExistsException;

    Set<Auditorium> saveAll(Set<Auditorium> auditoriums, boolean overwrite) throws AuditoriumAlreadyExistsException;

    Auditorium remove(Auditorium auditorium) throws AuditoriumNotExistsException;

    boolean checkAuditoriumUnique(Auditorium auditorium);

    long getNextEmptyId();
}
