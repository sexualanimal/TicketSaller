package ua.kozak.solowork.dao;

import ua.kozak.solowork.domain.User;

import java.util.List;

public interface UserDAO {

    User add(User user);

    void update(User user);

    void delete(User user);

    List<User> getAll();

    User getById(long id);

    User getByEmail(String email);
}