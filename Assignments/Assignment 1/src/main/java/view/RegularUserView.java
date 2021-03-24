package view;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class RegularUserView extends JFrame {

    private JButton btnAddClientInformation;
    private JButton btnUpdateClientInformation;
    private JButton btnViewClientInformation;

    private JButton btnCreateClientAccount;
    private JButton btnViewClientAccount;
    private JButton btnUpdateClientAccount;
    private JButton btnDeleteClientAccount;

    private JButton btnTransfer;
    private JButton btnProcessUtilities;

    private JTextField tfPasswordClient;
    private JTextField tfNameClient;
    private JTextField tfAmountOfMoney;


    private JTextField tfTypeAccount;
    private JTextField tfIdentityNumberAccount;
    private JTextField tfIdentityNumberAccountReceiver;
    private JTextField tfIdUtilityBill;
    private JTextField tfClientAddress;


    private JLabel lbPasswordClient;
    private JLabel lbNameClient;

    private JLabel lbAmountOfMoney;

    private JLabel lbTypeAccount;
    private JLabel lbIdentityNumberAccount;
    private JLabel lbIdentityNumberAccountReceiver;
    private JLabel lbIdUtilityBill;
    private JLabel lbClientAddress;


    public RegularUserView() {
        setSize(800, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(lbNameClient);
        add(tfNameClient);

        add(lbPasswordClient);
        add(tfPasswordClient);

        add(lbClientAddress);
        add(tfClientAddress);

        add(lbTypeAccount);
        add(tfTypeAccount);

        add(lbIdentityNumberAccount);
        add(tfIdentityNumberAccount);

        add(lbAmountOfMoney);
        add(tfAmountOfMoney);

        add(lbIdentityNumberAccountReceiver);
        add(tfIdentityNumberAccountReceiver);

        add(lbIdUtilityBill);
        add(tfIdUtilityBill);

        add(btnAddClientInformation);
        add(btnUpdateClientInformation);
        add(btnViewClientInformation);

        add(btnCreateClientAccount);
        add(btnViewClientAccount);
        add(btnUpdateClientAccount);
        add(btnDeleteClientAccount);

        add(btnTransfer);
        add(btnProcessUtilities);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisibility(false);

    }

    private void initializeFields() {
        btnAddClientInformation = new JButton("Add Client");
        btnUpdateClientInformation = new JButton("Update Client");
        btnViewClientInformation = new JButton("View Client");
        btnCreateClientAccount = new JButton("Create Account");
        btnViewClientAccount = new JButton("View Account");
        btnUpdateClientAccount = new JButton("Update Account");
        btnDeleteClientAccount = new JButton("Delete Account");
        btnTransfer = new JButton("Transfer money");
        btnProcessUtilities = new JButton("Pay utlities");

        tfTypeAccount = new JTextField();
        tfIdentityNumberAccount = new JTextField();
        tfIdentityNumberAccountReceiver = new JTextField();

        tfIdUtilityBill = new JTextField();
        tfPasswordClient = new JTextField();
        tfClientAddress = new JTextField();

        tfNameClient = new JTextField();
        tfAmountOfMoney = new JTextField();

        lbPasswordClient = new JLabel("Password Client:");
        lbClientAddress = new JLabel("Address Client:");
        lbNameClient = new JLabel("Name of Client");

        lbAmountOfMoney = new JLabel("Sum of money");
        lbIdentityNumberAccount = new JLabel("Account Number");
        lbTypeAccount = new JLabel("Type of account");
        lbIdentityNumberAccount = new JLabel("Identity Number Account");
        lbIdentityNumberAccountReceiver = new JLabel("For transfer - Receiver acccount");
        lbIdUtilityBill = new JLabel("Utility id");

    }


    public void setBtnAddClientInformationListener(ActionListener loginButtonListener) {
        btnAddClientInformation.addActionListener(loginButtonListener);
    }

    public void setBtnUpdateClientInformationListener(ActionListener loginButtonListener) {
        btnUpdateClientInformation.addActionListener(loginButtonListener);
    }

    public void setBtnViewClientInformationListener(ActionListener loginButtonListener) {
        btnViewClientInformation.addActionListener(loginButtonListener);
    }

    public void setBtnCreateClientAccountListener(ActionListener loginButtonListener) {
        btnCreateClientAccount.addActionListener(loginButtonListener);
    }

    public void setBtnViewClientAccountListener(ActionListener loginButtonListener) {
        btnViewClientAccount.addActionListener(loginButtonListener);
    }

    public void setBtnUpdateClientAccountListener(ActionListener loginButtonListener) {
        btnUpdateClientAccount.addActionListener(loginButtonListener);
    }

    public void setBtnDeleteClientAccountListener(ActionListener loginButtonListener) {
        btnDeleteClientAccount.addActionListener(loginButtonListener);
    }

    public void setBtnTransferListener(ActionListener loginButtonListener) {
        btnTransfer.addActionListener(loginButtonListener);
    }

    public void setBtnProcessUtilitiesListener(ActionListener loginButtonListener) {
        btnProcessUtilities.addActionListener(loginButtonListener);
    }

    public String getTfPasswordClient() {
        return tfPasswordClient.getText();
    }

    public String getTfNameClient() {
        return tfNameClient.getText();
    }

    public String getTfTypeAccount() {
        return tfTypeAccount.getText();
    }

    public String getTfIdentityNumberAccount() {
        return tfIdentityNumberAccount.getText();
    }

    public String getTfIdentityNumberAccountReceiver() {
        return tfIdentityNumberAccountReceiver.getText();
    }

    public String getTfIdUtilityBill() {
        return tfIdUtilityBill.getText();
    }

    public String getTfClientAddress() {
        return tfClientAddress.getText();
    }

    public void setVisibility(boolean visible) {
        this.setVisible(visible);
    }

    public float getTfAmountOfMoney() {
        return Float.parseFloat(tfAmountOfMoney.getText());
    }
}
