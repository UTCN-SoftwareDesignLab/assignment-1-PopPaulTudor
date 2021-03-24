package repository;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;

import java.sql.*;

import static database.Constants.Tables.CLIENT;

public class ClientRepository {

    private final Connection connection;

    public ClientRepository(Connection connection) {
        this.connection = connection;
    }

    public Boolean save(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + CLIENT + "` values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getAddress());
            insertUserStatement.setString(3, client.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Notification<Client> findClient(String name) {
        Notification<Client> notification = new Notification<>();

        try {
            Statement statement = connection.createStatement();
            String fetchEmployeeSql = "Select * from `" + CLIENT + "` where `name`='" + name + "'";
            ResultSet rs = statement.executeQuery(fetchEmployeeSql);
            if (rs.next()) {
                Client client = new ClientBuilder()
                        .setId(rs.getLong("id"))
                        .setName(rs.getString("name"))
                        .setAddress(rs.getString("address"))
                        .setPassword(rs.getString("password"))
                        .build();

                notification.setResult(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something went wrong when searching for the name");

        }
        return notification;
    }

    public Notification<Boolean> update(String name, String encodePassword, String newAddress) {

        Notification<Boolean> notification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String updateEmployeeSQL = "UPDATE `" + CLIENT +
                    "` SET `password` = '" + encodePassword + "', `address`  = '" + newAddress + "' WHERE  `name` = '" + name + "'";

            statement.executeUpdate(updateEmployeeSQL);
            notification.setResult(true);

        } catch (SQLException throwables) {
            notification.addError("Something went wrong with the update");

            throwables.printStackTrace();
        }


        return notification;
    }

    public Notification<String> retrieve() {
        StringBuilder result = new StringBuilder();
        Notification<String> notification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchEmployeesSql = "Select * from `" + CLIENT + "`";
            ResultSet rs = statement.executeQuery(fetchEmployeesSql);

            while (rs.next()) {
                result.append(rs.getString("name")).append(" ").append(rs.getString("address")).append("\n");
            }
            notification.setResult(result.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something went wrong.");

        }

        return notification;

    }
}
