package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.AdminSelectPageComponentFactory;
import launcher.EmployeeComponentFactory;
import launcher.LoginComponentFactory;
import model.User;
import model.validator.Notification;
import service.user.AuthentificationService;
import view.AdminSelectPageView;
import view.AdminView;
import view.LoginView;

import static database.Constants.Roles.*;


public class LoginController {
    private final LoginView loginView;
    private final AuthentificationService authentificationService;
    private User currentSessionUser;

    public LoginController(LoginView loginView, AuthentificationService authentificationService) {
        this.loginView = loginView;
        this.authentificationService = authentificationService;

        this.loginView.addLoginButtonListener(new LogInButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LogInButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authentificationService.login(username, password);


            if (loginNotification.hasErrors()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("LogIn Successful!");
                currentSessionUser = loginNotification.getResult();

                String currentSessionUserRole = currentSessionUser.getRoles().getFirst().getRole();

                if (currentSessionUserRole.equals(CUSTOMER)){
                    //EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage(), authentificationService.findByUsername(username));
                }
                else if (currentSessionUserRole.equals(EMPLOYEE)){
                    EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage(), authentificationService.findByUsername(username));

                }
                else if (currentSessionUserRole.equals(ADMINISTRATOR)){
                    AdminSelectPageComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                }

            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authentificationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful");
            }

        }
    }


}
