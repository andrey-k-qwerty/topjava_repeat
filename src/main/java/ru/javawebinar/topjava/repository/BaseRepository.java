package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseRepository<I extends Number, T extends AbstractBaseEntity> implements Repository<I, T> {
    protected  final Logger log = LoggerFactory.getLogger(getClass());
    // делать для каждого отдельный счетчик или общий? - решил каждому отдельно
    protected  AtomicInteger counter;// = new AtomicInteger(0);
    protected Map<Integer, T> repository = new ConcurrentHashMap<>();

    // null if not found, when updated
    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        log.info("save {}", entity);
        if (Objects.requireNonNull(entity, "Entity must not be null").isNew()) {
            entity.setId(nextID());
            repository.put(entity.getId(), entity);
            return entity;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(entity.getId(), (id, oldMeal) -> entity);
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
    // через это метод можно полностью сделать абстрактный дженериковый репозиторий
    // помечаем его как обстрактный и реализуем его у дитей
    public int nextID() {
       return counter.incrementAndGet();
    }
}
