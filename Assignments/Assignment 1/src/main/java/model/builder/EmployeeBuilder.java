package model.builder;

import model.Employee;
import model.Role;

import java.util.List;

public class EmployeeBuilder {

    private final Employee employee;

    public EmployeeBuilder() {
        employee = new Employee();
    }

    public EmployeeBuilder setUsername(String username) {
        employee.setUsername(username);
        return this;
    }

    public EmployeeBuilder setRoles(List<Role> roles) {
        employee.setRoles(roles);
        return this;
    }

    public EmployeeBuilder setId(long id) {
        employee.setId(id);
        return this;
    }

    public EmployeeBuilder setPassword(String password) {
        employee.setPassword(password);
        return this;
    }

    public Employee build() {
        return employee;
    }
}
