package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.List;

public interface Repository <I extends Number,T extends AbstractBaseEntity>  {
    // null if not found, when updated
    T save(T user);

    // false if not found
    boolean delete(I id);

    // null if not found
    T get(I id);

    Collection<I> getAll();
}
