package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
//    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id, @Param("userId") int userId);

    //  Проверить через запросы на быстроту выполнения
    Meal getMealByIdAndUserId(int id, int userId);

//    @Override
//    @Transactional
//    Meal save(Meal item);

    List<Meal> findAllByUserIdOrderByIdDesc(int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetweenHalfOpen(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("userId") int userId);
    List<Meal> findAllByUser_IdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(int user_id, LocalDateTime startTime, LocalDateTime endTime);
}
