package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.DateTimeUtil.getEndExclusive;
import static ru.javawebinar.topjava.util.DateTimeUtil.getStartInclusive;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;


    @Test
    public void get() {
        Meal meal = service.get(START_SEQ, USER_ID);
        assertMatch(USER_MEALS.get(0),meal);
    }

    @Test
    public void delete() {
        service.delete(START_SEQ,USER_ID);
        final List<Meal> expected = USER_MEALS.stream()
                .filter(meal -> meal.getId() !=  START_SEQ)
                .sorted(Comparator.comparing(Meal::getId).reversed()).collect(Collectors.toList());
        assertMatch(service.getAll(USER_ID), expected);
    }

    @Test
    public void getBetweenHalfOpen() {
        LocalDate startDate = LocalDate.of(2020, Month.JANUARY, 30);
        LocalDate endDate = LocalDate.of(2020, Month.JANUARY,30);
        List<Meal> betweenDateTimes = service.getBetweenHalfOpen(startDate, endDate, USER_ID);
        List<Meal> mealFilterList = USER_MEALS.stream()
                .filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime(), getStartInclusive(startDate), getEndExclusive(endDate)))
                .sorted(Comparator.comparing(Meal::getId).reversed()).collect(Collectors.toList());
                 assertMatch(betweenDateTimes,mealFilterList);

    }

    @Test
    public void getAll() {
        final List<Meal> expected = USER_MEALS.stream()
                .sorted(Comparator.comparing(Meal::getId).reversed()).collect(Collectors.toList());
                 assertMatch(service.getAll(USER_ID), expected);
    }

    @Test
    public void update() {
        Meal meal = service.get(START_SEQ, USER_ID);
        meal.setDescription("Перекусон");
        meal.setCalories(3000);
        meal.setDateTime(LocalDateTime.now());
        service.update(meal,USER_ID);
        assertMatch(service.get(START_SEQ, USER_ID),meal);
    }

    @Test
    public void create() {
        Meal meal =  newMeal();
        Meal mealReturn = service.create(meal, ADMIN_ID);
        assertThat(mealReturn.getId()).isEqualTo(LAST_SEQ_ID + 1);
        assertThat(mealReturn.getDescription()).isEqualTo(meal.getDescription());
        assertThat(mealReturn.getCalories()).isEqualTo(meal.getCalories());
    }


}