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
        log.debug("enter doGet");

        String action = request.getParameter("action");

        if (action == null) {
            log.debug("redirect to meals");
            List<MealTo> mealToList = MealsUtil.filteredByStreams(MealsUtil.meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("meals", mealToList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }
        else {
            int id = Integer.parseInt(request.getParameter("id"));
            switch (Objects.requireNonNull(action)) {
                case "create":
                    log.debug("enter create");
                    Meal meal = new Meal();
                    meal.setId(MealsUtil.count.incrementAndGet());
                    MealsUtil.meals.add(meal);
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;

                case "update":
                    log.debug("enter update");
                    Meal mealToEdit = MealsUtil.meals.get(id);
                    MealsUtil.meals.set(id, mealToEdit);
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;

                case "delete":
                    log.debug("enter delete");
                    MealsUtil.meals.remove(id);
                    response.sendRedirect("/meals");
                    break;
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("enter doDelete");

        int id = Integer.parseInt(request.getParameter("id"));
        MealsUtil.meals.remove(id);
        response.sendRedirect("/meals");
    }

    @Override
    protected void  doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int id = Integer.parseInt(req.getParameter("id"));
//        User user = service.findUser(id);
//        user.setName(req.getParameter("name"));
//        user.setAge(Integer.parseInt(req.getParameter("age")));
//        service.updateUser(user);
//        resp.sendRedirect("/users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("enter doPost");
        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(MealsUtil.count.incrementAndGet());
        MealsUtil.meals.add(meal);
        response.sendRedirect("/meals");



        // TODO
//        request.setCharacterEncoding("UTF-8");
//        String action = request.getParameter("action");
//
//        Meal meal = new Meal();
//
//        if (action.equals("submit")) {
//            meal.setId(Integer.parseInt(request.getParameter("id")));
//            meal.setDateTime(LocalDateTime.parse(request.getParameter("date")));
//            meal.setDescription(request.getParameter("description"));
//            meal.setCalories(Integer.parseInt(request.getParameter("calories")));
//        }
//
//        request.setAttribute("meal", meal);
//        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
