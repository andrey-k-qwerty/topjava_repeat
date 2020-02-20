package ru.javawebinar.topjava.repository;

import java.util.Collection;

public interface Repository <I extends Number  ,T >  {
    // null if not found, when updated
    T save(T user);

    // false if not found
    boolean delete(I id);

    // null if not found
    T get(I id);

    Collection<T> getAll();
}
