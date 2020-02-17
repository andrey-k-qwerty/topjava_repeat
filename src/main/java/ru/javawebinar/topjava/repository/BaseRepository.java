package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseRepository<I extends Number, T extends AbstractBaseEntity> implements Repository<I, T> {
    protected static final Logger log = LoggerFactory.getLogger(BaseRepository.class);
    // делать для каждого отдельный счетчик или общий?
    protected static AtomicInteger counter = new AtomicInteger(0);
    protected Map<I, T> repository = new ConcurrentHashMap<>();

    // null if not found, when updated
    public T save(T entity) {
        log.info("save {}", entity);
        if (entity.isNew()) {
            entity.setId(counter.incrementAndGet());
            repository.put((I) entity.getId(), entity);
            return entity;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent((I) entity.getId(), (id, oldMeal) -> entity);
    }

    // false if not found
    public boolean delete(I id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }


    // null if not found
    public T get(I id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    public Collection<T> getAll() {
        log.info("getAll");
        return repository.values();
    }
}
