package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.EmployeeComponentFactory;
import launcher.LoginComponentFactory;
import model.User;
import model.validator.UserValidator;
import service.user.AuthentificationService;
import view.LoginView;

import java.util.List;

public class LoginController {
    private final LoginView loginView;
    private final AuthentificationService authentificationService;
    private final UserValidator userValidator;


    public LoginController(LoginView loginView, AuthentificationService authentificationService, UserValidator userValidator) {
        this.loginView = loginView;
        this.authentificationService = authentificationService;
        this.userValidator = userValidator;

        this.loginView.addLoginButtonListener(new LogInButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LogInButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User user = authentificationService.login(username, password);

            if (user == null) {
                loginView.setActionTargetText("Invalid Username or password!");
            } else {
                loginView.setActionTargetText("LogIn Successful!");
                EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                if (authentificationService.register(username, password)) {
                    loginView.setActionTargetText("Register successful");
                } else {
                    loginView.setActionTargetText("Invalid Username or password. User may be already taken!");
                }
            } else {
                loginView.setActionTargetText(userValidator.getFormattedErrors());
            }

        }
    }


}
