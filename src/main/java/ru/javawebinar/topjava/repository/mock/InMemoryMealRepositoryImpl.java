package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setUserId(SecurityUtil.authUserId());
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            log.info("save {}", meal);
            return meal;
        }
        // treat case: update, but absent in storage
        if (meal.getUserId() == SecurityUtil.authUserId()) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (!repository.get(id).getUserId().equals(SecurityUtil.authUserId())) {
            return false;
        }
        repository.remove(id);
        return true;
    }

    @Override
    public Meal get(int id) {
        List<Meal> meal = repository
                .values()
                .stream()
                .filter(m -> m.getUserId() == SecurityUtil.authUserId() && m.getId() == id)
                .collect(Collectors.toList());
        if (meal.isEmpty()) {
            return null;
        }
        return meal.get(0);
    }

    @Override
    public List<Meal> getAll() {
        return repository
                .values()
                .stream()
                .filter(meal -> meal.getUserId() == SecurityUtil.authUserId())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

