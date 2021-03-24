package service;

import Utills.FieldUtills;
import model.Employee;
import model.Role;
import model.builder.EmployeeBuilder;
import model.validation.EmployeeValidator;
import model.validation.Notification;
import repository.EmployeeRepository;
import repository.RightsRolesRepository;

import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;


public class EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public EmployeeManagementService(EmployeeRepository employeeRepository, RightsRolesRepository rightsRolesRepository) {
        this.employeeRepository = employeeRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }


    public Notification<Boolean> addEmployee(String username, String password) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Employee employee = new EmployeeBuilder()
                .setRoles(Collections.singletonList(customerRole))
                .setUsername(username)
                .setPassword(password)
                .build();


        EmployeeValidator employeeValidator = new EmployeeValidator(employee);
        boolean userValid = employeeValidator.validate();
        Notification<Boolean> employeeRegisterNotification = new Notification<>();

        if (!userValid) {
            employeeValidator.getErrors().forEach(employeeRegisterNotification::addError);
            employeeRegisterNotification.setResult(Boolean.FALSE);
        } else {
            employee.setPassword(FieldUtills.encodePassword(password));
            employeeRegisterNotification.setResult(employeeRepository.save(employee));
        }
        return employeeRegisterNotification;

    }


    public Notification<Boolean> deleteEmployee(String username) {
        Notification<Boolean> employeeDeleteNotification = new Notification<>();
        Notification<Employee> findUsername = employeeRepository.findEmployeeFromUsername(username);

        if (!findUsername.hasErrors()) {
            if (findUsername.getResult() != null) {
                return employeeRepository.delete(username);
            } else {
                employeeDeleteNotification.addError("User could not be found.");
            }
        } else {
            employeeDeleteNotification.addError(findUsername.getFormattedErrors());
        }
        return employeeDeleteNotification;
    }

    public Notification<Employee> login(String username, String password) {
        return employeeRepository.findByUsernameAndPassword(username, FieldUtills.encodePassword(password));
    }


    public Notification<Boolean> updateEmployee(String username, String password, String newPassword) {

        Notification<Boolean> employeeUpdateNotification = new Notification<>();
        Notification<Employee> findUsername = employeeRepository.findByUsernameAndPassword(username, FieldUtills.encodePassword(password));

        if (!findUsername.hasErrors()) {
            return employeeRepository.update(username, FieldUtills.encodePassword(password), FieldUtills.encodePassword(newPassword));

        } else {
            employeeUpdateNotification.addError(findUsername.getFormattedErrors());
        }
        return employeeUpdateNotification;
    }

    public Notification<String> retrieveAllEmployee() {
        return employeeRepository.retrieve();

    }

    public Notification<String> generateReport(String username, long startDate, long endDate) {


        Notification<Employee> getEmployeeNotification = employeeRepository.findEmployeeFromUsername(username);
        if (getEmployeeNotification.hasErrors()) {

            Notification<String> notification = new Notification<>();
            notification.addError(getEmployeeNotification.getFormattedErrors());
            return notification;
        } else {
            return employeeRepository.generateReport(getEmployeeNotification.getResult().getId(), startDate, endDate);

        }
    }
}
