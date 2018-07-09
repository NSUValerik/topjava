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

    public Meal create(Meal meal) {
        return service.create(meal);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public void update(Meal meal) {
        service.update(meal);
    }

    public List<Meal> getAll() {
        return service.getAll();
    }

    public List<MealWithExceed> getAllWithExceed(int userCalories) {
        return MealsUtil.getWithExceeded(service.getAll(), userCalories);
    }
}