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

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        for ( UserMealWithExceed um : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000) )
        {
            System.out.println( um.getDateTime() + "  " + um.getDescription() + "  " + um.getCalories() + "  " + um.getExceed() );
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesOfDayMap = new HashMap<>();
        for( UserMeal um : mealList )
        {
            caloriesOfDayMap.merge( um.getDateTime().toLocalDate(), um.getCalories(), Integer::sum );
        }

        List<UserMealWithExceed> mealWithList = new ArrayList<>();
        for( UserMeal um : mealList )
        {
            if( um.getDateTime().toLocalTime().isAfter( startTime ) && um.getDateTime().toLocalTime().isBefore( endTime ) )
            {
                for( LocalDate ld : caloriesOfDayMap.keySet() )
                {
                    if( um.getDateTime().toLocalDate().equals( ld ) )
                    {
                        mealWithList.add(
                                new UserMealWithExceed(
                                        um.getDateTime(),
                                        um.getDescription(),
                                        um.getCalories(),
                                        caloriesOfDayMap.get( ld ) > caloriesPerDay ) );
                    }
                }
            }
        }
        return mealWithList;
    }
}
