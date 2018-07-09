package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

@Controller
public class MealRestController {

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal, Integer userID) {
        return service.create(meal, userID);
    }

    public void delete(int id, Integer userID) {
        service.delete(id, userID);
    }

    public void update(Meal meal, Integer userID) {
        service.update(meal, userID);
    }

    public List<Meal> getAll(Integer userID, int userCalories) {
        return service.getAll(userID);
    }

    public List<MealWithExceed> getAllWithExceed(Integer userID, int userCalories) {
        return MealsUtil.getWithExceeded(service.getAll(userID), userCalories);
    }
}