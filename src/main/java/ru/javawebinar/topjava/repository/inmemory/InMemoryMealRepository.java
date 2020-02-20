package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.BaseRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository extends BaseRepository<Integer, Meal> implements MealRepository {
    {
        counter = new AtomicInteger(0);
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак", 500), 2);
        save(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед", 1000), 2);
        save(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин", 500), 2);


    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew() || (get(meal.getId(), userId) != null)) {
            meal.setUserID(userId);
            return save(meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userID) {
        return get(id, userID) != null && (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userID) {
        final Meal meal = get(id);
        return meal != null && meal.getId() == id ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userID) {
        return getAll().stream()
                //   return repository.values().stream()
                .filter(meal -> meal.getUserID() != null && meal.getUserID() == userID)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

