package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;


    @Autowired
    private CrudUserRepository userRepository;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(userRepository.getOne(userId));
        return crudRepository.save(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id,userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
//        1-й вариант
//        final Optional<Meal> byId = crudRepository.findById(id);
//        return byId.isPresent() && byId.get().getId() == userId ? byId.get() : null;

//        2-й вариант
       return  crudRepository.getMealByIdAndUserId(id,userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(startDateTime,endDateTime,userId);
   //     return crudRepository.findAllByUser_IdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(userId,startDateTime,endDateTime);
    }
}
