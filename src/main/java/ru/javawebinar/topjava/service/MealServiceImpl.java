package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, Integer userID) {
        return repository.save(meal, userID);
    }

    @Override
    public void delete(int id, Integer userID) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userID), id);
    }

    @Override
    public Meal get(int id, Integer userID) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userID), id);
    }

    @Override
    public List<Meal> getAll(Integer userID) {
        return repository.getAll(userID);
    }

    @Override
    public void update(Meal meal, Integer userID) {
        checkNotFoundWithId(repository.save(meal, userID), meal.getId());
    }

}