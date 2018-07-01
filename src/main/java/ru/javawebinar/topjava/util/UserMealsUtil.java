package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println("cycles");
        for (UserMealWithExceed um : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)) {
            System.out.println(um.toString());
        }
        System.out.println("Stream API");
        List<UserMealWithExceed> mealWithList = getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealWithList.forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesOfDayMap = new HashMap<>();
        for (UserMeal um : mealList) {
            caloriesOfDayMap.merge(um.getDateTime().toLocalDate(), um.getCalories(), Integer::sum);
        }

        List<UserMealWithExceed> mealWithList = new ArrayList<>();
        for (UserMeal um : mealList) {
            if (TimeUtil.isBetween(um.getLocalTime(), startTime, endTime)) {
                mealWithList.add(
                        new UserMealWithExceed(
                                um.getDateTime(),
                                um.getDescription(),
                                um.getCalories(),
                                caloriesOfDayMap.get(um.getLocalDate()) > caloriesPerDay));
            }
        }
        return mealWithList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesOfDayMap = mealList
                .stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));

        return mealList
                .stream()
                .filter(mealWithExceed -> TimeUtil.isBetween(mealWithExceed.getLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesOfDayMap.get(meal.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
