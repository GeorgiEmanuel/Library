package controller;

import com.lowagie.text.Document;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.AdminComponentFactory;
import launcher.AdminSelectPageComponentFactory;
import launcher.LoginComponentFactory;
import mapper.UserMapper;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import service.admin.AdminService;
import view.AdminView;
import view.LoginView;
import view.model.UserDTO;

public class AdminController {

    private final AdminView adminView;
    private final AdminService adminService;

    public AdminController(AdminView adminView, AdminService adminService) {
        this.adminView = adminView;
        this.adminService = adminService;

        this.adminView.addUserButtonListener(new SaveButtonListener());
        this.adminView.addDeleteUserButtonListener(new DeleteButtonListener());
        this.adminView.addGenerateMonthlyReportButtonListener(new GenerateMonthlyReportButtonListener());
        this.adminView.addLogOutButtonListener(new LogOutButtonListener());

    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            if (username.isEmpty() || password.isEmpty()) {
                adminView.addDisplayAlertMessage("Save Error", "Problem at Username or Password fields", "Can not have empty username of password");

            } else {
                User userToSave = new UserBuilder().setUsername(username).setPassword(password).build();
                Notification<User> savedUserNotification = adminService.save(userToSave);

                if (savedUserNotification.hasErrors()) {
                    adminView.addDisplayAlertMessage("Save Error", "Problem at adding an Employee", savedUserNotification.getFormattedErrors());
                } else {
                    userToSave.setId(savedUserNotification.getResult().getId());
                    adminView.addDisplayAlertMessage("Save Successful", "Employee Added", "Employee was successfully added to the database");
                    adminView.setUsernameTextField("");
                    adminView.setPasswordTextField("");
                    adminView.addUserToObservableList(UserMapper.converUserToUserDTO(userToSave));
                }

            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

            UserDTO userDTO = (UserDTO) adminView.getUserTableView().getSelectionModel().getSelectedItem();
            if (userDTO != null) {
                Notification<Boolean> deleteUserNotification = adminService.delete(UserMapper.convertUserDTOToUser(userDTO));
                if (deleteUserNotification.hasErrors()) {
                    adminView.addDisplayAlertMessage("Delete Error", "Problem at deleting the User", deleteUserNotification.getFormattedErrors());
                } else {
                    adminView.addDisplayAlertMessage("Delete Successful", "User deleted", "User was successfully deleted");
                    adminView.removeUserFromObservableList(userDTO);
                }
            } else {
                adminView.addDisplayAlertMessage("Delete Error", "Problem at deleting the User", "You must select a user before pressing the delete button");
            }


        }
    }

    private class GenerateMonthlyReportButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
          Notification<Document> reportGenerationNotification =  adminService.generateReport();
          if (reportGenerationNotification.hasErrors()){
              adminView.addDisplayAlertMessage("Report generation error !", "Report generation failed !", reportGenerationNotification.getFormattedErrors());
          } else {
              adminView.addDisplayAlertMessage("Report generated successfully !", "Monthly report available in local directory !", "Check monthly report for data accuracy");
          }

        }
    }

    public class LogOutButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Stage primaryStage = LoginComponentFactory.getStage();
            LoginView loginView = LoginComponentFactory.getInstance(AdminComponentFactory.getComponentsForTest(), AdminComponentFactory.getStage()).getLoginView();
            loginView.resetLoginViewFields();
            AdminSelectPageComponentFactory.resetInstance();
            AdminComponentFactory.resetInstance();
            primaryStage.setScene(loginView.getScene());
        }
    }
}
