package ua.kozak.solowork.service.impl;

import org.springframework.stereotype.Service;
import ua.kozak.solowork.domain.User;
import ua.kozak.solowork.domain.exception.UserAlreadyExistsException;
import ua.kozak.solowork.domain.exception.UserNotExistsException;
import ua.kozak.solowork.service.StorageService;
import ua.kozak.solowork.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private StorageService<Set<User>> storageService;

    public UserServiceImpl() {
        storageService = new StorageServiceImpl<>(FILENAME);
    }

    @Override
    public User save(User user) throws UserAlreadyExistsException {
        if (checkUserUnique(user)) {
            Set<User> buffUsers = getAll();
            buffUsers.add(user);
            storageService.write(buffUsers);
            return getAll().stream()
                    .filter(u -> u.getId() == user.getId() && u.getEmail().equals(user.getEmail()))
                    .findFirst().orElse(user);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public Set<User> saveAll(Set<User> users, boolean overwrite) throws UserAlreadyExistsException {
        boolean usersValid = users.stream()
                .map(this::checkUserUnique)
                .filter(b -> b.equals(false))
                .collect(Collectors.toSet())
                .isEmpty();
        if (usersValid) {
            if (overwrite) {
                return storageService.write(users);
            } else {
                Set<User> buffUsers = getAll();
                buffUsers.addAll(users);
                return storageService.write(buffUsers);
            }
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public User getById(int id) throws UserNotExistsException {
        return getAll().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(UserNotExistsException::new);
    }

    @Override
    public User getByEmail(String email) throws UserNotExistsException {
        return getAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(UserNotExistsException::new);
    }

    @Override
    public Set<User> getAll() {
        return storageService.read();
    }

    @Override
    public User remove(User user) throws UserNotExistsException {
        Set<User> buffUsers = getAll();
        boolean isDeleted = buffUsers.removeIf(m -> m.equals(user));
        if (!isDeleted) throw new UserNotExistsException();
        storageService.write(buffUsers);
        return user;
    }

    @Override
    public boolean checkUserUnique(User user) {
        return getAll().stream()
                .filter(u -> u.equals(user))
                .collect(Collectors.toSet())
                .isEmpty();
    }

    @Override
    public long getNextEmptyId() {
        return getAll().stream().mapToLong(User::getId).max().orElse(-1) + 1;
    }
}
