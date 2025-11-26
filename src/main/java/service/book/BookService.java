package service.book;

import model.Book;
import model.validator.Notification;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    Notification<Book> save(Book book);

    boolean delete(Book book);

    int getAgeOfBook(Long id);

    boolean updateQuantity(Long id, Long quantity);
}
