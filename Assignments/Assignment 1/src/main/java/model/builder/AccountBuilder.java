package model.builder;

import model.Account;

public class AccountBuilder {

    private final Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder setOwnerId(long ownerId) {
        account.setOwnerId(ownerId);
        return this;
    }

    public AccountBuilder setIdentificationNumber(long identificationNumber) {
        account.setIdentificationNumber(identificationNumber);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setSum(float sum) {
        account.setSumOfMoney(sum);
        return this;
    }

    public AccountBuilder setDate(long date) {
        account.setDateCreation(date);
        return this;
    }

    public Account build() {
        return account;
    }


}
