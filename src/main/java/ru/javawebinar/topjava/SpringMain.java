package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
/*            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
*//*
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //создадим админа
            adminUserController
                    .create(new User(null, "Bob", "user@user.com", "user123", Role.ROLE_USER));*//*

            ProfileRestController userController = appCtx.getBean(ProfileRestController.class);
            //создадим юзера
            User user = userController
                    .create(new User(null, "Bob", "user@user.com", "user123", Role.ROLE_USER));

            //создадим еду
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(
                    new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000));
            mealRestController.create(
                    new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 800));
            mealRestController.create(
                    new Meal(LocalDateTime.of(2015, Month.MAY, 30, 18, 0), "Ужин", 700));

            List<MealWithExceed> mealWithExceed = mealRestController
                    .getAllWithExceed(user.getCaloriesPerDay());

            //выведем еду юзера
            mealWithExceed.forEach(System.out::println);

            //удалим одну позицию еды
            System.out.println("удалим одну позицию еды");
            mealRestController.delete(mealWithExceed.get(0).getId());
            mealRestController
                    .getAllWithExceed(user.getCaloriesPerDay())
                    .forEach(System.out::println);

            List<Meal> meal = mealRestController.getAll();

            //создадим еще одного пользователя
            System.out.println("создадим еще одного пользователя");
            user = userController
                    .create(new User(null, "Alex", "alex@user.com", "alex", Role.ROLE_USER));
            SecurityUtil.setAuthUserId( 2 );
            //проверяем что под другим пользователем нельзя удалять еду первого
            try {
                mealRestController.delete(meal.get(0).getId());
            } catch (NotFoundException e) {
                System.out.println("Нельзя удалять еду другого пользователя \n" + e.getMessage());
            }

            //проверяем что под другим пользователем нельзя менять еду первого
            try {
                Meal oldMeal = meal.get(0);
                Meal newMeal = new Meal(oldMeal.getId(), oldMeal.getUserId(), oldMeal.getDateTime(), "Сломать чужое", 777);
                mealRestController.update(newMeal);
            } catch (NotFoundException e) {
                System.out.println("Нельзя изменить еду другого пользователя \n" + e.getMessage());
            }
            appCtx.close();*/
        }
    }
}
