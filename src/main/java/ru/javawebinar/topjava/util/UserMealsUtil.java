package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenInclusive;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate, List<UserMeal>> mapUserMeal = new HashMap<>();

        for (UserMeal meal : meals) {
            mapUserMeal.computeIfAbsent(meal.getDateTime().toLocalDate(), ld -> new ArrayList<>()).add(meal);
        }

        for (Map.Entry<LocalDate, List<UserMeal>> localDateListEntry : mapUserMeal.entrySet()) {
            final List<UserMeal> listUM = localDateListEntry.getValue();
            final int sum = listUM.stream().mapToInt(value -> value.getCalories()).sum();
            for (UserMeal userMeal : listUM) {
                if (isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                    result.add(new UserMealWithExcess(userMeal, sum > caloriesPerDay));
                }
            }
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByCycles2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate, Integer> mapUserMeal = new HashMap<>();

        for (UserMeal meal : meals) {
            mapUserMeal.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }

        for (UserMeal meal : meals) {
            if (isBetweenInclusive(meal.getTime(), startTime, endTime)) {
                result.add(new UserMealWithExcess(meal, mapUserMeal.get(meal.getDate()) > caloriesPerDay));
            }
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        final Map<LocalDate, Integer> collect = meals.stream().collect(
                Collectors.groupingBy(userMeal -> userMeal.getDate(), Collectors.summingInt(value -> value.getCalories()))  // мое 1 вариант
                //    Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))                                              // 2 вариант
                //    Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)                                                            // 3 вариант
        );
        return meals
                .stream()
                .filter(userMeal -> isBetweenInclusive(userMeal.getTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal, collect.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }
}
