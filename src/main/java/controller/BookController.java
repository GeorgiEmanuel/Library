package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Book;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.order.OrderService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDateTime;

public class BookController {

    private final BookView bookView;

    private final BookService bookService;
    private final OrderService orderService;
    private final User user;

    public BookController(BookView bookView, BookService bookService, OrderService orderService, User user) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.orderService = orderService;
        this.user = user;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addOrderButtonListener(new OrderButtonListener());

    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String quantityString = bookView.getQuantity();
            String priceString = bookView.getPrice();


            if (title.isEmpty() || author.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author or Title fields", "Can not have an empty title or author field");
                return;
            }
            Long quantity = getValue(quantityString);
            Long price = getValue(priceString);
            if (quantity == -1L || price == -1L) {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Quantity or Price fields", "Can not have an empty quantity or price field or invalid input");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setQuantity(quantity).setPrice(price).build();
                boolean savedBooks = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBooks) {
                    bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database");
                    bookView.addBookToObservableList(bookDTO);

                } else {
                    bookView.addDisplayAlertMessage("Save Error", "Problem at adding Book", "There was a problem at adding the book to the database. Please try again !");

                }
            }

        }

    private static Long getValue(String value){
        try {
            Long result = Long.parseLong(value);
            if (result < 0L) {
                return -1L;
            }
            return result;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1L;
    }
}

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

                if (deletionSuccessful) {
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database");
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting the book", "There was a problem at deleting the book from the database. Please try again !");
                }
            } else {
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting Book", "You must select a book before  pressing the delete button");
            }
        }

    }

    private class OrderButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                Book bookToInsert = BookMapper.convertBookDTOToBook(bookDTO);
                if (bookToInsert.getQuantity() >= 1) {
                    Notification<Boolean> orderNotification = orderService.orderBook(
                             user.getId()
                            , bookToInsert.getId()
                            , 1L
                            , bookToInsert.getPrice()
                            , LocalDateTime.now()
                    );
                    if (orderNotification.hasErrors()) {
                        bookView.addDisplayAlertMessage("Order failed", "Problem at ordering the book", orderNotification.getFormattedErrors());
                    } else {
                        bookDTO.decrementQuantity();
                        bookService.updateQuantity(bookToInsert.getId(), bookToInsert.getQuantity() - 1);
                        bookView.addDisplayAlertMessage("Order placed successfully", "Order was placed", "Thank you for ordering from our library");
                    }
                }

                else {
                    bookView.addDisplayAlertMessage("Order failed!", "The book is out of stock !", "We are sorry for the inconvenience !");
                }

            }else {
                bookView.addDisplayAlertMessage("Order failed!", "Problem at ordering a book", "Please select a book before ordering");
            }
        }
    }
}