package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private static int FIRST_MEAL_ID_USER = 100002;
    private static int FIRST_MEAL_ID_ADMIN = 100004;
    private static Meal FIRST_USER_MEAL = new Meal(FIRST_MEAL_ID_USER,
            LocalDateTime.of(2017, 7, 15, 9, 0, 0), "завтрак", 500 );
    private static Meal FIRST_ADMIN_MEAL = new Meal(FIRST_MEAL_ID_ADMIN,
            LocalDateTime.of(2017, 7, 15, 9, 0, 0), "завтрак", 500 );


    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(FIRST_MEAL_ID_USER, USER_ID);
        //assertThat(meal).isEqualToIgnoringGivenFields(FIRST_MEAL, "userId");
        assertThat(meal).isEqualTo(FIRST_USER_MEAL);
    }

    @Test
    public void delete() {
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void create() {
    }
}