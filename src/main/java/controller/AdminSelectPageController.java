package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import model.User;
import view.AdminSelectPageView;

public class AdminSelectPageController {

    private AdminSelectPageView adminSelectPageView;
    private final User user;
    private final Boolean componentsFortTests;

    public AdminSelectPageController(Boolean componentsFortTests, AdminSelectPageView adminSelectPageView, User user){
        this.adminSelectPageView = adminSelectPageView;
        this.componentsFortTests = componentsFortTests;

        this.adminSelectPageView.addBookSceneButtonListener(new BookSceneButtonListener());
        this.adminSelectPageView.addUserSceneButtonListener(new UsersSceneButtonListener());
        this.user = user;

    }

    private class BookSceneButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeComponentFactory.getInstance(componentsFortTests, adminSelectPageView.getPrimaryStage(), user);
        }
    }

    private class UsersSceneButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            AdminComponentFactory.getInstance(componentsFortTests, adminSelectPageView.getPrimaryStage());
        }
    }

}
