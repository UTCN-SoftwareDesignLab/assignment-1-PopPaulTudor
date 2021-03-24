package controller;

import launcher.ComponentFactory;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.ActivityRepository;
import service.AccountManagementService;
import service.ClientManagementService;
import view.RegularUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class RegularUserViewController {


    private final ClientManagementService clientManagementService;
    private final AccountManagementService accountManagementService; // crud + transfer money + process utility bills
    private final RegularUserView regularUserView;
    private final ActivityRepository activityRepository;


    public RegularUserViewController(RegularUserView regularUserView, AccountManagementService accountManagementService,
                                     ClientManagementService clientManagementService, ActivityRepository activityRepository) {
        this.clientManagementService = clientManagementService;
        this.accountManagementService = accountManagementService;
        this.regularUserView = regularUserView;
        this.activityRepository = activityRepository;

        regularUserView.setBtnAddClientInformationListener(new AddClientListener());
        regularUserView.setBtnViewClientInformationListener(new ViewClientInformation());
        regularUserView.setBtnUpdateClientInformationListener(new UpdateClientInformation());

        regularUserView.setBtnCreateClientAccountListener(new AddAccountListener());
        regularUserView.setBtnViewClientAccountListener(new ViewAccountListener());
        regularUserView.setBtnDeleteClientAccountListener(new DeleteAccountListener());
        regularUserView.setBtnUpdateClientAccountListener(new UpdateAccountListener());

        regularUserView.setBtnProcessUtilitiesListener(new ProcessBillsListener());
        regularUserView.setBtnTransferListener(new TransferListener());
    }

    private class AddClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = regularUserView.getTfNameClient();
            String address = regularUserView.getTfClientAddress();
            String password = regularUserView.getTfPasswordClient();

            Notification<Boolean> addClientNotification = clientManagementService.addClient(name, address, password);

            if (addClientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), addClientNotification.getFormattedErrors());
            } else {
                if (!addClientNotification.getResult()) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Creation not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Create Client: " + name).setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Creation successful!");
                }
            }
        }
    }


    private class UpdateClientInformation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String name = regularUserView.getTfNameClient();
            String newPassword = regularUserView.getTfPasswordClient();
            String newAddress = regularUserView.getTfClientAddress();

            Notification<Boolean> updateNotification = new Notification<>();
            if (newPassword.length() > 8) {
                updateNotification = clientManagementService.updateClient(name, newPassword, newAddress);

            } else {
                updateNotification.addError("Please enter a new password");
            }

            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), updateNotification.getFormattedErrors());
            } else {
                if (!updateNotification.getResult()) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Update not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Update Employee: " + name).setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Client updated!");

                }
            }

        }

    }


    private class ViewClientInformation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<String> retrieveNotification = clientManagementService.retrieveAllClients();

            if (retrieveNotification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), retrieveNotification.getFormattedErrors());
            } else {
                if (retrieveNotification.getResult() == null) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Update not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Retrieve clients").setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), retrieveNotification.getResult());

                }
            }

        }

    }


    private class AddAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ownerName = regularUserView.getTfNameClient();
            String type = regularUserView.getTfTypeAccount();
            float sumOfMoney = regularUserView.getTfAmountOfMoney();
            long dateCreation = new Date().getTime();

            Notification<Boolean> notification = accountManagementService.addAccount(ownerName, type, sumOfMoney, dateCreation);

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), notification.getFormattedErrors());
            } else {
                if (!notification.getResult()) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Delete not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Create account for: " + ownerName)
                            .setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Account created!");

                }
            }
        }
    }

    private class ViewAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            long idAccount = Integer.parseInt(regularUserView.getTfIdentityNumberAccount());
            Notification<String> notification = accountManagementService.retrieveAccountById(idAccount);


            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), notification.getFormattedErrors());
            } else {
                if (notification.getResult() == null) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Retrieve not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Retrieve information for: " + idAccount)
                            .setDate(new Date().getTime())
                            .setUserId(componentFactory.getActiveUserId())
                            .build());

                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), notification.getResult());

                }
            }

        }
    }

    private class DeleteAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            long idAccount = Integer.parseInt(regularUserView.getTfIdentityNumberAccount());
            Notification<Boolean> notification = accountManagementService.deleteAccount(idAccount);

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), notification.getFormattedErrors());
            } else {
                if (!notification.getResult()) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Deletion not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("Deleted account: " + idAccount)
                            .setDate(new Date().getTime())
                            .setUserId(componentFactory.getActiveUserId())
                            .build());

                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Account deleted");

                }
            }

        }
    }

    private class UpdateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            long idAccount = Integer.parseInt(regularUserView.getTfIdentityNumberAccount());
            String newType = regularUserView.getTfTypeAccount();
            float newSumOfMoney = regularUserView.getTfAmountOfMoney();

            Notification<Boolean> notification = accountManagementService.updateAccount(idAccount, newType, newSumOfMoney);

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), notification.getFormattedErrors());
            } else {
                if (!notification.getResult()) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Updating not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("account: " + idAccount + " modified")
                            .setDate(new Date().getTime()).setUserId(componentFactory.getActiveUserId()).build());
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Account modified");

                }
            }
        }

    }


    private class TransferListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            long idSent = Integer.parseInt(regularUserView.getTfIdentityNumberAccount());
            long idReceive = Integer.parseInt(regularUserView.getTfIdentityNumberAccountReceiver());
            float sum = regularUserView.getTfAmountOfMoney();

            Notification<Boolean> notification = accountManagementService.transferMoney(idSent, idReceive, sum);

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), notification.getFormattedErrors());
            } else {
                if (!notification.getResult()) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Transfer not successful, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder()
                            .setAction(sum + " Euro transferred from: " + idSent + " to: " + idReceive)
                            .setDate(new Date().getTime())
                            .setUserId(componentFactory.getActiveUserId())
                            .build());
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Transfer has been made");

                }
            }


        }
    }

    private class ProcessBillsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long idAccount = Integer.parseInt(regularUserView.getTfIdentityNumberAccount());
            long idBill = Integer.parseInt(regularUserView.getTfIdUtilityBill());
            float sum = regularUserView.getTfAmountOfMoney();

            Notification<Boolean> notification = accountManagementService.processBill(idAccount, idBill, sum);

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(regularUserView.getContentPane(), notification.getFormattedErrors());
            } else {
                if (!notification.getResult()) {
                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Bill was nor processed, please try again later.");
                } else {
                    ComponentFactory componentFactory = ComponentFactory.instance(false);
                    activityRepository.create(new ActivityBuilder().setAction("paid bill: " + idBill)
                            .setDate(new Date().getTime())
                            .setUserId(componentFactory.getActiveUserId())
                            .build());

                    JOptionPane.showMessageDialog(regularUserView.getContentPane(), "Bill paid");

                }
            }

        }
    }


}
