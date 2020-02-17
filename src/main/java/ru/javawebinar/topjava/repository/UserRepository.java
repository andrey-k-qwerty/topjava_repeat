package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends Repository<Integer,User>  {
    // null if not found
    User getByEmail(String email);


}