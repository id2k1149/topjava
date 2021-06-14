package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    int userId = SecurityUtil.authUserId();

    {
        MealsUtil.meals.forEach(each -> save(userId, each));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (SecurityUtil.authUserId() == userId) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        if (SecurityUtil.authUserId() == userId) {
            return repository.remove(id) != null;
        } else return false;
    }

    @Override
    public Meal get(int userId, int id) {
        if (SecurityUtil.authUserId() == userId) {
            return repository.get(id);
        }
        else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (SecurityUtil.authUserId() == userId) {
            return repository.
                    values().
                    stream().
                    sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                    collect(Collectors.toList());
        }
        else return null;
    }
}