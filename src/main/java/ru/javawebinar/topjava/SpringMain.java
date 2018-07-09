package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
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
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
/*
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //создадим админа
            adminUserController
                    .create(new User(null, "Bob", "user@user.com", "user123", Role.ROLE_USER));*/

            ProfileRestController userController = appCtx.getBean(ProfileRestController.class);
            //создадим юзера
            userController
                    .create(new User(null, "Bob", "user@user.com", "user123", Role.ROLE_USER));
            //вазьмем аторизированного юзера
            User user = userController.get();

            //создадим еду пользователю
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(
                    new Meal(user.getId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000),
                    user.getId());
            mealRestController.create(
                    new Meal(user.getId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 800),
                    user.getId());
            mealRestController.create(
                    new Meal(user.getId(), LocalDateTime.of(2015, Month.MAY, 30, 18, 0), "Ужин", 700),
                    user.getId());

            //выведем еду юзера
            mealRestController
                    .getAll(user.getId(),
                            user.getCaloriesPerDay())
                    .forEach(System.out::println);

            //удалим одну позицию еды
            List<MealWithExceed> mealWithExceed = mealRestController
                    .getAllWithExceed(user.getId(),
                            user.getCaloriesPerDay());

            mealRestController.delete(mealWithExceed.get(0).getId(), user.getId());

            mealRestController
                    .getAllWithExceed(user.getId(),
                            user.getCaloriesPerDay())
                    .forEach(System.out::println);

            List<Meal> meal = mealRestController
                    .getAll(user.getId(),
                            user.getCaloriesPerDay());

            //создадим еще одного пользователя
            user = userController
                    .create(new User(null, "Alex", "alex@user.com", "alex", Role.ROLE_USER));
            //проверяем что под другим пользователем нельзя удалять еду первого
            try {
                mealRestController.delete(meal.get(0).getId(), user.getId());
            } catch (NotFoundException e) {
                System.out.println("Нельзя удалять еду другого пользователя \n" + e.getMessage());
            }

            //проверяем что под другим пользователем нельзя менять еду первого
            try {
                Meal oldMeal = meal.get(0);
                Meal newMeal = new Meal(oldMeal.getId(), oldMeal.getUserId(), oldMeal.getDateTime(), "Сломать чужое", 777);
                mealRestController.update(newMeal, user.getId());
            } catch (NotFoundException e) {
                System.out.println("Нельзя изменить еду другого пользователя \n" + e.getMessage());
            }
        }
    }
}
