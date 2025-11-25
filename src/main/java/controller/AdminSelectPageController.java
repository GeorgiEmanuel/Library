package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import view.AdminSelectPageView;

public class AdminSelectPageController {

    private AdminSelectPageView adminSelectPageView;

    public AdminSelectPageController(AdminSelectPageView adminSelectPageView){
        this.adminSelectPageView = adminSelectPageView;

        this.adminSelectPageView.addBookSceneButtonListener(new BookSceneButtonListener());
        this.adminSelectPageView.addUserSceneButtonListener(new UsersSceneButtonListener());

    }

    private class BookSceneButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeComponentFactory.getInstance(Boolean.FALSE, adminSelectPageView.getPrimaryStage(), null);
        }
    }

    private class UsersSceneButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            AdminComponentFactory.getInstance(Boolean.FALSE, adminSelectPageView.getPrimaryStage());
        }
    }

}
