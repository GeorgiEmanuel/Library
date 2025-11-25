package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import view.model.BookDTO;

import java.util.List;

public class BookView {
    private TableView bookTableView;

    private final ObservableList<BookDTO> booksObservableList;

    private TextField authorTextField;

    private TextField titleTextField;

    private TextField quantityTextField;

    private TextField priceTextField;

    private Label authorLabel;

    private Label titleLabel;

    private Label quantityLabel;

    private Label priceLabel;

    private Button saveButton;

    private Button deleteButton;

    private Button orderButton;




    public BookView(Stage primaryStage, List<BookDTO>books){
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene = new Scene(gridPane, 1024, 600);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableList(books);
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

    }

    private void initSaveOptions(GridPane gridPane){
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        quantityLabel = new Label("Quantity");
        gridPane.add(quantityLabel, 5, 1);

        quantityTextField = new TextField();
        gridPane.add(quantityTextField, 6, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 7, 1);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 8, 1);


        saveButton = new Button("Save");

        deleteButton = new Button("Delete");

        orderButton = new Button("Order");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveButton, deleteButton, orderButton);
        gridPane.add(buttonBox, 4, 2);



    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addOrderButtonListener(EventHandler<ActionEvent> orderButtonListener){
        orderButton.setOnAction(orderButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getTitle(){
        return titleTextField.getText();
    }

    public String getAuthor(){
        return authorTextField.getText();
    }

    public String getQuantity() { return quantityTextField.getText(); }

    public String getPrice () { return priceTextField.getText(); }

    public void addBookToObservableList(BookDTO bookDTO){
        this.booksObservableList.add(bookDTO);
    }
    public void removeBookFromObservableList(BookDTO bookDTO){
        this.booksObservableList.remove(bookDTO);
    }

    public TableView getBookTableView(){
        return bookTableView;
    }
}
