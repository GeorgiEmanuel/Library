package view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.*;

public class AdminSelectPageView {

    private Stage primaryStage;
    private Button bookSceneButton;
    private Button usersSceneButton;

    public AdminSelectPageView(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Page Select");
        GridPane gridPane = new GridPane();

        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 1024, 600);
        primaryStage.setScene(scene);

        initButtons(gridPane);
        primaryStage.show();

    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));
    }

    private void initButtons(GridPane gridPane){
        bookSceneButton = new Button("Book Page");
        usersSceneButton = new Button("Users Page");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(bookSceneButton, usersSceneButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 0);
    }


    public void addBookSceneButtonListener(EventHandler<ActionEvent> bookSceneButtonListener){
        bookSceneButton.setOnAction(bookSceneButtonListener);
    }

    public void addUserSceneButtonListener(EventHandler<ActionEvent> userSceneButtonListener){
        usersSceneButton.setOnAction(userSceneButtonListener);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
