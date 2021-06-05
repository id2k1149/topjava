package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> dateTotalCaloriesMap = new HashMap<>();
        meals.forEach(meal ->
                dateTotalCaloriesMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        List<UserMealWithExcess> userMealWithExcessesList = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalTime localTime = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(localTime, startTime, endTime)) {
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        dateTotalCaloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);
                userMealWithExcessesList.add(userMealWithExcess);
            }
        }

        return userMealWithExcessesList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Implement by streams
        Map<LocalDate, Integer> dateTotalCaloriesMap = meals.stream()
                .collect(Collectors.groupingBy(eachDay -> eachDay.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(time -> TimeUtil.isBetweenHalfOpen(time.getDateTime().toLocalTime(), startTime, endTime))
                .map(e -> new UserMealWithExcess(e.getDateTime(),
                        e.getDescription(),
                        e.getCalories(),
                        dateTotalCaloriesMap.get(e.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}