package ua.kozak.solowork.service.impl;

import org.springframework.stereotype.Service;
import ua.kozak.solowork.domain.Auditorium;
import ua.kozak.solowork.domain.exception.AuditoriumAlreadyExistsException;
import ua.kozak.solowork.domain.exception.AuditoriumNotExistsException;
import ua.kozak.solowork.service.AuditoriumService;
import ua.kozak.solowork.service.StorageService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private StorageService<Set<Auditorium>> storageService;

    public AuditoriumServiceImpl() {
        storageService = new StorageServiceImpl<>(FILENAME);
    }

    @Override
    public Set<Auditorium> getAll() {
        return storageService.read();
    }

    @Override
    public Auditorium getByName(String name) throws AuditoriumNotExistsException {
        return getAll().stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElseThrow(AuditoriumNotExistsException::new);
    }

    @Override
    public Auditorium getById(long id) throws AuditoriumNotExistsException {
        return getAll().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(AuditoriumNotExistsException::new);
    }

    @Override
    public Auditorium save(Auditorium auditorium) throws AuditoriumAlreadyExistsException {
        if (checkAuditoriumUnique(auditorium)) {
            Set<Auditorium> buffAuditoriums = getAll();
            buffAuditoriums.add(auditorium);
            storageService.write(buffAuditoriums);
            return getAll().stream()
                    .filter(u -> u.equals(auditorium))
                    .findFirst().orElse(auditorium);
        } else {
            throw new AuditoriumAlreadyExistsException();
        }
    }

    @Override
    public Set<Auditorium> saveAll(Set<Auditorium> auditoriums, boolean overwrite) throws AuditoriumAlreadyExistsException {
        boolean auditoriumsValid = auditoriums.stream()
                .map(this::checkAuditoriumUnique)
                .filter(b -> b.equals(false))
                .collect(Collectors.toSet())
                .isEmpty();
        if (auditoriumsValid) {
            if (overwrite) {
                return storageService.write(auditoriums);
            } else {
                Set<Auditorium> buffAuditoriums = getAll();
                buffAuditoriums.addAll(auditoriums);
                return storageService.write(buffAuditoriums);
            }
        } else {
            throw new AuditoriumAlreadyExistsException();
        }
    }

    @Override
    public Auditorium remove(Auditorium auditorium) throws AuditoriumNotExistsException {
        Set<Auditorium> buffAuditoriums = getAll();
        boolean isDeleted = buffAuditoriums.removeIf(m -> m.equals(auditorium));
        if (!isDeleted) throw new AuditoriumNotExistsException();
        storageService.write(buffAuditoriums);
        return auditorium;
    }

    @Override
    public boolean checkAuditoriumUnique(Auditorium auditorium) {
        return getAll().stream()
                .filter(a -> a.equals(auditorium))
                .collect(Collectors.toSet())
                .isEmpty();
    }

    @Override
    public long getNextEmptyId() {
        return getAll().stream().mapToLong(Auditorium::getId).max().orElse(-1) + 1;
    }
}
