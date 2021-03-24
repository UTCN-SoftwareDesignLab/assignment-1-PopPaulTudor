package repository;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;

import java.sql.*;

import static database.Constants.Tables.ACCOUNT;

public class AccountRepository {


    private final Connection connection;

    public AccountRepository(Connection connection) {
        this.connection = connection;
    }

    public Notification<Boolean> save(Account account) {

        Notification<Boolean> notification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + ACCOUNT + "` values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, account.getOwnerId());
            insertUserStatement.setString(2, account.getType());
            insertUserStatement.setFloat(3, account.getSumOfMoney());
            insertUserStatement.executeUpdate();

            notification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something went wrong when creating the account");

        }

        return notification;
    }

    public Notification<Account> retrieveAccount(long idAccount) {
        Notification<Account> notification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchEmployeesSql = "Select * from `" + ACCOUNT + "` WHERE identificationNumber = '" + idAccount + "'";
            ResultSet rs = statement.executeQuery(fetchEmployeesSql);
            rs.next();

            Account account = new AccountBuilder()
                    .setSum(rs.getFloat("sumOfMoney"))
                    .setIdentificationNumber(idAccount)
                    .setType(rs.getString("type"))
                    .setOwnerId(rs.getLong("ownerId"))
                    .build();


            notification.setResult(account);

        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something went when trying to get the result.");

        }

        return notification;
    }

    public Notification<Boolean> delete(long idAccount) {
        Notification<Boolean> notification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String deleteEmployeeSQL = "DELETE from `" + ACCOUNT + "` where `identificationNumber`='" + idAccount + "'";
            statement.executeUpdate(deleteEmployeeSQL);

            notification.setResult(true);

        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something went when deleting the account.");

        }

        return notification;
    }

    public Notification<Boolean> updateAccount(long idAccount, String newType, float newSumOfMoney) {
        Notification<Boolean> updateEmployee = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String updateEmployeeSQL = "UPDATE `" + ACCOUNT +
                    "` SET `type` = '" + newType + "', `sumOfMoney` = '" + newSumOfMoney +
                    "' WHERE  `identificationNumber`= '" + idAccount + "'";

            statement.executeUpdate(updateEmployeeSQL);
            updateEmployee.setResult(true);

        } catch (SQLException throwables) {
            updateEmployee.addError("Something went wrong with the update");

            throwables.printStackTrace();
        }

        return updateEmployee;
    }
}
