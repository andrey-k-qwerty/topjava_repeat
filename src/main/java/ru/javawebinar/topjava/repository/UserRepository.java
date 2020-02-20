package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository<I extends Number,T extends AbstractBaseEntity<I>> extends Repository<I,T>  {
    // null if not found
    User getByEmail(String email);


}