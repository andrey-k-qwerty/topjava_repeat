package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.BaseRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@Repository
public class InMemoryUserRepository extends BaseRepository<Integer,User> implements UserRepository{

     {
       save(new User(1, "admin", "admin@mail.ru", "password", Role.ROLE_ADMIN));
       save(new User(2, "user", "user@mail.ru", "password", Role.ROLE_USER));
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().get();
    }
}
