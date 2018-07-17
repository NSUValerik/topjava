package ru.javawebinar.topjava.service;

import org.assertj.core.internal.FieldByFieldComparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(SECOND_MEAL_ID_USER, USER_ID);
        assertThat(meal).usingComparator(new FieldByFieldComparator()).isEqualTo(SECOND_USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundGet() throws Exception {
        service.get(1, 1);
    }

    @Test(expected = NotFoundException.class)
    public void getOther() throws Exception {
        service.get(FOURTH_MEAL_ID_ADMIN, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(FIRST_MEAL_ID_USER, USER_ID);
        assertThat(service.getAll(USER_ID)).doesNotContain(FIRST_USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1, 1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOther() throws Exception {
        service.delete(FIRST_MEAL_ID_USER, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> mealBetweenDates = service.getBetweenDates(LocalDate.of(2018, 7, 15),
                LocalDate.of(2018, 7, 15), ADMIN_ID);
        assertThat(mealBetweenDates).containsExactly(FOURTH_ADMIN_MEAL, THIRD_ADMIN_MEAL, SECOND_ADMIN_MEAL, FIRST_ADMIN_MEAL);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> mealsBetweenDateTimes = service.getBetweenDateTimes(LocalDateTime.of(2018, 7, 15, 9, 0, 0),
                LocalDateTime.of(2018, 7, 15, 14, 0, 0), USER_ID);
        assertThat(mealsBetweenDateTimes).containsExactly(SECOND_USER_MEAL, FIRST_USER_MEAL);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertThat(meals).doesNotContain(FIRST_ADMIN_MEAL, SECOND_ADMIN_MEAL, THIRD_ADMIN_MEAL, FOURTH_ADMIN_MEAL);
    }

    @Test
    public void update() throws Exception {
        Meal meal = FIRST_USER_MEAL;
        meal.setCalories(3000);
        meal.setDescription("update");
        meal.setDateTime(LocalDateTime.of(2099, 12, 31, 23, 59, 59));
        service.update(meal, USER_ID);
        assertThat(meal).usingComparator(new FieldByFieldComparator()).isEqualTo(service.get(FIRST_MEAL_ID_USER, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() throws Exception {
        Meal meal = FIRST_USER_MEAL;
        meal.setId(START_SEQ + 9);
        service.update(meal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateOther() throws Exception {
        service.update(THIRD_USER_MEAL, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.of(2099, 12, 31, 23, 59, 59), "create", 777);
        service.create(meal, ADMIN_ID);
        meal.setId(START_SEQ + 9);
        assertThat(meal).usingComparator(new FieldByFieldComparator()).isEqualTo(service.get(START_SEQ + 9, ADMIN_ID));
    }

}