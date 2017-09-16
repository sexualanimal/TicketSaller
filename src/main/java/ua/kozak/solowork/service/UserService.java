package ua.kozak.solowork.service;

import ua.kozak.solowork.domain.User;
import ua.kozak.solowork.domain.exception.UserAlreadyExistsException;
import ua.kozak.solowork.domain.exception.UserNotExistsException;

import java.util.Set;

public interface UserService {
    String FILENAME = "user_accounts_data";

    User save(User user) throws UserAlreadyExistsException;

    Set<User> saveAll(Set<User> users, boolean overwrite) throws UserAlreadyExistsException;

    User getById(int id) throws UserNotExistsException;

    User getByEmail(String email) throws UserNotExistsException;

    Set<User> getAll();

    User remove(User user) throws UserNotExistsException;

    boolean checkUserUnique(User user);

    long getNextEmptyId();
}
