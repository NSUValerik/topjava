package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int FIRST_MEAL_ID_USER = START_SEQ + 2;
    public static final int SECOND_MEAL_ID_USER = START_SEQ + 3;
    public static final int THIRD_MEAL_ID_USER = START_SEQ + 4;
    public static final int FIRST_MEAL_ID_ADMIN = START_SEQ + 5;
    public static final int SECOND_MEAL_ID_ADMIN = START_SEQ + 6;
    public static final int THIRD_MEAL_ID_ADMIN = START_SEQ + 7;
    public static final int FOURTH_MEAL_ID_ADMIN = START_SEQ + 8;

    public static final Meal FIRST_USER_MEAL = new Meal(FIRST_MEAL_ID_USER,
            LocalDateTime.of(2018, 7, 15, 9, 0, 0), "завтрак", 500);
    public static final Meal SECOND_USER_MEAL = new Meal(SECOND_MEAL_ID_USER,
            LocalDateTime.of(2018, 7, 15, 14, 0, 0), "обед", 1000);
    public static final Meal THIRD_USER_MEAL = new Meal(THIRD_MEAL_ID_USER,
            LocalDateTime.of(2018, 7, 15, 19, 0, 0), "ужин", 1000);
    public static final Meal FIRST_ADMIN_MEAL = new Meal(FIRST_MEAL_ID_ADMIN,
            LocalDateTime.of(2018, 7, 15, 10, 0, 0), "завтрак", 500);
    public static final Meal SECOND_ADMIN_MEAL = new Meal(SECOND_MEAL_ID_ADMIN,
            LocalDateTime.of(2018, 7, 15, 15, 0, 0), "обед", 1000);
    public static final Meal THIRD_ADMIN_MEAL = new Meal(THIRD_MEAL_ID_ADMIN,
            LocalDateTime.of(2018, 7, 15, 20, 0, 0), "ужин", 800);
    public static final Meal FOURTH_ADMIN_MEAL = new Meal(FOURTH_MEAL_ID_ADMIN,
            LocalDateTime.of(2018, 7, 15, 23, 0, 0), "ночной перекус", 800);
}
