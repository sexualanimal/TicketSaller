package ua.kozak.solowork.service;

public interface StorageService<T> {
    T write(T t);

    T read();

    boolean delete();

    boolean create(String fileName);
}
