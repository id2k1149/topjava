package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.LocalDataBase;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private List<Meal> meals;

    @Override
    public void init() {
        meals = new CopyOnWriteArrayList<>(LocalDataBase.getMealsDB());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("enter doGet");

        String action = request.getParameter("action");

        if (action == null) {
            log.debug("redirect to meals");
            List<MealTo> mealToList = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("meals", mealToList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            List<Meal> mealsCopy = new CopyOnWriteArrayList<>(meals);
            switch (Objects.requireNonNull(action)) {
                case "update":
                    log.debug("update meal with id = " + id);
                    request.getRequestDispatcher("/meal.jsp").forward(request, response);
                    break;
                case "delete":
                    log.debug("delete meal with id = " + id);
                    for (int i = 0; i < mealsCopy.size(); i++) {
                        if (mealsCopy.get(i).getId() == id) {
                            meals.remove(i);
                            break;
                        }
                    }
                    response.sendRedirect("meals");
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("enter doPost");
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            List<Meal> mealsCopy = new CopyOnWriteArrayList<>(meals);
            for (int i = 0; i < mealsCopy.size(); i++) {
                if (mealsCopy.get(i).getId() == id) {
                    Meal meal = new Meal(id, dateTime, description, calories);
                    log.debug("set a meal with id = " + id);
                    meals.set(i, meal);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            id = LocalDataBase.getCount().incrementAndGet();
            Meal meal = new Meal(id, dateTime, description, calories);
            log.debug("add a new meal with id = " + id);
            meals.add(meal);
        }
        doGet(request, response);
    }
}
