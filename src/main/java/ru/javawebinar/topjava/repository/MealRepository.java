package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository<I extends Number,T extends AbstractBaseEntity<I>> extends Repository<I,T> {
    // null if not found, when updated
    T save(T meal,I id);

    // false if not found
    boolean delete(I id, I userID);

    // null if not found
    Meal get(I id, I userID);

    List<T> getAll(I userID);

}
