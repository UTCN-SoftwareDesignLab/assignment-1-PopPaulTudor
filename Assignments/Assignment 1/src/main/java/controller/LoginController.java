package controller;

import launcher.ComponentFactory;
import model.Employee;
import model.Role;
import model.validation.Notification;
import service.EmployeeManagementService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final EmployeeManagementService employeeManagementService;

    public LoginController(LoginView loginView, EmployeeManagementService employeeManagementService) {
        this.loginView = loginView;
        this.employeeManagementService = employeeManagementService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Employee> loginNotification = employeeManagementService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");

                List<Role> roles = loginNotification.getResult().getRoles();
                ComponentFactory componentFactory = ComponentFactory.instance(false);
                componentFactory.setActiveUserId(loginNotification.getResult().getId());

                loginView.setVisibility(false);
                if (roles.get(0).getRole().equals("employee")) {
                    componentFactory.getRegularUserView().setVisibility(true);

                } else {
                    componentFactory.getAdministratorView().setVisibility(true);

                }

            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = employeeManagementService.addEmployee(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }
}
