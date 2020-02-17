package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseRepository  implements Repository {
    // делать для каждого отдельный счетчик или общий?
    private static AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, T> repository = new ConcurrentHashMap<>();

    // null if not found, when updated
    T save(T entity) {
        if (entity.isNew()) {
            entity.setId(counter.incrementAndGet());
            repository.put(entity.getId(), entity);
            return entity;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(entity.getId(), (id, oldMeal) -> entity);
    }

    // false if not found
    boolean delete(I id) {
        return repository.remove(id) != null;
    }


    // null if not found
    T get(I id) {
      return repository.get(id) ;
    }

    Collection<T> getAll() {
        return repository.values();
    }
}
