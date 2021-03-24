package service;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.AccountRepository;
import repository.ClientRepository;

public class AccountManagementService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountManagementService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public Notification<Boolean> addAccount(String ownerName, String type, float sumOfMoney, long dateCreation) {
        Notification<Boolean> notification = new Notification<>();
        Notification<Client> clientNotification = clientRepository.findClient(ownerName);

        if (clientNotification.hasErrors()) {
            notification.addError(clientNotification.getFormattedErrors());
            return notification;
        } else {
            Client client = clientNotification.getResult();
            Account account = new AccountBuilder()
                    .setType(type)
                    .setDate(dateCreation)
                    .setOwnerId(client.getId())
                    .setSum(sumOfMoney)
                    .build();

            return accountRepository.save(account);
        }

    }

    public Notification<String> retrieveAccountById(long idAccount) {
        Notification<String> notification = new Notification<>();
        Notification<Account> accountNotification = accountRepository.retrieveAccount(idAccount);
        String result = "Owner: " +
                accountNotification.getResult().getOwnerId() +
                "\nSum: " +
                accountNotification.getResult().getSumOfMoney() +
                "\nType: " +
                accountNotification.getResult().getType();
        notification.setResult(result);

        if (accountNotification.hasErrors())
            notification.addError(accountNotification.getFormattedErrors());

        return notification;
    }

    public Notification<Boolean> deleteAccount(long idAccount) {
        return accountRepository.delete(idAccount);
    }

    public Notification<Boolean> updateAccount(long idAccount, String newType, float newSumOfMoney) {

        if (newType.equals(""))
            newType = accountRepository.retrieveAccount(idAccount).getResult().getType();
        return accountRepository.updateAccount(idAccount, newType, newSumOfMoney);


    }

    public Notification<Boolean> transferMoney(long idSent, long idReceive, float sum) {
        Notification<Boolean> notification = new Notification<>();
        Notification<Account> sentMoneyNotification = accountRepository.retrieveAccount(idSent);
        Notification<Account> receiveMoneyNotification = accountRepository.retrieveAccount(idReceive);

        if (sentMoneyNotification.hasErrors() || receiveMoneyNotification.hasErrors()) {
            notification.addError(sentMoneyNotification.getFormattedErrors());
            notification.addError(receiveMoneyNotification.getFormattedErrors());
        } else if (sentMoneyNotification.getResult().getSumOfMoney() < sum) {
            notification.addError("Not enough money");
        } else if (sum <= 0) {
            notification.addError("Enter a valid sum");
        } else {
            notification = updateAccount(idSent, "", sentMoneyNotification.getResult().getSumOfMoney() - sum);
            Notification<Boolean> notificationReceive = updateAccount(idReceive, "", receiveMoneyNotification.getResult().getSumOfMoney() + sum);

            if (notificationReceive.hasErrors()) {
                notification.addError(notificationReceive.getFormattedErrors());

            } else if (!notification.hasErrors()) {
                notification.setResult(notification.getResult() && notificationReceive.getResult());
            }
        }

        return notification;
    }

    public Notification<Boolean> processBill(long idAccount, long idBill, float sum) {
        Notification<Boolean> notification = new Notification<>();
        Notification<Account> accountNotification = accountRepository.retrieveAccount(idAccount);

        if (accountNotification.hasErrors()) {
            notification.addError(accountNotification.getFormattedErrors());

        } else if (accountNotification.getResult().getSumOfMoney() < sum) {
            notification.addError("Not enough money");
        } else if (sum <= 0) {
            notification.addError("Enter a valid sum");
        } else
            return updateAccount(idAccount, "", accountNotification.getResult().getSumOfMoney() - sum);

        return notification;

    }
}
