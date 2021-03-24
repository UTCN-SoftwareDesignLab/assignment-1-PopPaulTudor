package repository;

import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.Notification;

import java.sql.*;
import java.util.List;

import static database.Constants.Tables.ACTIVITY;

public class ActivityRepository {

    private final Connection connection;


    public ActivityRepository(Connection connection) {
        this.connection = connection;
    }

    public void create(Activity activity) {

        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + ACTIVITY + "` values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, activity.getUserId());
            insertUserStatement.setLong(2, activity.getDate());
            insertUserStatement.setString(3, activity.getAction());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long id = rs.getLong(1);
            activity.setId(id);

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public Notification<List<Activity>> getActivityOfEmployee(long userId) {
        Notification<List<Activity>> activityOfUserNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchActivitySQL = "Select * from `" + ACTIVITY + "` where `id`= " + userId + "''";
            ResultSet rs = statement.executeQuery(fetchActivitySQL);

            while (rs.next()) {
                Activity activity = new ActivityBuilder()
                        .setId(rs.getLong("id"))
                        .setUserId(userId)
                        .setDate(rs.getLong("date"))
                        .setAction(rs.getString("action"))
                        .build();

                activityOfUserNotification.getResult().add(activity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            activityOfUserNotification.addError("Something is wrong with the database");
        }
        return activityOfUserNotification;
    }
}
