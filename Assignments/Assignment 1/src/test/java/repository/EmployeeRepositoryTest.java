package repository;

import Utills.FieldUtills;
import database.DBConnectionFactory;
import model.Employee;
import model.Role;
import model.builder.EmployeeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;
    private RightsRolesRepository rightsRolesRepository;

    @BeforeEach
    void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepository(connection);
        employeeRepository = new EmployeeRepository(connection, rightsRolesRepository);
    }

    @Test
    void save() {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Employee employee = new EmployeeBuilder()
                .setRoles(Collections.singletonList(customerRole))
                .setPassword("password1!")
                .setUsername("user@gmail.com")
                .build();

        assertTrue(employeeRepository.save(employee));
    }

    @Test
    void retrieve() {
        assertEquals("user@gmail.com\n", employeeRepository.retrieve().getResult());
    }


    @Test
    void findEmployeeFromUsername() {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Employee employee = new EmployeeBuilder()
                .setRoles(Collections.singletonList(customerRole))
                .setPassword("password1!")
                .setUsername("user@gmail.com")
                .build();

        assertEquals(employee.getUsername(), employeeRepository.findEmployeeFromUsername("user@gmail.com").getResult().getUsername());
    }




    @Test
    void update() {
        assertTrue(employeeRepository.update("user@gmail.com",
                FieldUtills.encodePassword("password1!"),
                FieldUtills.encodePassword("aNewPassword!1")).getResult());


    }

    @Test
    void delete() {
        assertTrue(employeeRepository.delete("user@gmail.com").getResult());

    }

}