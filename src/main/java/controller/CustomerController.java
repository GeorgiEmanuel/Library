package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Book;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.order.OrderService;
import view.CustomerView;
import view.model.BookDTO;


import java.time.LocalDateTime;

public class CustomerController {

    private final CustomerView customerView;
    private final OrderService orderService;
    private final BookService bookService;
    private final User user;

    public CustomerController(CustomerView customerView, OrderService orderService, BookService bookService, User user){
        this.customerView = customerView;
        this.orderService = orderService;
        this.bookService = bookService;
        this.user = user;

        this.customerView.addOrderButtonListener(new OrderButtonListener());
    }

    private class OrderButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) customerView.getBookTableView().getSelectionModel().getSelectedItem();
            String username  =  customerView.getEmployeesComboBoxView().getSelectionModel().getSelectedItem();
            if (username == null){
                customerView.addDisplayAlertMessage("Order failed", "Problem at ordering the book", "Please select an employee to proceed your order");
            }
            else {
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
                            customerView.addDisplayAlertMessage("Order failed", "Problem at ordering the book", orderNotification.getFormattedErrors());
                        } else {
                            bookDTO.decrementQuantity();
                            bookService.updateQuantity(bookToInsert.getId(), bookToInsert.getQuantity() - 1);
                            customerView.addDisplayAlertMessage("Order placed successfully", "Order was placed", "Thank you for ordering from our library");
                        }
                    } else {
                        customerView.addDisplayAlertMessage("Order failed!", "The book is out of stock !", "We are sorry for the inconvenience !");
                    }

                } else {
                    customerView.addDisplayAlertMessage("Order failed!", "Problem at ordering a book", "Please select a book before ordering");
                }
            }
        }
    }
}
