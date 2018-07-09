package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

   /* {
        MealsUtil.MEALS.forEach(meal -> save(meal, null));
    }*/

    @Override
    public Meal save(Meal meal, Integer userID) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            log.info("save {}", meal);
            return meal;
        }
        if (meal.getUserId().equals(userID)) {
            // treat case: update, but absent in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, Integer userID) {
        if (!repository.get(id).getUserId().equals(userID)) {
            return false;
        }
        repository.remove(id);
        return true;
    }

    @Override
    public Meal get(int id, Integer userID) {
        List<Meal> meal = repository
                .values()
                .stream()
                .filter(m -> userID.equals(m.getUserId()) && m.getId() == id)
                .collect(Collectors.toList());
        if (meal.isEmpty()) {
            return null;
        }
        return meal.get(0);
    }

    @Override
    public List<Meal> getAll(Integer userID) {
        return repository
                .values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userID))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

