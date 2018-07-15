package ru.javawebinar.topjava.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserRepositoryImpl;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealRepositoryTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private JdbcMealRepositoryImpl repository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void setUp() throws Exception {
        repository = new JdbcMealRepositoryImpl(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getBetween() {
    }
}