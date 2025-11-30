package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import view.model.UserDTO;

import java.util.List;

public class AdminView {

    private TableView userTableView;

    private TextField usernameTextField;

    private TextField passwordTextField;

    private Label usernameLabel;

    private final ObservableList<UserDTO> usersObservableList;

    private Label passwordLabel;

    private Button addUser;

    private Button deleteUser;

    private Button generateMonthlyReport;

    private Button logOutButton;

    public AdminView(Stage primaryStage, List<UserDTO> users) {

        primaryStage.setTitle("Admin Panel");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 1024, 600);
        primaryStage.setScene(scene);

        usersObservableList = FXCollections.observableList(users);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();


    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

    }

    private void initTableView(GridPane gridPane) {
        userTableView = new TableView<UserDTO>();

        userTableView.setPlaceholder(new Label("No users to display"));
        TableColumn<UserDTO, Long> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTableView.getColumns().addAll(idColumn, usernameColumn);

        userTableView.setItems(usersObservableList);

        gridPane.add(userTableView, 0, 0, 10, 1);

    }

    private void initSaveOptions(GridPane gridPane) {
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 3, 1);

        passwordTextField = new PasswordField();
        gridPane.add(passwordTextField, 4, 1);

        addUser = new Button("Add User");
        deleteUser = new Button("Delete User");
        generateMonthlyReport = new Button("Generate Report");
        logOutButton = new Button("Logout");

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(addUser, deleteUser, generateMonthlyReport, logOutButton);
        gridPane.add(buttonBox, 4, 2);

    }

    public void addUserButtonListener(EventHandler<ActionEvent> addButtonListener) {
        addUser.setOnAction(addButtonListener);
    }

    public void addDeleteUserButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteUser.setOnAction(deleteButtonListener);
    }

    public void addGenerateMonthlyReportButtonListener(EventHandler<ActionEvent> generateReportButtonListener){
        generateMonthlyReport.setOnAction(generateReportButtonListener);
    }

    public void addLogOutButtonListener(EventHandler<ActionEvent> logOutButtonListener){
        logOutButton.setOnAction(logOutButtonListener);
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public void setUsername(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public void setPassword(TextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public TableView getUserTableView() {
        return userTableView;
    }

    public void addUserToObservableList(UserDTO userDTO) {
        this.usersObservableList.add(userDTO);
    }

    public void removeUserFromObservableList(UserDTO userDTO) {
        this.usersObservableList.remove(userDTO);
    }

    public void setUsernameTextField(String usernameTextField) {
        this.usernameTextField.setText(usernameTextField);
    }

    public void setPasswordTextField(String passwordString) {
        this.passwordTextField.setText(passwordString);
    }
}
