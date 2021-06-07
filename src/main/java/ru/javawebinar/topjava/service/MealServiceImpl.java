package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class MealServiceImpl implements MealService {
    public static AtomicInteger count = new AtomicInteger(1);

    public MealServiceImpl() {
    }

    @Override
    public void create(Meal meal) {
        meal.setId(count.incrementAndGet());
        MealsUtil.meals.add(meal);
    }

    @Override
    public void update(Meal meal) {
        Meal mealToEdit = MealsUtil.meals.get(meal.getId());
        MealsUtil.meals.set(meal.getId(), mealToEdit);
    }

    @Override
    public void delete(int id) {
        MealsUtil.meals.remove(id);
    }

    @Override
    public Meal getById(int id) {
        for (Meal meal : MealsUtil.meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }
}
