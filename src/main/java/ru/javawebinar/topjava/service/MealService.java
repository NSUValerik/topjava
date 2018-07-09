package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    Meal create(Meal meal, Integer userID);

    void delete(int id, Integer userID) throws NotFoundException;

    Meal get(int id, Integer userID) throws NotFoundException;

    void update(Meal meal, Integer userID);

    List<Meal> getAll(Integer userID);
}