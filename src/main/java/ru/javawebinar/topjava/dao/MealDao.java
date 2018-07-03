package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MealDao {

    private Connection connection;

    public MealDao() {
        connection = DbUtil.getConnection();
    }

    public void addMeal(String dateTime, String description, String calories) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into meal(dateTime,description,calories) values (?, ?, ?)");

            preparedStatement.setString(1, dateTime);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, Integer.parseInt(calories));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMeal(int mealId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from meal where id=?");
            // Parameters start with 1
            preparedStatement.setInt(1, mealId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update meal set dateTime=?, description=?, calories=?" +
                            " where id=?");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = meal.getDateTime().format(dtf);

            preparedStatement.setString(1, formattedDateTime);
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.setInt(4, meal.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Meal> getAllMeals() {
        List<Meal> meals = new ArrayList<Meal>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from meal");
            while (rs.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Meal meal = new Meal(
                        rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("dateTime"), formatter),
                        rs.getString("description"),
                        rs.getInt("calories")
                );
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meals;
    }

    public Meal getMealById(int mealId) {
        Meal meal = null;
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from meal where id=?");
            preparedStatement.setInt(1, mealId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                meal = new Meal(
                        rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("dateTime"), formatter),
                        rs.getString("description"),
                        rs.getInt("calories")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meal;
    }
}