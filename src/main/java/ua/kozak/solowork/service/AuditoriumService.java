package ua.kozak.solowork.service;

import ua.kozak.solowork.domain.Auditorium;

import java.util.Set;

public interface AuditoriumService {
    String FILENAME = "auditorium_accounts_data";

    Set<Auditorium> getAll();

    Auditorium getByName(String name);

    Auditorium save(Auditorium auditorium);

    Set<Auditorium> saveAll(Set<Auditorium> auditoriums, boolean overwrite);

    Auditorium remove(Auditorium auditorium);
}
