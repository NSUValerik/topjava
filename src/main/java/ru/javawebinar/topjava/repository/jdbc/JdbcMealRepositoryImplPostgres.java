package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class JdbcMealRepositoryImplPostgres<T extends LocalDateTime> extends JdbcMealRepositoryImpl {
    public JdbcMealRepositoryImplPostgres(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    Object getFormatDate(LocalDateTime dateTime) {
        return dateTime;
    }
}
