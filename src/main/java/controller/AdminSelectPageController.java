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

    public AdminSelectPageController(AdminSelectPageView adminSelectPageView, User user){
        this.adminSelectPageView = adminSelectPageView;

        this.adminSelectPageView.addBookSceneButtonListener(new BookSceneButtonListener());
        this.adminSelectPageView.addUserSceneButtonListener(new UsersSceneButtonListener());
        this.user = user;

    }

    private class BookSceneButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeComponentFactory.getInstance(Boolean.FALSE, adminSelectPageView.getPrimaryStage(), user);
        }
    }

    private class UsersSceneButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            AdminComponentFactory.getInstance(Boolean.FALSE, adminSelectPageView.getPrimaryStage());
        }
    }

}
