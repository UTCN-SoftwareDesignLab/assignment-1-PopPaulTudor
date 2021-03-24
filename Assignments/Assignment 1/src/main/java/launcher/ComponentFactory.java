package launcher;


import controller.AdministratorViewController;
import controller.LoginController;
import controller.RegularUserViewController;
import database.DBConnectionFactory;
import repository.*;
import service.AccountManagementService;
import service.ClientManagementService;
import service.EmployeeManagementService;
import view.AdministratorView;
import view.LoginView;
import view.RegularUserView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;
    private final LoginController loginController;
    private final EmployeeManagementService employeeManagementService;
    private final RightsRolesRepository rightsRolesRepository;
    private final EmployeeRepository employeeRepository;
    private final ActivityRepository activityRepository;

    private final AdministratorView administratorView;
    private final AdministratorViewController administratorViewController;
    private final RegularUserViewController regularUserViewController;
    private final RegularUserView regularUserView;

    private final ClientManagementService clientManagementService;
    private final AccountManagementService accountManagementService;

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    private long activeUserId;

    private static ComponentFactory instance;

    public ComponentFactory(Boolean componentsForTests) {

        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepository(connection);
        this.activityRepository = new ActivityRepository(connection);
        this.employeeRepository = new EmployeeRepository(connection, rightsRolesRepository);

        this.employeeManagementService = new EmployeeManagementService(this.employeeRepository, this.rightsRolesRepository);
        this.loginView = new LoginView();
        this.loginController = new LoginController(loginView, employeeManagementService);

        this.administratorView = new AdministratorView();
        this.administratorViewController = new AdministratorViewController(administratorView, employeeManagementService, activityRepository);

        this.clientRepository = new ClientRepository(connection);
        this.clientManagementService = new ClientManagementService(clientRepository);

        this.accountRepository = new AccountRepository(connection);
        this.accountManagementService = new AccountManagementService(accountRepository, clientRepository);

        this.regularUserView = new RegularUserView();


        this.regularUserViewController = new RegularUserViewController(regularUserView, accountManagementService, clientManagementService, activityRepository);


    }

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public AdministratorView getAdministratorView() {
        return administratorView;
    }

    public RegularUserView getRegularUserView() {
        return regularUserView;
    }

    public void setActiveUserId(long activeUserId) {
        this.activeUserId = activeUserId;
    }

    public long getActiveUserId() {
        return activeUserId;
    }
}
