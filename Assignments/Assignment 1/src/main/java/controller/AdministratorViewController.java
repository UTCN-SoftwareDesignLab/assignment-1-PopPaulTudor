package controller;

import Utills.FieldUtills;
import launcher.ComponentFactory;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.ActivityRepository;
import service.EmployeeManagementService;
import view.AdministratorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AdministratorViewController {

    private final AdministratorView administratorView;
    private final EmployeeManagementService employeeManagementService;
    private final ActivityRepository activityRepository;


    public AdministratorViewController(AdministratorView administratorView, EmployeeManagementService employeeManagementService, ActivityRepository activityRepository) {
        this.administratorView = administratorView;
        this.employeeManagementService = employeeManagementService;
        this.activityRepository = activityRepository;

        administratorView.setBtnCreateEmployeeListener(new CreateEmployeeListener());
        administratorView.setBtnGenerateReportListener(new GenerateReportListener());
        administratorView.setBtnDeleteEmployeeListener(new DeleteEmployeeListener());
        administratorView.setBtnRetrieveEmployeeListener(new RetrieveEmployeeListener());
        administratorView.setBtnUpdateEmployeeListener(new UpdateEmployeeListener());
    }

    private class CreateEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = administratorView.getUsername();
            String password = administratorView.getPassword();

            Notification<Boolean> registerNotification = employeeManagementService.addEmployee(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Create Employee: " + username).setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Registration successful!");
                }
            }
        }

    }

    private class DeleteEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = administratorView.getUsername();

            Notification<Boolean> deleteNotification = employeeManagementService.deleteEmployee(username);

            if (deleteNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), deleteNotification.getFormattedErrors());
            } else {
                if (!deleteNotification.getResult()) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Delete not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Delete Employee: " + username).setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "User deleted!");

                }
            }
        }

    }

    private class UpdateEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = administratorView.getUsername();
            String password = administratorView.getPassword();
            String newPassword = administratorView.getNewPassword();

            Notification<Boolean> updateNotification = new Notification<>();
            if (newPassword.length() > 8) {
                updateNotification = employeeManagementService.updateEmployee(username, password, newPassword);

            } else {
                updateNotification.addError("Please enter a new passowrd");
            }

            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), updateNotification.getFormattedErrors());
            } else {
                if (!updateNotification.getResult()) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Update not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Update Employee: " + username).setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "User updated!");

                }
            }

        }
    }

    private class RetrieveEmployeeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<String> retrieveNotification = employeeManagementService.retrieveAllEmployee();

            if (retrieveNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), retrieveNotification.getFormattedErrors());
            } else {
                if (retrieveNotification.getResult() == null) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Update not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Retrieve employees").setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), retrieveNotification.getResult());

                }
            }

        }

    }

    private class GenerateReportListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<String> generateReport = new Notification<>();
            String username = administratorView.getUsername();
            long startDate = FieldUtills.getLongFromString(administratorView.getStartDateReport());
            long endDate = FieldUtills.getLongFromString(administratorView.getEndDateReport());

            if (endDate < startDate)
                generateReport.addError("End date should be bigger than start date");

            generateReport = employeeManagementService.generateReport(username, startDate, endDate);

            if (generateReport.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), generateReport.getFormattedErrors());
            } else {

                ComponentFactory componentFactory = ComponentFactory.instance(false);
                activityRepository.create(new ActivityBuilder()
                        .setAction("Generate report")
                        .setDate(new Date().getTime())
                        .setUserId(componentFactory.getActiveUserId())
                        .build());

                JOptionPane.showMessageDialog(administratorView.getContentPane(), generateReport.getResult());


            }
        }

    }
}


