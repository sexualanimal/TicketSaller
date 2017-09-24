//package ua.kozak.solowork.service;
//
//import ua.kozak.solowork.domain.User;
//
//import java.util.Set;
//
//public interface UserService {
//
//    User save(User user);
//
//    Set<User> saveAll(Set<User> users, boolean overwrite) throws UserAlreadyExistsException;
//
//    User getById(int id) throws UserNotExistsException;
//
//    User getByEmail(String email) throws UserNotExistsException;
//
//    Set<User> getAll();
//
//    User remove(User user) throws UserNotExistsException;
//
//    boolean checkUserUnique(User user);
//
//    long getNextEmptyId();
//}
