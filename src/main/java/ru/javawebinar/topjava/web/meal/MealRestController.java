package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }


    public Meal create(Meal meal) {
        int authUserId = SecurityUtil.authUserId();
        checkNew(meal);
        log.info("create {} for user {}", meal, authUserId);
        return service.create(meal, authUserId);
    }

    public Meal get(int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, authUserId);
        return service.get(id, authUserId);
    }

    public void update(Meal meal, int id) {
        int authUserId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, authUserId);
        service.update(meal, authUserId);
    }

    public void delete(int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, authUserId);
        service.delete(id, authUserId);
    }

    public List<MealTo> getAll() {
        int authUserId = SecurityUtil.authUserId();
        log.info("getAll for user {}", authUserId);
        return MealsUtil.getTos(service.getAll(authUserId), SecurityUtil.authUserCaloriesPerDay());
    }

  /*  List<MealTo> filterByDateTime(LocalDate beginData, LocalDate endData, LocalTime beginTime, LocalTime endTime) {
        int authUserId = SecurityUtil.authUserId();
        log.info("filterByDateTime  beginData {}, endData {}, beginTime {}, endTime {} ", beginData, endData, beginTime, endTime);
        List<Meal> meals = service.filterByDateTime(beginData, endData, LocalTime.MIN, LocalTime.MAX, authUserId);

        return  MealsUtil.getFilteredWithExcess(meals,SecurityUtil.authUserCaloriesPerDay(),beginTime,endTime);
    }*/

}