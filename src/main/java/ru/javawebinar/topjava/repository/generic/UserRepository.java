package ru.javawebinar.topjava.repository.generic;


import ru.javawebinar.topjava.model.generic.AbstractBaseEntity;
import ru.javawebinar.topjava.model.generic.User;

public interface UserRepository<I extends Number,T extends AbstractBaseEntity<I>> extends Repository<I,T>  {
    // null if not found
    T getByEmail(String email);


}