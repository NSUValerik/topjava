package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDao;

    public MealServlet() {
        super();
        mealDao = new MealDao();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("insert")) {
            forward = "mealsAdd.jsp";
        } else if (action.equalsIgnoreCase("edit")) {
            forward = "mealsAdd.jsp";
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDao.getMealById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listUser")) {
            forward = "meals.jsp";
            List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(mealDao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealsWithExceed);
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.deleteMeal(id);
            forward = "meals.jsp";
            List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(mealDao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealsWithExceed);
        }
        RequestDispatcher rd = request.getRequestDispatcher(forward);
        rd.forward(request, response);
        log.debug("redirect to users");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            mealDao.addMeal(request.getParameter("dateTime"),
                    request.getParameter("description"),
                    request.getParameter("calories"));
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            mealDao.updateMeal(new Meal(Integer.parseInt(id),
                    LocalDateTime.parse(request.getParameter("dateTime"), formatter),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))
            ));
        }
        RequestDispatcher view = request.getRequestDispatcher("meals.jsp");
        List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(mealDao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals", mealsWithExceed);
        view.forward(request, response);
    }
}
