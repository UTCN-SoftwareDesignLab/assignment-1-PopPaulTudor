package repository;

import Utills.FieldUtills;
import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {

    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepository(connection);
    }
    @Test
    void save() {

        Client client = new ClientBuilder()
                .setId(1L)
                .setAddress("Street Donath no. 2")
                .setName("MihaiPop")
                .setPassword("password!1")
                .build();

        assertTrue(clientRepository.save(client));
    }

    @Test
    void findClient() {
        assertEquals("Street newAddress no. 2", clientRepository.findClient("MihaiPop").getResult().getAddress());

    }

    @Test
    void update() {
        assertTrue(clientRepository.update("MihaiPop",
                FieldUtills.encodePassword("password!1"), "Street newAddress no. 2").getResult());

    }

    @Test
    void retrieve() {
        assertEquals("MihaiPop Street newAddress no. 2\n", clientRepository.retrieve().getResult());
    }
}