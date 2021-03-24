package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdministratorView extends JFrame {

    private JTextField tfUsernameEmployee;
    private JTextField tfPasswordEmployee;
    private JTextField tfNewPasswordEmployee;

    private JTextField tfStartDateReport;
    private JTextField tfEndDateReport;


    private JLabel lbUsernameEmployee;
    private JLabel lbPasswordEmployee;
    private JLabel lbNewPasswordEmployee;

    private JLabel lbStartDateReport;
    private JLabel lbEndDateReport;

    private JButton btnCreateEmployee;
    private JButton btnRetrieveEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnGenerateReport;


    private JList<String> listEmployees;
    private JList<String> listReports;


    public AdministratorView() throws HeadlessException {
        setSize(800, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lbUsernameEmployee);
        add(tfUsernameEmployee);

        add(lbPasswordEmployee);
        add(tfPasswordEmployee);

        add(lbNewPasswordEmployee);
        add(tfNewPasswordEmployee);

        add(lbStartDateReport);
        add(tfStartDateReport);

        add(lbEndDateReport);
        add(tfEndDateReport);

        add(listEmployees);
        add(listReports);

        add(btnCreateEmployee);
        add(btnRetrieveEmployee);
        add(btnDeleteEmployee);
        add(btnUpdateEmployee);
        add(btnGenerateReport);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisibility(false);
    }

    private void initializeFields() {
        tfUsernameEmployee = new JTextField();
        tfPasswordEmployee = new JTextField();
        tfNewPasswordEmployee = new JTextField();
        tfEndDateReport = new JTextField();
        tfStartDateReport = new JTextField();
        listEmployees = new JList<>();
        listReports = new JList<>();

        btnCreateEmployee = new JButton("Add Employee");
        btnRetrieveEmployee = new JButton("Retrieve Employees");
        btnDeleteEmployee = new JButton("Delete Employee");
        btnUpdateEmployee = new JButton("Update Employee");
        btnGenerateReport = new JButton("Generate report");

        lbUsernameEmployee = new JLabel("Username Employee");
        lbPasswordEmployee = new JLabel("Password Employee");
        lbNewPasswordEmployee = new JLabel("New password Employee");

        lbStartDateReport = new JLabel("Start date: yyyy/MM/dd");
        lbEndDateReport = new JLabel("End date: yyyy/MM/dd");
    }

    public String getUsername() {
        return tfUsernameEmployee.getText();
    }

    public String getPassword() {
        return tfPasswordEmployee.getText();
    }

    public String getNewPassword() {
        return tfNewPasswordEmployee.getText();
    }

    public void setBtnCreateEmployeeListener(ActionListener loginButtonListener) {
        btnCreateEmployee.addActionListener(loginButtonListener);
    }

    public void setBtnRetrieveEmployeeListener(ActionListener registerButtonListener) {
        btnRetrieveEmployee.addActionListener(registerButtonListener);
    }

    public void setBtnDeleteEmployeeListener(ActionListener registerButtonListener) {
        btnDeleteEmployee.addActionListener(registerButtonListener);
    }

    public void setBtnUpdateEmployeeListener(ActionListener registerButtonListener) {
        btnUpdateEmployee.addActionListener(registerButtonListener);
    }

    public void setBtnGenerateReportListener(ActionListener registerButtonListener) {
        btnGenerateReport.addActionListener(registerButtonListener);
    }

    public void setVisibility(boolean visibility) {
        this.setVisible(visibility);
    }


    public String getEndDateReport() {
        return tfEndDateReport.getText();
    }


    public String getStartDateReport() {
        return tfStartDateReport.getText();
    }
}
