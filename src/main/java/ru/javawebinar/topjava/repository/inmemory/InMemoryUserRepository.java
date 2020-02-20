package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.BaseRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository extends BaseRepository<Integer,User> implements UserRepository<Integer,User>{
    private   AtomicInteger counter;
     {
       counter = new AtomicInteger(0);
       save(new User(null, "admin", "admin@mail.ru", "password", Role.ROLE_ADMIN));
       save(new User(null, "user", "user@mail.ru", "password", Role.ROLE_USER));
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().get();
    }

    @Override
    public Integer nextID() {
        return counter.incrementAndGet();
    }
}
