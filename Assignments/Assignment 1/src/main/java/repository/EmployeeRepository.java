package repository;

import model.Employee;
import model.builder.EmployeeBuilder;
import model.validation.Notification;

import java.sql.*;

import static database.Constants.Tables.ACTIVITY;
import static database.Constants.Tables.EMPLOYEE;

public class EmployeeRepository {


    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public EmployeeRepository(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    public Boolean save(Employee employee) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + EMPLOYEE + "` values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, employee.getUsername());
            insertUserStatement.setString(2, employee.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            employee.setId(userId);

            rightsRolesRepository.addRolesToUser(employee, employee.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public Notification<Boolean> delete(String username) {
        Notification<Boolean> deleteEmployee = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String deleteEmployeeSQL = "DELETE from `" + EMPLOYEE + "` where `username`='" + username +
                    "'";

            statement.executeUpdate(deleteEmployeeSQL);
            deleteEmployee.setResult(true);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            deleteEmployee.addError("Something went wrong.");
        }


        return deleteEmployee;
    }

    public Notification<Boolean> update(String username, String encodePassword, String newPassword) {
        Notification<Boolean> updateEmployee = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String updateEmployeeSQL = "UPDATE `" + EMPLOYEE +
                    "` SET `password` ='" + newPassword + "' WHERE  `username`='" + username +
                    "' and `password`='" + encodePassword + "'";

            statement.executeUpdate(updateEmployeeSQL);
            updateEmployee.setResult(true);

        } catch (SQLException throwables) {
            updateEmployee.addError("Something went wrong with the update");

            throwables.printStackTrace();
        }

        return updateEmployee;
    }

    public Notification<Employee> findByUsernameAndPassword(String username, String encodePassword) {
        Notification<Employee> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchEmployeeSql = "Select * from `" + EMPLOYEE + "` where `username`='" + username +
                    "' and `password`='" + encodePassword + "'";
            ResultSet userResultSet = statement.executeQuery(fetchEmployeeSql);
            if (userResultSet.next()) {
                Employee employee = new EmployeeBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(employee);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }


    public Notification<Employee> findEmployeeFromUsername(String username) {
        Notification<Employee> notification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchEmployeeSql = "Select * from `" + EMPLOYEE + "` where `username`='" + username + "'";
            ResultSet userResultSet = statement.executeQuery(fetchEmployeeSql);
            if (userResultSet.next()) {
                Employee employee = new EmployeeBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                notification.setResult(employee);
                return notification;
            } else {
                notification.addError("Invalid email or password!");
                return notification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something is wrong with the Database");
        }
        return notification;
    }


    public Notification<String> retrieve() {
        StringBuilder result = new StringBuilder();
        Notification<String> retrieveAllEmployee = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchEmployeesSql = "Select * from `" + EMPLOYEE + "`";
            ResultSet userResultSet = statement.executeQuery(fetchEmployeesSql);

            while (userResultSet.next()) {
                result.append(userResultSet.getString("username")).append("\n");
            }
            retrieveAllEmployee.setResult(result.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            retrieveAllEmployee.addError("Something went wrong.");

        }

        return retrieveAllEmployee;

    }

    public Notification<String> generateReport(long id, long startDate, long endDate) {

        Notification<String> notification = new Notification<>();
        StringBuilder result = new StringBuilder();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + ACTIVITY + "` WHERE date BETWEEN '" + startDate + "' AND '" + endDate + "' AND userId = '" + id + "'";
            ResultSet rs = statement.executeQuery(sql);
            result.append("User with id: ")
                    .append(id)
                    .append("Activity: ");
            while (rs.next()) {
                result.append(rs.getString("action")).append("\n");
            }
            notification.setResult(result.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something went wrong.");

        }

        return notification;

    }
}
