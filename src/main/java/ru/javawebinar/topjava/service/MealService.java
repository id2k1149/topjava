package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

public interface MealService {
    Meal getById(int id);

    void create(Meal meal);

    void update(Meal meal);

    void delete(int id);
}
