package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LocalDataBase {
    private static AtomicInteger count = new AtomicInteger(0);;

    private static List<Meal> mealsDB = Arrays.asList(
            new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static List<Meal> getMealsDB() {
        return mealsDB;
    }

    public static AtomicInteger getCount() {
        return count;
    }
}
