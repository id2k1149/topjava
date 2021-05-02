package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        for (UserMeal eachDayMeal : meals) {
            if (dayCalories.containsKey(eachDayMeal.getDateTime().toLocalDate())) {
                Integer sumOfCalories = dayCalories.get(eachDayMeal.getDateTime().toLocalDate());
                sumOfCalories += eachDayMeal.getCalories();
                dayCalories.put(eachDayMeal.getDateTime().toLocalDate(), sumOfCalories);
            } else {
                dayCalories.put(eachDayMeal.getDateTime().toLocalDate(), eachDayMeal.getCalories());
            }
        }

        Map<LocalDate, Boolean> dayExcess = new HashMap<>();
        for (Map.Entry<LocalDate, Integer> pair : dayCalories.entrySet()) {
            LocalDate key = pair.getKey();
            Integer value = pair.getValue();
            if (value > caloriesPerDay) {
                dayExcess.put(key, true);
            } else {
                dayExcess.put(key, false);
            }
        }

        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal eachDayMeal : meals) {
            LocalTime timeOfMeal = eachDayMeal.getDateTime().toLocalTime();
            if (timeOfMeal.isAfter(startTime) && timeOfMeal.isBefore(endTime)) {
                LocalDateTime dateTime = eachDayMeal.getDateTime();
                String description = eachDayMeal.getDescription();
                int calories = eachDayMeal.getCalories();
                boolean excess = dayExcess.get(eachDayMeal.getDateTime().toLocalDate());
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(dateTime, description, calories, excess);
                result.add(userMealWithExcess);
            }
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
