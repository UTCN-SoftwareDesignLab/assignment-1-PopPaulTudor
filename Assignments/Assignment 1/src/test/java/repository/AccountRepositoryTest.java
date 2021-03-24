package repository;

import database.DBConnectionFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepository(connection);
    }

    @Test
    void save() {
        Account account = new AccountBuilder()
                .setType("saving")
                .setIdentificationNumber(1L)
                .setOwnerId(1L)
                .setSum(50)
                .build();

        assertTrue(accountRepository.save(account).getResult());

    }

    @Test
    void updateAccount() {
        assertTrue(accountRepository.updateAccount(1L, "new type", 100).getResult());
    }

    @Test
    void delete() {
        assertTrue(accountRepository.delete(1L).getResult());
    }
}