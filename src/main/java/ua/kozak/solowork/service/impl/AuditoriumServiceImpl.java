package ua.kozak.solowork.service.impl;

import ua.kozak.solowork.domain.Auditorium;
import org.springframework.stereotype.Service;
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

//    @Override
//    public User save(User user) throws UserAlreadyExistsException {
//        if (checkUserUnique(user)) {
//            Set<User> buffUsers = getAll();
//            buffUsers.add(user);
//            storageService.write(buffUsers);
//            return getAll().stream()
//                    .filter(u -> u.getId() == user.getId() && u.getEmail().equals(user.getEmail()))
//                    .findFirst().orElse(user);
//        } else {
//            throw new UserAlreadyExistsException();
//        }
//    }
//
//    @Override
//    public Set<User> saveAll(Set<User> users, boolean overwrite) throws UserAlreadyExistsException {
//        boolean usersValid = users.stream()
//                .map(this::checkUserUnique)
//                .filter(b -> b.equals(false))
//                .collect(Collectors.toSet())
//                .isEmpty();
//        if (usersValid) {
//            if (overwrite) {
//                return storageService.write(users);
//            } else {
//                Set<User> buffUsers = getAll();
//                buffUsers.addAll(users);
//                return storageService.write(buffUsers);
//            }
//        } else {
//            throw new UserAlreadyExistsException();
//        }
//    }
//
//    @Override
//    public User getById(int id) throws UserNotExistsException {
//        return getAll().stream()
//                .filter(u -> u.getId() == id)
//                .findFirst()
//                .orElseThrow(UserNotExistsException::new);
//    }
//
//    @Override
//    public User getByEmail(String email) throws UserNotExistsException {
//        return getAll().stream()
//                .filter(u -> u.getEmail().equals(email))
//                .findFirst()
//                .orElseThrow(UserNotExistsException::new);
//    }
//
//    @Override
//    public Set<User> getAll() {
//        return storageService.read();
//    }
//
//    @Override
//    public User remove(User user) throws UserNotExistsException {
//        User buffUser = getAll().stream()
//                .filter(u -> u.getEmail().equals(user.getEmail()) && u.getId() == user.getId())
//                .findFirst()
//                .orElseThrow(UserNotExistsException::new);
//        Set<User> buffUsers = getAll();
//        buffUsers.remove(buffUser);
//        storageService.write(buffUsers);
//        return buffUser;
//    }

    @Override
    public Set<Auditorium> getAll() {
        return null;
    }

    @Override
    public Auditorium getByName(String name) {
        return null;
    }

    @Override
    public Auditorium save(Auditorium auditorium) {
        return null;
    }

    @Override
    public Set<Auditorium> saveAll(Set<Auditorium> auditoriums, boolean overwrite) {
        return null;
    }

    @Override
    public Auditorium remove(Auditorium auditorium) {
        return null;
    }

    private boolean checkAuditoriumUnique(Auditorium auditorium) {
        return getAll().stream()
                .filter(a -> a.getName().equals(auditorium.getName()))
                .collect(Collectors.toSet())
                .isEmpty();
    }
}
