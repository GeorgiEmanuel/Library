package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import mapper.UserMapper;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import service.admin.AdminService;
import view.AdminView;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

public class AdminController {

    private final AdminView adminView;
    private final AdminService adminService;

    public AdminController(AdminView adminView, AdminService adminService) {
        this.adminView = adminView;
        this.adminService = adminService;

        this.adminView.addUserButtonListener(new SaveButtonListener());
        this.adminView.addDeleteUserButtonListener(new DeleteButtonListener());

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

}
