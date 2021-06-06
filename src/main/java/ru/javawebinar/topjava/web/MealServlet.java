package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
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

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");

        if (action == null) {
            List<MealTo> mealToList = MealsUtil.filteredByStreams(MealsUtil.meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("meals", mealToList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }

        switch (Objects.requireNonNull(action)) {
            case "create":
                // TODO
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;

            case "update":
                // TODO
                request.getRequestDispatcher("/updateMeal.jsp").forward(request, response);
                break;

            case "delete":
                // TODO
                request.getRequestDispatcher("/deleteMeal.jsp").forward(request, response);
                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("enter doPost");

        // TODO
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        Meal meal = new Meal();

        if (action.equals("submit")) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            meal.setDateTime(LocalDateTime.parse(request.getParameter("date")));
            meal.setDescription(request.getParameter("description"));
            meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        }

        request.setAttribute("meal", meal);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
