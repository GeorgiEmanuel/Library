package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.model.BookDTO;
import view.model.UserDTO;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.List;

public class CustomerView {
    private TableView bookTableView;

    private ComboBox<String> employeesComboBoxView;
    
    private Button orderButton;

    private Button logOutButton;
    
    private final ObservableList<BookDTO> booksObservableList;
    
    private final ObservableList<UserDTO> employeesObservableList;
    
    
    public CustomerView(Stage primaryStage, List<BookDTO> books, List<UserDTO> employees){
        primaryStage.setTitle("Customer Page");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);
        
        Scene scene = new Scene(gridPane, 1024, 600);
        primaryStage.setScene(scene);
        
        booksObservableList = FXCollections.observableList(books);
        employeesObservableList = FXCollections.observableList(employees);
        initTableView(gridPane);
        
        initSaveOptions(gridPane);
        primaryStage.show();
        
    }

    private void initializeGridPage(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane){
        bookTableView = new TableView<BookDTO>();

        bookTableView.setPlaceholder(new Label("No books to display"));
        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, Long> quantityColumn  = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<BookDTO, Long> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, quantityColumn, priceColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0, 0, 10, 1);
        bookTableView.setPrefWidth(800);

    }


    private void initSaveOptions(GridPane gridPane){

        employeesComboBoxView = new ComboBox<>();
        employeesComboBoxView.setPromptText("Select an employee");
        employeesComboBoxView.setPlaceholder(new Label ("No employees available right now !"));
        List<String> usernamesList = employeesObservableList.stream().map(UserDTO::getUsername).toList();
        employeesComboBoxView.setItems(FXCollections.observableArrayList(usernamesList));


        orderButton = new Button("Order");
        logOutButton = new Button("Log Out");

        HBox hBox = new HBox(10, employeesComboBoxView, orderButton, logOutButton);
        hBox.setAlignment(Pos.CENTER);
        gridPane.add(hBox, 0, 3, 10, 1);

    }

    public void addOrderButtonListener(EventHandler<ActionEvent> orderButtonListener){
        orderButton.setOnAction(orderButtonListener);
    }

    public void addLogOutButtonListener(EventHandler<ActionEvent> logOutButtonListener){
        logOutButton.setOnAction(logOutButtonListener);
    }

    public TableView getBookTableView() {
        return bookTableView;
    }

    public ComboBox<String> getEmployeesComboBoxView() {
        return employeesComboBoxView;
    }
    public void addDisplayAlertMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
